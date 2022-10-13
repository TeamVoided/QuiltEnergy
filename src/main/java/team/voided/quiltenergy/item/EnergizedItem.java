package team.voided.quiltenergy.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import team.voided.quiltenergy.energy.EnergyUnit;
import team.voided.quiltenergy.numerics.Decimal;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Optional;

public class EnergizedItem extends Item implements StandardEnergizedItem {
	private EnergyUnit unit;
	private Decimal maxCapacity;

	public EnergizedItem(Properties properties, EnergyUnit unit, Decimal maxCapacity) {
		super(properties);
		this.unit = unit;
		this.maxCapacity = maxCapacity;
	}

	@Override
	@ParametersAreNonnullByDefault
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
		appendHoverTextDefault(stack, world, tooltip, context);
	}

	@Override
	@ParametersAreNonnullByDefault
	public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
		return getTooltipImageDefault(stack);
	}

	@Override
	public void setUnit(EnergyUnit unit) {
		this.unit = unit;
	}

	@Override
	public EnergyUnit unit() {
		return unit;
	}

	@Override
	public void setMaxCapacity(Decimal maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	@Override
	public Decimal getMaxCapacity() {
		return maxCapacity;
	}
}
