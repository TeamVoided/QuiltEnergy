package team.voided.quiltenergy.client.gui;

import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import team.voided.quiltenergy.energy.EnergyUnit;
import team.voided.quiltenergy.item.EnergizedItem;

public class EnergyBarTooltip implements TooltipComponent {
	private final float percentFull;
	private final EnergyUnit unit;

	public EnergyBarTooltip(float percentFull, EnergyUnit unit) {
		this.percentFull = percentFull;
		this.unit = unit;
	}

	public static EnergyBarTooltip fromEnergizedItem(ItemStack stack) {
		if (stack.getItem() instanceof EnergizedItem item) {
			return new EnergyBarTooltip(getFractionForDisplay(item, stack), item.getUnit(stack));
		}
		throw new IllegalStateException("Item is not of type \"EnergizedItem\"");
	}

	public static float getFractionForDisplay(EnergizedItem item, ItemStack stack) {
		return (float) (item.getStored(stack) / item.getMaxCapacity(stack));
	}

	public float getPercentFull() {
		return percentFull;
	}

	public EnergyUnit getUnit() {
		return unit;
	}
}
