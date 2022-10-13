package team.voided.quiltenergy.item;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.item.ItemStack;
import team.voided.quiltenergy.energy.EnergyUnit;
import team.voided.quiltenergy.energy.IEnergyContainer;
import team.voided.quiltenergy.energy.interaction.EnergyInteractionResult;
import team.voided.quiltenergy.numerics.Decimal;

import java.util.List;

public interface IEnergizedItem {
	void setUnit(EnergyUnit unit);
	EnergyUnit unit();
	void setMaxCapacity(Decimal maxCapacity);
	Decimal getMaxCapacity();
	Decimal stored(ItemStack stack);
	EnergyInteractionResult setEnergy(ItemStack stack, Decimal amount);
	EnergyInteractionResult addEnergy(ItemStack stack, Decimal amount);
	EnergyInteractionResult removeEnergy(ItemStack stack, Decimal amount);
	<T extends IEnergizedItem> void transferEnergy(ItemStack self, T otherClass, ItemStack otherStack, Decimal amount, IEnergyContainer.Operation operation);
	void transferEnergy(ItemStack self, IEnergyContainer other, Decimal amount, IEnergyContainer.Operation operation);

	void equalizeWith(ItemStack self, List<IEnergyContainer> containers, List<Pair<IEnergizedItem, ItemStack>> stacks);
}
