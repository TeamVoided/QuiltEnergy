package team.voided.quiltenergy.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;
import team.voided.quiltenergy.client.gui.EnergyBarTooltipData;
import team.voided.quiltenergy.energy.EnergyUnit;
import team.voided.quiltenergy.numerics.Decimal;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Optional;

import static team.voided.quiltenergy.QuiltEnergy.formatDouble;

public class EnergizedBlockItem extends BlockItem implements StandardEnergizedItem {
	private EnergyUnit unit;
	private Decimal maxCapacity;

	public EnergizedBlockItem(Block block, Properties properties, EnergyUnit unit, Decimal maxCapacity) {
		super(block, properties);
		this.unit = unit;
		this.maxCapacity = maxCapacity;
	}

	@Override
	@ParametersAreNonnullByDefault
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
		if (Screen.hasShiftDown()) {
			tooltip.add(Component.translatable("quilt_energy.energyitem.unit", unit().getName()).withStyle(ChatFormatting.LIGHT_PURPLE));
			tooltip.add(Component.translatable("quilt_energy.energyitem.max_capacity", formatDouble(getMaxCapacity().doubleValue(), 64)).withStyle(ChatFormatting.LIGHT_PURPLE));
			tooltip.add(Component.translatable("quilt_energy.energyitem.stored", formatDouble(stored(stack).doubleValue(), 64)).withStyle(ChatFormatting.LIGHT_PURPLE));
		} else {
			tooltip.add(Component.translatable("quilt_energy.item.shift_to_expand").withStyle(ChatFormatting.LIGHT_PURPLE));
		}
	}

	@Override
	@ParametersAreNonnullByDefault
	public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
		return EnergyBarTooltipData.fromEnergizedItem(stack);
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
