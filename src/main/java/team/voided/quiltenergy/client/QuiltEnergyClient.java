package team.voided.quiltenergy.client;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.tooltip.api.client.TooltipComponentCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.voided.quiltenergy.client.gui.EnergyBarTooltipComponent;

public class QuiltEnergyClient implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Quilt Energy Client");

	@Override
	public void onInitializeClient(ModContainer mod) {
		TooltipComponentCallback.EVENT.register(EnergyBarTooltipComponent::tryConvert);
	}
}
