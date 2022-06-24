package team.voided.quiltenergy.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import team.voided.quiltenergy.client.gui.EnergyBarTooltipData;
import team.voided.quiltenergy.energy.EnergyUnit;
import team.voided.quiltenergy.energy.IEnergyContainer;
import team.voided.quiltenergy.energy.interaction.EnergyInteractionResult;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Optional;

import static team.voided.quiltenergy.QuiltEnergy.formatDouble;

public class EnergizedItem extends Item implements IEnergizedItem {
	private EnergyUnit unit;
	private double maxCapacity;

	public EnergizedItem(Properties properties, EnergyUnit unit, double maxCapacity) {
		super(properties);
		this.unit = unit;
		this.maxCapacity = maxCapacity;
	}

	@Override
	@ParametersAreNonnullByDefault
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
		if (Screen.hasShiftDown()) {
			tooltip.add(Component.translatable("quilt_energy.energyitem.unit", getUnit().getName()).withStyle(ChatFormatting.LIGHT_PURPLE));
			tooltip.add(Component.translatable("quilt_energy.energyitem.max_capacity", formatDouble(getMaxCapacity(), 64)).withStyle(ChatFormatting.LIGHT_PURPLE));
			tooltip.add(Component.translatable("quilt_energy.energyitem.stored", formatDouble(getStored(stack), 64)).withStyle(ChatFormatting.LIGHT_PURPLE));
		} else {
			tooltip.add(Component.translatable("quilt_energy.item.shift_to_expand").withStyle(ChatFormatting.LIGHT_PURPLE));
		}
	}

	@Override
	@ParametersAreNonnullByDefault
	public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
		return Optional.of(EnergyBarTooltipData.fromEnergizedItem(stack));
	}


	@Override
	public void setUnit(EnergyUnit unit) {
		this.unit = unit;
	}

	@Override
	public EnergyUnit getUnit() {
		return unit;
	}

	@Override
	public void setMaxCapacity(double maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	@Override
	public double getMaxCapacity() {
		return maxCapacity;
	}

	@Override
	public double getStored(ItemStack stack) {
		return stack.getOrCreateTag().getDouble("stored");
	}

	@Override
	public EnergyInteractionResult setStored(ItemStack stack, double amount) {
		double original = getStored(stack);

		if (amount > getMaxCapacity()) return new EnergyInteractionResult(getUnit(), original, original, false);

		stack.getOrCreateTag().putDouble("stored", amount);
		return new EnergyInteractionResult(getUnit(), original, amount, true);
	}

	@Override
	public EnergyInteractionResult addEnergy(ItemStack stack, double amount) {
		double original = getStored(stack);

		if ((original + amount) > getMaxCapacity()) return new EnergyInteractionResult(getUnit(), original, original, false);

		setStored(stack, (original + amount));
		return new EnergyInteractionResult(getUnit(), original, (original + amount), true);
	}

	@Override
	public EnergyInteractionResult removeEnergy(ItemStack stack, double amount) {
		double original = getStored(stack);

		if ((original - amount) < 0) return new EnergyInteractionResult(getUnit(), original, original, false);

		setStored(stack, (original - amount));
		return new EnergyInteractionResult(getUnit(), original, (original - amount), true);
	}

	@Override
	public <T extends IEnergizedItem> void transferEnergy(ItemStack self, T otherClass, ItemStack otherStack, double amount, IEnergyContainer.Operation operation) {
		if (operation == IEnergyContainer.Operation.RECEIVE) {
			this.addEnergy(self, otherClass.getUnit().convertTo(this.getUnit(), amount));
			this.removeEnergy(otherStack, this.getUnit().convertTo(otherClass.getUnit(), amount));
		} else {
			this.removeEnergy(self, otherClass.getUnit().convertTo(this.getUnit(), amount));
			this.addEnergy(otherStack, this.getUnit().convertTo(otherClass.getUnit(), amount));
		}
	}

	@Override
	public void transferEnergy(ItemStack self, IEnergyContainer other, double amount, IEnergyContainer.Operation operation) {
		if (operation == IEnergyContainer.Operation.RECEIVE) {
			this.addEnergy(self, other.unit().convertTo(this.getUnit(), amount));
			other.removeEnergy(this.getUnit().convertTo(other.unit(), amount));
		} else {
			this.removeEnergy(self, other.unit().convertTo(this.getUnit(), amount));
			other.addEnergy(this.getUnit().convertTo(other.unit(), amount));
		}
	}
}
