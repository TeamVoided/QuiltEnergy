package team.voided.quiltenergy.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;
import team.voided.quiltenergy.energy.EnergyUnit;
import team.voided.quiltenergy.energy.IEnergyContainer;
import team.voided.quiltenergy.energy.interaction.EnergyInteractionResult;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class EnergizedBlockItem extends BlockItem implements IEnergizedItem {
	private EnergyUnit unit;

	public EnergizedBlockItem(Block block, Properties properties, EnergyUnit unit) {
		super(block, properties);
		this.unit = unit;
	}

	@Override
	@ParametersAreNonnullByDefault
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
		if (Screen.hasShiftDown()) {
			tooltip.add(Component.translatable("quilt_energy.energyitem.unit", getUnit().getName()).withStyle(ChatFormatting.LIGHT_PURPLE));
			tooltip.add(Component.translatable("quilt_energy.energyitem.max_capacity", getMaxCapacity(stack)).withStyle(ChatFormatting.LIGHT_PURPLE));
			tooltip.add(Component.translatable("quilt_energy.energyitem.stored", getStored(stack)).withStyle(ChatFormatting.LIGHT_PURPLE));
		} else {
			tooltip.add(Component.translatable("quilt_energy.item.shift_to_expand").withStyle(ChatFormatting.LIGHT_PURPLE));
		}
	}

	/*
	Quilt API is broken

	@Override
	@ParametersAreNonnullByDefault
	public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
		return Optional.of(EnergyBarTooltipData.fromEnergizedItem(stack));
	}
	*/

	@Override
	public void setUnit(EnergyUnit unit) {
		this.unit = unit;
	}

	@Override
	public EnergyUnit getUnit() {
		return unit;
	}

	@Override
	public void setMaxCapacity(ItemStack stack, double maxCapacity) {
		stack.getOrCreateTag().putDouble("max_energy_capacity", maxCapacity);
	}

	@Override
	public double getMaxCapacity(ItemStack stack) {
		return stack.getOrCreateTag().getDouble("max_energy_capacity");
	}

	@Override
	public double getStored(ItemStack stack) {
		return stack.getOrCreateTag().getDouble("stored");
	}

	@Override
	public EnergyInteractionResult setStored(ItemStack stack, double amount) {
		double original = getStored(stack);

		if (amount > getMaxCapacity(stack)) return new EnergyInteractionResult(getUnit(), original, original, false);

		stack.getOrCreateTag().putDouble("stored", amount);
		return new EnergyInteractionResult(getUnit(), original, amount, true);
	}

	@Override
	public EnergyInteractionResult addEnergy(ItemStack stack, double amount) {
		double original = getStored(stack);

		if ((original + amount) > getMaxCapacity(stack)) return new EnergyInteractionResult(getUnit(), original, original, false);

		setStored(stack, (original + amount));
		return new EnergyInteractionResult(getUnit(), original, (original + amount), true);
	}

	@Override
	public EnergyInteractionResult removeEnergy(ItemStack stack, double amount) {
		double original = getStored(stack);

		if ((original - amount) < 0) return new EnergyInteractionResult(getUnit(), original, original, false);

		setStored(stack, (original - amount));
		return new EnergyInteractionResult(getUnit(), original, (original - amount), true);
	}

	@Override
	public <T extends IEnergizedItem> void transferEnergy(ItemStack self, T otherClass, ItemStack otherStack, double amount, IEnergyContainer.Operation operation) {
		if (operation == IEnergyContainer.Operation.RECEIVE) {
			this.addEnergy(self, otherClass.getUnit().convertTo(this.getUnit(), amount));
			this.removeEnergy(otherStack, this.getUnit().convertTo(otherClass.getUnit(), amount));
		} else {
			this.removeEnergy(self, otherClass.getUnit().convertTo(this.getUnit(), amount));
			this.addEnergy(otherStack, this.getUnit().convertTo(otherClass.getUnit(), amount));
		}
	}

	@Override
	public void transferEnergy(ItemStack self, IEnergyContainer other, double amount, IEnergyContainer.Operation operation) {
		if (operation == IEnergyContainer.Operation.RECEIVE) {
			this.addEnergy(self, other.unit().convertTo(this.getUnit(), amount));
			other.removeEnergy(this.getUnit().convertTo(other.unit(), amount));
		} else {
			this.removeEnergy(self, other.unit().convertTo(this.getUnit(), amount));
			other.addEnergy(this.getUnit().convertTo(other.unit(), amount));
		}
	}
}
