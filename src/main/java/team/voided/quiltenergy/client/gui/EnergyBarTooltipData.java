package team.voided.quiltenergy.client.gui;

import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import team.voided.quiltenergy.energy.EnergyUnit;
import team.voided.quiltenergy.item.IEnergizedItem;

import java.util.Optional;

public class EnergyBarTooltipData implements TooltipComponent {
	private final float percentFull;
	private final EnergyUnit unit;

	public EnergyBarTooltipData(float percentFull, EnergyUnit unit) {
		this.percentFull = percentFull;
		this.unit = unit;
	}

	public static Optional<TooltipComponent> fromEnergizedItem(ItemStack stack) {
		if (stack.getItem() instanceof IEnergizedItem item) {
			return Optional.of(new EnergyBarTooltipData(getFractionForDisplay(item, stack), item.unit()));
		}
		return Optional.empty();
	}

	public static float getFractionForDisplay(IEnergizedItem item, ItemStack stack) {
		return (float) (item.stored(stack) / item.getMaxCapacity());
	}

	public float getPercentFull() {
		return percentFull;
	}

	public EnergyUnit getUnit() {
		return unit;
	}
}
