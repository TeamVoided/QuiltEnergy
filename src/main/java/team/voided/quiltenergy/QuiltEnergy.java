package team.voided.quiltenergy;

import net.minecraft.resources.ResourceLocation;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.voided.quiltenergy.energy.EnergyUnits;

public class QuiltEnergy implements ModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("Quilt Energy");
	public static final String MOD_ID = "quilt_energy";

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Hello Quilt world from {}!", mod.metadata().name());
		EnergyUnits.register();
	}

	public static ResourceLocation modLoc(String path) {
		return new ResourceLocation(MOD_ID, path);
	}
}
