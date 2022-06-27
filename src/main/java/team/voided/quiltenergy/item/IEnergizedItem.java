package team.voided.quiltenergy.item;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.item.ItemStack;
import team.voided.quiltenergy.energy.EnergyUnit;
import team.voided.quiltenergy.energy.IEnergyContainer;
import team.voided.quiltenergy.energy.interaction.EnergyInteractionResult;

import java.util.List;

public interface IEnergizedItem {
	void setUnit(EnergyUnit unit);
	EnergyUnit unit();
	void setMaxCapacity(double maxCapacity);
	double getMaxCapacity();
	double stored(ItemStack stack);
	EnergyInteractionResult setEnergy(ItemStack stack, double amount);
	EnergyInteractionResult addEnergy(ItemStack stack, double amount);
	EnergyInteractionResult removeEnergy(ItemStack stack, double amount);
	<T extends IEnergizedItem> void transferEnergy(ItemStack self, T otherClass, ItemStack otherStack, double amount, IEnergyContainer.Operation operation);
	void transferEnergy(ItemStack self, IEnergyContainer other, double amount, IEnergyContainer.Operation operation);

	void equalizeWith(ItemStack self, List<IEnergyContainer> containers, List<Pair<IEnergizedItem, ItemStack>> stacks);
}
