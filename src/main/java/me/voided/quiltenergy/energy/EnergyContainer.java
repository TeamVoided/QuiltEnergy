package me.voided.quiltenergy.energy;

import me.voided.quiltenergy.energy.interaction.EnergyInteractionResult;
import me.voided.quiltenergy.item.EnergizedItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class EnergyContainer implements IEnergyContainer {
	private final EnergyUnit unit;

	private double stored;
	private final double maxCapacity;
	private boolean canReceive;

	public EnergyContainer(EnergyUnit unit, double maxCapacity) {
		this.unit = unit;
		this.maxCapacity = maxCapacity;
	}

	@Override
	public EnergyUnit unit() {
		return unit;
	}

	@Override
	public double stored() {
		return stored;
	}

	@Override
	public double maxCapacity() {
		return maxCapacity;
	}

	@Override
	public boolean canReceive() {
		return canReceive;
	}

	@Override
	public boolean canReceive(boolean canReceive) {
		this.canReceive = canReceive;
		return this.canReceive;
	}

	@Override
	public EnergyInteractionResult addEnergy(double amount) {
		if (!canReceive()) return new EnergyInteractionResult(unit, stored, stored, false);
		double originalAmount = stored;

		stored += amount;

		return new EnergyInteractionResult(unit, originalAmount, stored, true);
	}

	@Override
	public EnergyInteractionResult removeEnergy(double amount) {
		if (!canReceive()) return new EnergyInteractionResult(unit, stored, stored, false);
		double originalAmount = stored;

		stored -= amount;

		return new EnergyInteractionResult(unit, originalAmount, stored, true);
	}

	@Override
	public void transferEnergy(IEnergyContainer other, double amount, Operation operation) {
		if (operation == Operation.RECEIVE) {
			this.addEnergy(other.unit().convertTo(this.unit, amount));
			other.removeEnergy(this.unit.convertTo(other.unit(), amount));
		} else {
			this.removeEnergy(other.unit().convertTo(this.unit, amount));
			other.addEnergy(this.unit.convertTo(other.unit(), amount));
		}
	}

	@Override
	public <T extends EnergizedItem> void transferEnergy(T other, ItemStack stack, double amount, Operation operation) {
		if (operation == Operation.RECEIVE) {
			this.addEnergy(other.getUnit(stack).convertTo(this.unit, amount));
			other.removeEnergy(stack, this.unit.convertTo(other.getUnit(stack), amount));
		} else {
			this.removeEnergy(other.getUnit(stack).convertTo(this.unit, amount));
			other.addEnergy(stack, this.unit.convertTo(other.getUnit(stack), amount));
		}
	}

	@Override
	public void writeNBT(NbtCompound compound, String prefix) {
		compound.putDouble(prefix + "_stored_energy", stored);
		compound.putBoolean(prefix + "_can_receive", canReceive);
	}

	@Override
	public void readNBT(NbtCompound compound, String prefix) {
		stored = compound.getDouble(prefix + "_stored_energy");
		canReceive = compound.getBoolean(prefix + "_can_receive");
	}
}
