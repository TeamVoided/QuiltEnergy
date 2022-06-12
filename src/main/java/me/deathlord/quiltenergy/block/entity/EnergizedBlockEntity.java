package me.deathlord.quiltenergy.block.entity;

import me.deathlord.quiltenergy.energy.EnergyContainer;
import me.deathlord.quiltenergy.energy.EnergyUnit;
import me.deathlord.quiltenergy.energy.IEnergyContainer;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class EnergizedBlockEntity extends BlockEntity {
	private final IEnergyContainer container;

	public EnergizedBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, IEnergyContainer container) {
		super(blockEntityType, blockPos, blockState);
		this.container = container;
	}

	public EnergizedBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, EnergyUnit unit, double maxCapacity) {
		super(blockEntityType, blockPos, blockState);
		this.container = new EnergyContainer(unit, maxCapacity);
	}

	public IEnergyContainer getContainer() {
		return container;
	}
}
