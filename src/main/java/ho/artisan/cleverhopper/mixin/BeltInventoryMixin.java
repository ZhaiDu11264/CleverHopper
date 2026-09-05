package ho.artisan.cleverhopper.mixin;

import com.simibubi.create.content.kinetics.belt.behaviour.DirectBeltInputBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.BehaviourType;
import ho.artisan.cleverhopper.belt.HopperBeltInput;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Makes vanilla hoppers accept items pushed off the end of a Create belt,
 * exactly like a basin does. Create only checks its own
 * {@link DirectBeltInputBehaviour}; this redirect falls back to a wrapper
 * around the hopper's item handler when Create finds no behaviour.
 */
@Mixin(targets = "com.simibubi.create.content.kinetics.belt.transport.BeltInventory", remap = false)
public class BeltInventoryMixin {

	@Redirect(
			method = {"resolveEnding", "tick"},
			at = @At(value = "INVOKE",
					target = "Lcom/simibubi/create/foundation/blockEntity/behaviour/BlockEntityBehaviour;"
							+ "get(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;"
							+ "Lcom/simibubi/create/foundation/blockEntity/behaviour/BehaviourType;)"
							+ "Lcom/simibubi/create/foundation/blockEntity/behaviour/BlockEntityBehaviour;")
	)
	private static BlockEntityBehaviour cleverhopper$wrapVanillaHopper(
			net.minecraft.world.level.BlockGetter world, BlockPos pos, BehaviourType<?> type) {
		BlockEntityBehaviour original = BlockEntityBehaviour.get(world, pos, type);
		if (original != null)
			return original;
		if (type != DirectBeltInputBehaviour.TYPE)
			return null;
		if (!(world instanceof Level level))
			return null;

		// Only vanilla hoppers: keep Create's behaviour for everything else untouched.
		BlockState state = level.getBlockState(pos);
		if (!(state.getBlock() instanceof HopperBlock))
			return null;

		// A redstone-locked hopper counts as a full container:
		// belt items pile up on the belt instead of being ejected.
		if (!state.getValue(HopperBlock.ENABLED))
			return new HopperBeltInput(null);

		IItemHandler handler = level.getCapability(Capabilities.ItemHandler.BLOCK, pos, null);
		if (handler == null)
			return null;
		return new HopperBeltInput(handler);
	}
}
