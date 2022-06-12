package team.voided.quiltenergy.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import team.voided.quiltenergy.HSV;
import team.voided.quiltenergy.energy.EnergyUnit;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class EnergyBarTooltipComponent implements ClientTooltipComponent {
	private final float percentFull;
	private final EnergyUnit unit;

	private int mouseX, mouseY;

	private int totalWidth;

	public EnergyBarTooltipComponent(EnergyBarTooltipData component) {
		this.percentFull = component.getPercentFull();
		this.unit = component.getUnit();
	}

	@Nullable
	public static ClientTooltipComponent tryConvert(TooltipComponent component) {
		if (component instanceof EnergyBarTooltipData t)
			return new EnergyBarTooltipComponent(t);

		return null;
	}

	@Override
	public int getHeight() {
		return 0;
	}

	@Override
	public int getWidth(Font font) {
		return 0;
	}

	public void setContext(int mouseX, int mouseY, int totalWidth) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		this.totalWidth = totalWidth;
	}

	@Override
	@ParametersAreNonnullByDefault
	public void renderImage(Font font, int x, int y, PoseStack ps, ItemRenderer itemRenderer, int z) {
		int height = 3;
		int offsetFromBox = 4;

		ps.pushPose();
		ps.translate(0, 0, z);

		int barWidth = (int) Math.ceil(totalWidth * percentFull);

		HSV color = unit.getEnergyBarColor();

		GuiComponent.fill(ps, mouseX - 1, mouseY - height - offsetFromBox -1, mouseX + totalWidth + 1, mouseY - offsetFromBox, 0xFF000000);
		GuiComponent.fill(ps, mouseX, mouseY - height - offsetFromBox, mouseX + barWidth, mouseY - offsetFromBox, Mth.hsvToRgb(color.hue(), color.saturation(), color.value()));
		GuiComponent.fill(ps, mouseX + barWidth, mouseY - height - offsetFromBox, mouseX + totalWidth, mouseY - offsetFromBox, 0xFF555555);

		mouseX = mouseY = 0;
		totalWidth = 50;
	}
}
