package me.deathlord.quiltenergy;

import me.deathlord.quiltenergy.energy.EnergyUnits;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuiltEnergy implements ModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("Quilt Energy");
	public static final String MOD_ID = "quilt_energy";

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Hello Quilt world from {}!", mod.metadata().name());
		EnergyUnits.register();
	}

	public static Identifier modLoc(String path) {
		return new Identifier(MOD_ID, path);
	}
}
