/*
The MIT License (MIT)

Copyright (c) 2022 TeamVoided

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/

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

	public EnergyBarTooltipComponent(EnergyBarTooltip component) {
		this.percentFull = component.getPercentFull();
		this.unit = component.getUnit();
	}

	@Nullable
	public static ClientTooltipComponent tryConvert(TooltipComponent component) {
		if (component instanceof EnergyBarTooltip t)
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
