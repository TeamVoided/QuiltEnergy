package team.voided.quiltenergy.client.gui;

import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import team.voided.quiltenergy.energy.EnergyUnit;
import team.voided.quiltenergy.item.EnergizedItem;

public class EnergyBarTooltipData implements TooltipComponent {
	private final float percentFull;
	private final EnergyUnit unit;

	public EnergyBarTooltipData(float percentFull, EnergyUnit unit) {
		this.percentFull = percentFull;
		this.unit = unit;
	}

	public static EnergyBarTooltipData fromEnergizedItem(ItemStack stack) {
		if (stack.getItem() instanceof EnergizedItem item) {
			return new EnergyBarTooltipData(getFractionForDisplay(item, stack), item.unit());
		}
		return null;
	}

	public static float getFractionForDisplay(EnergizedItem item, ItemStack stack) {
		return (float) (item.stored(stack) / item.getMaxCapacity());
	}

	public float getPercentFull() {
		return percentFull;
	}

	public EnergyUnit getUnit() {
		return unit;
	}
}
