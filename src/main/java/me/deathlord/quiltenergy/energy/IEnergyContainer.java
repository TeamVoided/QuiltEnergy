package me.deathlord.quiltenergy.energy;

import me.deathlord.quiltenergy.energy.interaction.EnergyInteractionResult;
import me.deathlord.quiltenergy.item.EnergizedItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

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

	void writeNBT(NbtCompound compound, String prefix);
	void readNBT(NbtCompound compound, String prefix);
	enum Operation {
		RECEIVE, GIVE
	}
}
