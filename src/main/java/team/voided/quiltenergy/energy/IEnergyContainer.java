package team.voided.quiltenergy.energy;

import com.mojang.datafixers.util.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import team.voided.quiltenergy.energy.interaction.EnergyInteractionResult;
import team.voided.quiltenergy.item.IEnergizedItem;
import team.voided.quiltenergy.numerics.Decimal;

import java.util.List;

public interface IEnergyContainer {
	EnergyUnit unit();

	Decimal stored();
	Decimal maxCapacity();
	boolean canReceive();
	boolean setReceivability(boolean canReceive);

	EnergyInteractionResult addEnergy(Decimal amount);
	EnergyInteractionResult removeEnergy(Decimal amount);

	EnergyInteractionResult setEnergy(Decimal amount);

	void transferEnergy(IEnergyContainer other, Decimal amount, Operation operation);
	<T extends IEnergizedItem> void transferEnergy(T other, ItemStack stack, Decimal amount, Operation operation);

	void equalizeWith(List<IEnergyContainer> containers, List<Pair<IEnergizedItem, ItemStack>> stacks);

	void writeNBT(CompoundTag compound, String prefix);
	void readNBT(CompoundTag compound, String prefix);

	enum Operation {
		RECEIVE, GIVE
	}
}
