package team.voided.quiltenergy.block.entity;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;

public interface BlockEntityContainer extends Container {
	NonNullList<ItemStack> getInventory();
	boolean changed();

	@Override
	default int getContainerSize() {
		return getInventory().size();
	}

	@Override
	default boolean isEmpty() {
		return getInventory().isEmpty();
	}

	@Override
	default ItemStack getItem(int slot) {
		return getInventory().get(slot);
	}

	@Override
	default ItemStack removeItem(int slot, int amount) {
		ItemStack stack = getInventory().get(slot);
		stack.shrink(amount);

		setChanged();

		return stack;
	}

	@Override
	default ItemStack removeItemNoUpdate(int slot) {
		return getInventory().remove(slot);
	}

	@Override
	@ParametersAreNonnullByDefault
	default void setItem(int slot, ItemStack stack) {
		getInventory().set(slot, stack);
		setChanged();
	}

	@Override
	default int getMaxStackSize() {
		return Container.super.getMaxStackSize();
	}

	@Override
	@ParametersAreNonnullByDefault
	default boolean stillValid(Player player) {
		return true;
	}

	@Override
	default void clearContent() {
		getInventory().clear();
	}
}
