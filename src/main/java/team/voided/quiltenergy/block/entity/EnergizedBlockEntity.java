package team.voided.quiltenergy.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import team.voided.quiltenergy.energy.EnergyContainer;
import team.voided.quiltenergy.energy.EnergyUnit;
import team.voided.quiltenergy.energy.IEnergyContainer;

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
