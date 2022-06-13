package team.voided.quiltenergy.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;
import team.voided.quiltenergy.QuiltEnergy;
import team.voided.quiltenergy.energy.EnergyUnit;
import team.voided.quiltenergy.energy.IEnergyContainer;
import team.voided.quiltenergy.energy.interaction.EnergyInteractionResult;
import team.voided.quiltenergy.registry.EnergyRegistries;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class EnergizedBlockItem extends BlockItem implements IEnergizedItem {
	private EnergyUnit unit;
	private double maxCapacity;
	private final double preStoredEnergy;

	public EnergizedBlockItem(Block block, Properties properties, EnergyUnit unit, double maxCapacity, double preStoredEnergy) {
		super(block, properties);
		this.unit = unit;
		this.maxCapacity = maxCapacity;
		this.preStoredEnergy = preStoredEnergy;
	}

	@Override
	@ParametersAreNonnullByDefault
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
		if (Screen.hasShiftDown()) {
			tooltip.add(Component.translatable("quilt_energy.energyitem.unit", getUnit(stack).getName()).withStyle(ChatFormatting.LIGHT_PURPLE));
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
	@ParametersAreNonnullByDefault
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		if (setVars(stack)) {
			setUnit(stack, unit);
			setMaxCapacity(stack, maxCapacity);
			setStored(stack, preStoredEnergy);
			QuiltEnergy.LOGGER.info("updated vars");
		}
	}

	@Override
	public boolean setVars(ItemStack stack) {
		boolean ret = !stack.getOrCreateTag().contains("set_vars");

		if (ret) stack.getOrCreateTag().putBoolean("set_vars", false);

		return ret || stack.getOrCreateTag().getBoolean("set_vars");
	}

	@Override
	public void setUnit(ItemStack stack, EnergyUnit unit) {
		stack.getOrCreateTag().putString("energy_unit", unit.id().toString());
		QuiltEnergy.LOGGER.info(unit.id().toString());
		this.unit = unit;
	}

	@Override
	public EnergyUnit getUnit(ItemStack stack) {
		String[] split = stack.getOrCreateTag().getString("energy_unit").split(":");
		return EnergyRegistries.UNIT.get(new ResourceLocation(split[0], split[1]));
	}

	@Override
	public void setMaxCapacity(ItemStack stack, double maxCapacity) {
		stack.getOrCreateTag().putDouble("max_energy_capacity", maxCapacity);
		this.maxCapacity = maxCapacity;
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

		if (amount > getMaxCapacity(stack)) return new EnergyInteractionResult(getUnit(stack), original, original, false);

		stack.getOrCreateTag().putDouble("stored", amount);
		return new EnergyInteractionResult(getUnit(stack), original, amount, true);
	}

	@Override
	public EnergyInteractionResult addEnergy(ItemStack stack, double amount) {
		double original = getStored(stack);

		if ((original + amount) > getMaxCapacity(stack)) return new EnergyInteractionResult(getUnit(stack), original, original, false);

		setStored(stack, (original + amount));
		return new EnergyInteractionResult(getUnit(stack), original, (original + amount), true);
	}

	@Override
	public EnergyInteractionResult removeEnergy(ItemStack stack, double amount) {
		double original = getStored(stack);

		if ((original - amount) < 0) return new EnergyInteractionResult(getUnit(stack), original, original, false);

		setStored(stack, (original - amount));
		return new EnergyInteractionResult(getUnit(stack), original, (original - amount), true);
	}

	@Override
	public void transferEnergy(ItemStack self, ItemStack other, double amount, IEnergyContainer.Operation operation) {
		if (operation == IEnergyContainer.Operation.RECEIVE) {
			this.addEnergy(self, this.getUnit(other).convertTo(this.getUnit(self), amount));
			this.removeEnergy(other, this.getUnit(self).convertTo(this.getUnit(other), amount));
		} else {
			this.removeEnergy(self, this.getUnit(other).convertTo(this.getUnit(self), amount));
			this.addEnergy(other, this.getUnit(self).convertTo(this.getUnit(other), amount));
		}
	}

	@Override
	public void transferEnergy(ItemStack self, IEnergyContainer other, double amount, IEnergyContainer.Operation operation) {
		if (operation == IEnergyContainer.Operation.RECEIVE) {
			this.addEnergy(self, other.unit().convertTo(this.getUnit(self), amount));
			other.removeEnergy(this.getUnit(self).convertTo(other.unit(), amount));
		} else {
			this.removeEnergy(self, other.unit().convertTo(this.getUnit(self), amount));
			other.addEnergy(this.getUnit(self).convertTo(other.unit(), amount));
		}
	}
}
