package team.voided.quiltenergy.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import team.voided.quiltenergy.client.gui.EnergyBarTooltipComponent;

import java.util.List;

@Mixin(Screen.class)
public class ScreenMixin {

	@Inject(method = "renderTooltipInternal", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V"), locals = LocalCapture.CAPTURE_FAILHARD)
	private void renderEnergyBar(PoseStack poseStack, List<ClientTooltipComponent> components, int oldX, int oldY, CallbackInfo ci, int width, int height, int x, int y) {
		for (ClientTooltipComponent component : components) {
			if (component instanceof EnergyBarTooltipComponent bar) {
				bar.setContext(x, y, width);
			}
		}
	}
}
