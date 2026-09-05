package ho.artisan.cleverhopper;

import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(CleverHopper.MODID)
public class CleverHopper {
	public static final String MODID = "cleverhopper";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	public CleverHopper() {
		LOGGER.info("Clever Hopper loaded - vanilla hoppers now accept Create belts");
	}
}
