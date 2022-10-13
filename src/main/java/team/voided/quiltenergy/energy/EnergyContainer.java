package team.voided.quiltenergy.energy;

import com.mojang.datafixers.util.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import team.voided.quiltenergy.energy.interaction.EnergyInteractionResult;
import team.voided.quiltenergy.item.IEnergizedItem;
import team.voided.quiltenergy.numerics.Decimal;

import java.util.List;

public class EnergyContainer implements IEnergyContainer {
	private final EnergyUnit unit;

	private Decimal stored;
	private final Decimal maxCapacity;
	private boolean canReceive;

	public EnergyContainer(EnergyUnit unit, Decimal maxCapacity) {
		this.unit = unit;
		this.maxCapacity = maxCapacity;
	}

	@Override
	public EnergyUnit unit() {
		return unit;
	}

	@Override
	public Decimal stored() {
		return stored;
	}

	@Override
	public Decimal maxCapacity() {
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
	public EnergyInteractionResult addEnergy(Decimal amount) {
		if (!canReceive()) return new EnergyInteractionResult(unit, stored, stored, false);
		Decimal originalAmount = stored;

		stored.add(amount, true);

		return new EnergyInteractionResult(unit, originalAmount, stored, true);
	}

	@Override
	public EnergyInteractionResult removeEnergy(Decimal amount) {
		if (!canReceive()) return new EnergyInteractionResult(unit, stored, stored, false);
		Decimal originalAmount = stored;

		stored.subtract(amount, true);

		return new EnergyInteractionResult(unit, originalAmount, stored, true);
	}

	@Override
	public EnergyInteractionResult setEnergy(Decimal amount) {
		Decimal originalAmount = stored;

		stored = amount;

		return new EnergyInteractionResult(unit, originalAmount, stored, true);
	}

	@Override
	public void transferEnergy(IEnergyContainer other, Decimal amount, Operation operation) {
		if (operation == Operation.RECEIVE) {
			this.addEnergy(other.unit().convertTo(this.unit, amount));
			other.removeEnergy(this.unit.convertTo(other.unit(), amount));
		} else {
			this.removeEnergy(other.unit().convertTo(this.unit, amount));
			other.addEnergy(this.unit.convertTo(other.unit(), amount));
		}
	}

	@Override
	public <T extends IEnergizedItem> void transferEnergy(T other, ItemStack stack, Decimal amount, Operation operation) {
		if (operation == Operation.RECEIVE) {
			this.addEnergy(other.unit().convertTo(this.unit, amount));
			other.removeEnergy(stack, this.unit.convertTo(other.unit(), amount));
		} else {
			this.removeEnergy(other.unit().convertTo(this.unit, amount));
			other.addEnergy(stack, this.unit.convertTo(other.unit(), amount));
		}
	}

	@Override
	public void equalizeWith(List<IEnergyContainer> containers, List<Pair<IEnergizedItem, ItemStack>> stacks) {
		Decimal total = EnergyUnits.RAW_ENERGY.convertFrom(unit, stored);

		for (IEnergyContainer container : containers) {
			total.add(EnergyUnits.RAW_ENERGY.convertFrom(container.unit(), container.stored()), true);
		}

		for (Pair<IEnergizedItem, ItemStack> item : stacks) {
			total.add(EnergyUnits.RAW_ENERGY.convertFrom(item.getFirst().unit(), item.getFirst().stored(item.getSecond())), true);
		}

		Decimal set = total.divide(new Decimal(((Integer) (containers.size() + stacks.size())).toString()), false);

		for (IEnergyContainer container : containers) {
			container.setEnergy(container.unit().convertFrom(EnergyUnits.RAW_ENERGY, set));
		}

		for (Pair<IEnergizedItem, ItemStack> item : stacks) {
			item.getFirst().setEnergy(item.getSecond(), item.getFirst().unit().convertFrom(EnergyUnits.RAW_ENERGY, set));
		}
	}

	@Override
	public void writeNBT(CompoundTag compound, String prefix) {
		Decimal.NBTHelper.writeToCompound(compound, prefix + "_stored_energy", stored);
		compound.putBoolean(prefix + "_can_receive", canReceive);
	}

	@Override
	public void readNBT(CompoundTag compound, String prefix) {
		stored = Decimal.NBTHelper.readFromCompound(compound, prefix + "_stored_energy");
		canReceive = compound.getBoolean(prefix + "_can_receive");
	}
}
