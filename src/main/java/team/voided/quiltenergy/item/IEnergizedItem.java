package team.voided.quiltenergy.item;

import net.minecraft.world.item.ItemStack;
import team.voided.quiltenergy.energy.EnergyUnit;
import team.voided.quiltenergy.energy.IEnergyContainer;
import team.voided.quiltenergy.energy.interaction.EnergyInteractionResult;

public interface IEnergizedItem {
	void setUnit(EnergyUnit unit);
	EnergyUnit getUnit();
	void setMaxCapacity(double maxCapacity);
	double getMaxCapacity();
	double getStored(ItemStack stack);
	EnergyInteractionResult setStored(ItemStack stack, double amount);
	EnergyInteractionResult addEnergy(ItemStack stack, double amount);
	EnergyInteractionResult removeEnergy(ItemStack stack, double amount);
	<T extends IEnergizedItem> void transferEnergy(ItemStack self, T otherClass, ItemStack otherStack, double amount, IEnergyContainer.Operation operation);
	void transferEnergy(ItemStack self, IEnergyContainer other, double amount, IEnergyContainer.Operation operation);
}
