package ho.artisan.cleverhopper.belt;

import com.simibubi.create.content.kinetics.belt.behaviour.DirectBeltInputBehaviour;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;

/**
 * A lightweight {@link DirectBeltInputBehaviour} wrapper that feeds inserted
 * items straight into a vanilla hopper's item handler. Behaves the same way as
 * Create's basin when a belt pushes items into it.
 * <p>
 * A {@code null} handler models a redstone-locked hopper: insertions always
 * fail, so belt items pile up on the belt exactly like a full container.
 */
public class HopperBeltInput extends DirectBeltInputBehaviour {

	private final IItemHandler handler;

	public HopperBeltInput(@Nullable IItemHandler handler) {
		super(null);
		this.handler = handler;
	}

	@Override
	public boolean canInsertFromSide(Direction side) {
		return handler != null;
	}

	@Override
	public ItemStack handleInsertion(com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack transported,
									  Direction side, boolean simulate) {
		if (handler == null)
			return transported.stack;
		return ItemHandlerHelper.insertItemStacked(handler, transported.stack.copy(), simulate);
	}
}
