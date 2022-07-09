package team.voided.quiltenergy.item;

import com.mojang.datafixers.util.Pair;
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
import team.voided.quiltenergy.energy.EnergyUnits;
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
			tooltip.add(Component.translatable("quilt_energy.energyitem.unit", unit().getName()).withStyle(ChatFormatting.LIGHT_PURPLE));
			tooltip.add(Component.translatable("quilt_energy.energyitem.max_capacity", formatDouble(getMaxCapacity(), 64)).withStyle(ChatFormatting.LIGHT_PURPLE));
			tooltip.add(Component.translatable("quilt_energy.energyitem.stored", formatDouble(stored(stack), 64)).withStyle(ChatFormatting.LIGHT_PURPLE));
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
	public void setMaxCapacity(double maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	@Override
	public double getMaxCapacity() {
		return maxCapacity;
	}

	@Override
	public double stored(ItemStack stack) {
		return stack.getOrCreateTag().getDouble("stored");
	}

	@Override
	public EnergyInteractionResult setEnergy(ItemStack stack, double amount) {
		double original = stored(stack);

		if (amount > getMaxCapacity()) return new EnergyInteractionResult(unit(), original, original, false);

		stack.getOrCreateTag().putDouble("stored", amount);
		return new EnergyInteractionResult(unit(), original, amount, true);
	}

	@Override
	public EnergyInteractionResult addEnergy(ItemStack stack, double amount) {
		double original = stored(stack);

		if ((original + amount) > getMaxCapacity()) return new EnergyInteractionResult(unit(), original, original, false);

		setEnergy(stack, (original + amount));
		return new EnergyInteractionResult(unit(), original, (original + amount), true);
	}

	@Override
	public EnergyInteractionResult removeEnergy(ItemStack stack, double amount) {
		double original = stored(stack);

		if ((original - amount) < 0) return new EnergyInteractionResult(unit(), original, original, false);

		setEnergy(stack, (original - amount));
		return new EnergyInteractionResult(unit(), original, (original - amount), true);
	}

	@Override
	public <T extends IEnergizedItem> void transferEnergy(ItemStack self, T otherClass, ItemStack otherStack, double amount, IEnergyContainer.Operation operation) {
		if (operation == IEnergyContainer.Operation.RECEIVE) {
			this.addEnergy(self, otherClass.unit().convertTo(this.unit(), amount));
			this.removeEnergy(otherStack, this.unit().convertTo(otherClass.unit(), amount));
		} else {
			this.removeEnergy(self, otherClass.unit().convertTo(this.unit(), amount));
			this.addEnergy(otherStack, this.unit().convertTo(otherClass.unit(), amount));
		}
	}

	@Override
	public void transferEnergy(ItemStack self, IEnergyContainer other, double amount, IEnergyContainer.Operation operation) {
		if (operation == IEnergyContainer.Operation.RECEIVE) {
			this.addEnergy(self, other.unit().convertTo(this.unit(), amount));
			other.removeEnergy(this.unit().convertTo(other.unit(), amount));
		} else {
			this.removeEnergy(self, other.unit().convertTo(this.unit(), amount));
			other.addEnergy(this.unit().convertTo(other.unit(), amount));
		}
	}

	@Override
	public void equalizeWith(ItemStack self, List<IEnergyContainer> containers, List<Pair<IEnergizedItem, ItemStack>> stacks) {
		double total = EnergyUnits.RAW_ENERGY.convertFrom(unit, stored(self));

		for (IEnergyContainer container : containers) {
			total += EnergyUnits.RAW_ENERGY.convertFrom(container.unit(), container.stored());
		}

		for (Pair<IEnergizedItem, ItemStack> item : stacks) {
			total += EnergyUnits.RAW_ENERGY.convertFrom(item.getFirst().unit(), item.getFirst().stored(item.getSecond()));
		}

		double set = total / (containers.size() + stacks.size());

		for (IEnergyContainer container : containers) {
			container.setEnergy(container.unit().convertFrom(EnergyUnits.RAW_ENERGY, set));
		}

		for (Pair<IEnergizedItem, ItemStack> item : stacks) {
			item.getFirst().setEnergy(item.getSecond(), item.getFirst().unit().convertFrom(EnergyUnits.RAW_ENERGY, set));
		}
	}
}
