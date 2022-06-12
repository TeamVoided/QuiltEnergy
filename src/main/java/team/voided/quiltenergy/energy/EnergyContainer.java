/*
The MIT License (MIT)

Copyright (c) 2022 TeamVoided

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/

package team.voided.quiltenergy.energy;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import team.voided.quiltenergy.energy.interaction.EnergyInteractionResult;
import team.voided.quiltenergy.item.EnergizedItem;

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
