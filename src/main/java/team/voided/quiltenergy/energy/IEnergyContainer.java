package team.voided.quiltenergy.energy;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import team.voided.quiltenergy.energy.interaction.EnergyInteractionResult;
import team.voided.quiltenergy.item.EnergizedItem;

public interface IEnergyContainer {
	EnergyUnit unit();

	double stored();
	double maxCapacity();
	boolean canReceive();
	boolean canReceive(boolean canReceive);

	EnergyInteractionResult addEnergy(double amount);
	EnergyInteractionResult removeEnergy(double amount);

	void transferEnergy(IEnergyContainer other, double amount, Operation operation);
	<T extends EnergizedItem> void transferEnergy(T other, ItemStack stack, double amount, Operation operation);

	void writeNBT(CompoundTag compound, String prefix);
	void readNBT(CompoundTag compound, String prefix);
	enum Operation {
		RECEIVE, GIVE
	}
}
