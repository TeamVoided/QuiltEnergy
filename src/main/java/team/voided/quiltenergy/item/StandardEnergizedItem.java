package team.voided.quiltenergy.item;

import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import team.voided.quiltenergy.client.gui.EnergyBarTooltipData;
import team.voided.quiltenergy.energy.EnergyUnits;
import team.voided.quiltenergy.energy.IEnergyContainer;
import team.voided.quiltenergy.energy.interaction.EnergyInteractionResult;
import team.voided.quiltenergy.numerics.Decimal;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Optional;

import static team.voided.quiltenergy.QuiltEnergy.formatDouble;

public interface StandardEnergizedItem extends IEnergizedItem {

	@ParametersAreNonnullByDefault
	default void appendHoverTextDefault(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
		if (Screen.hasShiftDown()) {
			tooltip.add(Component.translatable("quilt_energy.energyitem.unit", unit().getName()).withStyle(ChatFormatting.LIGHT_PURPLE));
			tooltip.add(Component.translatable("quilt_energy.energyitem.max_capacity", formatDouble(getMaxCapacity().doubleValue(), 64)).withStyle(ChatFormatting.LIGHT_PURPLE));
			tooltip.add(Component.translatable("quilt_energy.energyitem.stored", formatDouble(stored(stack).doubleValue(), 64)).withStyle(ChatFormatting.LIGHT_PURPLE));
		} else {
			tooltip.add(Component.translatable("quilt_energy.item.shift_to_expand").withStyle(ChatFormatting.LIGHT_PURPLE));
		}
	}

	@ParametersAreNonnullByDefault
	default Optional<TooltipComponent> getTooltipImageDefault(ItemStack stack) {
		return EnergyBarTooltipData.fromEnergizedItem(stack);
	}

	@Override
	default Decimal stored(ItemStack stack) {
		if (!stack.getOrCreateTag().contains("stored")) {
			Decimal.NBTHelper.writeToCompound(stack.getOrCreateTag(), "stored", new Decimal("0"));
		}

		return Decimal.NBTHelper.readFromCompound(stack.getOrCreateTag(), "stored");
	}

	@Override
	default EnergyInteractionResult setEnergy(ItemStack stack, Decimal amount) {
		Decimal original = stored(stack);

		if (amount.lessThan(new Decimal("0"))) return new EnergyInteractionResult(unit(), original, original, false);
		if (amount.greaterThan(getMaxCapacity())) return new EnergyInteractionResult(unit(), original, original, false);

		Decimal.NBTHelper.writeToCompound(stack.getOrCreateTag(), "stored", amount);
		return new EnergyInteractionResult(unit(), original, amount, true);
	}

	@Override
	default EnergyInteractionResult addEnergy(ItemStack stack, Decimal amount) {
		Decimal original = stored(stack);

		if (original.add(amount, false).greaterThan(getMaxCapacity())) return new EnergyInteractionResult(unit(), original, original, false);

		setEnergy(stack, original.add(amount, false));
		return new EnergyInteractionResult(unit(), original, (original.add(amount, false)), true);
	}

	@Override
	default EnergyInteractionResult removeEnergy(ItemStack stack, Decimal amount) {
		Decimal original = stored(stack);

		if (original.subtract(amount, false).lessThan(new Decimal(""))) return new EnergyInteractionResult(unit(), original, original, false);

		setEnergy(stack, original.subtract(amount, false));
		return new EnergyInteractionResult(unit(), original, (original.subtract(amount, false)), true);
	}

	@Override
	default <T extends IEnergizedItem> void transferEnergy(ItemStack self, T otherClass, ItemStack otherStack, Decimal amount, IEnergyContainer.Operation operation) {
		if (operation == IEnergyContainer.Operation.RECEIVE) {
			this.addEnergy(self, otherClass.unit().convertTo(this.unit(), amount));
			this.removeEnergy(otherStack, this.unit().convertTo(otherClass.unit(), amount));
		} else {
			this.removeEnergy(self, otherClass.unit().convertTo(this.unit(), amount));
			this.addEnergy(otherStack, this.unit().convertTo(otherClass.unit(), amount));
		}
	}

	@Override
	default void transferEnergy(ItemStack self, IEnergyContainer other, Decimal amount, IEnergyContainer.Operation operation) {
		if (operation == IEnergyContainer.Operation.RECEIVE) {
			this.addEnergy(self, other.unit().convertTo(this.unit(), amount));
			other.removeEnergy(this.unit().convertTo(other.unit(), amount));
		} else {
			this.removeEnergy(self, other.unit().convertTo(this.unit(), amount));
			other.addEnergy(this.unit().convertTo(other.unit(), amount));
		}
	}

	@Override
	default void equalizeWith(ItemStack self, List<IEnergyContainer> containers, List<Pair<IEnergizedItem, ItemStack>> stacks) {
		Decimal total = EnergyUnits.RAW_ENERGY.convertFrom(unit(), stored(self));

		for (IEnergyContainer container : containers) {
			total.add(EnergyUnits.RAW_ENERGY.convertFrom(container.unit(), container.stored()), true);
		}

		for (Pair<IEnergizedItem, ItemStack> item : stacks) {
			total.add(EnergyUnits.RAW_ENERGY.convertFrom(item.getFirst().unit(), item.getFirst().stored(item.getSecond())), true);
		}

		Decimal set = total.divide(((Integer) (containers.size() + stacks.size())).toString(), false);

		for (IEnergyContainer container : containers) {
			container.setEnergy(container.unit().convertFrom(EnergyUnits.RAW_ENERGY, set));
		}

		for (Pair<IEnergizedItem, ItemStack> item : stacks) {
			item.getFirst().setEnergy(item.getSecond(), item.getFirst().unit().convertFrom(EnergyUnits.RAW_ENERGY, set));
		}
	}
}
