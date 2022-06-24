package team.voided.quiltenergy;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.voided.quiltenergy.command.SetEnergyCommand;
import team.voided.quiltenergy.energy.EnergyUnits;
import team.voided.quiltenergy.item.EnergizedItem;

import java.text.DecimalFormat;

public class QuiltEnergy implements ModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("Quilt Energy");
	public static final String MOD_ID = "quilt_energy";

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Hello Quilt world from {}!", mod.metadata().name());
		EnergyUnits.register();
		Registry.register(Registry.ITEM, modLoc("test_item_1"), new EnergizedItem(new Item.Properties(), EnergyUnits.QUILT_ENERGY, 100_000_000));
		Registry.register(Registry.ITEM, modLoc("test_item_2"), new EnergizedItem(new Item.Properties(), EnergyUnits.REDSTONE_FLUX, 100_000_000));
		CommandRegistrationCallback.EVENT.register((dispatcher, buildContext, environment) -> {
			SetEnergyCommand.register(dispatcher, buildContext);
		});
	}

	public static ResourceLocation modLoc(String path) {
		return new ResourceLocation(MOD_ID, path);
	}

	public static String formatDouble(double d, int maximumFractionalDigits) {
		DecimalFormat format = new DecimalFormat();
		format.setMaximumFractionDigits(maximumFractionalDigits);

		return format.format(d);
	}
}
