package me.voided.quiltenergy.item;

import me.voided.quiltenergy.QuiltEnergy;
import me.voided.quiltenergy.energy.EnergyUnit;
import me.voided.quiltenergy.energy.IEnergyContainer;
import me.voided.quiltenergy.energy.interaction.EnergyInteractionResult;
import me.voided.quiltenergy.registry.EnergyRegistries;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnergizedItem extends Item {
	private EnergyUnit unit;
	private double maxCapacity;
	private final double preStoredEnergy;
	private boolean setVars = true;

	public EnergizedItem(Settings settings, EnergyUnit unit, double maxCapacity, double preStoredEnergy) {
		super(settings);
		this.unit = unit;
		this.maxCapacity = maxCapacity;
		this.preStoredEnergy = preStoredEnergy;
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		if (Screen.hasShiftDown()) {
			tooltip.add(Text.translatable("quilt_energy.energyitem.unit", getUnit(stack).getName()).formatted(Formatting.LIGHT_PURPLE));
			tooltip.add(Text.translatable("quilt_energy.energyitem.max_capacity", getMaxCapacity(stack)).formatted(Formatting.LIGHT_PURPLE));
			tooltip.add(Text.translatable("quilt_energy.energyitem.stored", getStored(stack)).formatted(Formatting.LIGHT_PURPLE));
		} else {
			tooltip.add(Text.translatable("quilt_energy.item.shift_to_expand").formatted(Formatting.LIGHT_PURPLE));
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (setVars) {
			setUnit(stack, unit);
			setMaxCapacity(stack, maxCapacity);
			setStored(stack, preStoredEnergy);
			setVars = false;
		}
	}

	public void setUnit(ItemStack stack, EnergyUnit unit) {
		stack.getOrCreateNbt().putString("energy_unit", unit.id().toString());
		QuiltEnergy.LOGGER.info(unit.id().toString());
		this.unit = unit;
	}

	public EnergyUnit getUnit(ItemStack stack) {
		String[] split = stack.getOrCreateNbt().getString("energy_unit").split(":");
		return EnergyRegistries.UNIT.get(new Identifier(split[0], split[1]));
	}

	public void setMaxCapacity(ItemStack stack, double maxCapacity) {
		stack.getOrCreateNbt().putDouble("max_energy_capacity", maxCapacity);
		this.maxCapacity = maxCapacity;
	}

	public double getMaxCapacity(ItemStack stack) {
		return stack.getOrCreateNbt().getDouble("max_energy_capacity");
	}

	public double getStored(ItemStack stack) {
		return stack.getOrCreateNbt().getDouble("stored");
	}

	public EnergyInteractionResult setStored(ItemStack stack, double amount) {
		double original = getStored(stack);

		if (amount > getMaxCapacity(stack)) return new EnergyInteractionResult(getUnit(stack), original, original, false);

		stack.getOrCreateNbt().putDouble("stored", amount);
		return new EnergyInteractionResult(getUnit(stack), original, amount, true);
	}

	public EnergyInteractionResult addEnergy(ItemStack stack, double amount) {
		double original = getStored(stack);

		if ((original + amount) > getMaxCapacity(stack)) return new EnergyInteractionResult(getUnit(stack), original, original, false);

		setStored(stack, (original + amount));
		return new EnergyInteractionResult(getUnit(stack), original, (original + amount), true);
	}

	public EnergyInteractionResult removeEnergy(ItemStack stack, double amount) {
		double original = getStored(stack);

		if ((original - amount) < 0) return new EnergyInteractionResult(getUnit(stack), original, original, false);

		setStored(stack, (original - amount));
		return new EnergyInteractionResult(getUnit(stack), original, (original - amount), true);
	}

	public void transferEnergy(ItemStack self, ItemStack other, double amount, IEnergyContainer.Operation operation) {
		if (operation == IEnergyContainer.Operation.RECEIVE) {
			this.addEnergy(self, this.getUnit(other).convertTo(this.getUnit(self), amount));
			this.removeEnergy(other, this.getUnit(self).convertTo(this.getUnit(other), amount));
		} else {
			this.removeEnergy(self, this.getUnit(other).convertTo(this.getUnit(self), amount));
			this.addEnergy(other, this.getUnit(self).convertTo(this.getUnit(other), amount));
		}
	}

	public void transferEnergy(ItemStack self, IEnergyContainer other, double amount, IEnergyContainer.Operation operation) {
		if (operation == IEnergyContainer.Operation.RECEIVE) {
			this.addEnergy(self, other.unit().convertTo(this.getUnit(self), amount));
			other.removeEnergy(this.getUnit(self).convertTo(other.unit(), amount));
		} else {
			this.removeEnergy(self, other.unit().convertTo(this.getUnit(self), amount));
			other.addEnergy(this.getUnit(self).convertTo(other.unit(), amount));
		}
	}
}
