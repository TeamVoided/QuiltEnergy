package team.voided.quiltenergy;

import team.voided.quiltenergy.energy.EnergyUnits;
import team.voided.quiltenergy.item.EnergizedItem;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
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
		Item TEST_ENERGIZED_ITEM = Registry.register(Registry.ITEM, modLoc("test_item"), new EnergizedItem(new Item.Properties(), EnergyUnits.REDSTONE_FLUX, 100, 10));
	}

	public static ResourceLocation modLoc(String path) {
		return new ResourceLocation(MOD_ID, path);
	}
}
