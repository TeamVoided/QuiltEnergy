package team.voided.quiltenergy.energy;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import team.voided.quiltenergy.energy.interaction.EnergyInteractionResult;
import team.voided.quiltenergy.item.IEnergizedItem;

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
	public boolean setReceivability(boolean canReceive) {
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
	public <T extends IEnergizedItem> void transferEnergy(T other, ItemStack stack, double amount, Operation operation) {
		if (operation == Operation.RECEIVE) {
			this.addEnergy(other.getUnit().convertTo(this.unit, amount));
			other.removeEnergy(stack, this.unit.convertTo(other.getUnit(), amount));
		} else {
			this.removeEnergy(other.getUnit().convertTo(this.unit, amount));
			other.addEnergy(stack, this.unit.convertTo(other.getUnit(), amount));
		}
	}

	@Override
	public void writeNBT(CompoundTag compound, String prefix) {
		compound.putDouble(prefix + "_stored_energy", stored);
		compound.putBoolean(prefix + "_can_receive", canReceive);
	}

	@Override
	public void readNBT(CompoundTag compound, String prefix) {
		stored = compound.getDouble(prefix + "_stored_energy");
		canReceive = compound.getBoolean(prefix + "_can_receive");
	}
}
