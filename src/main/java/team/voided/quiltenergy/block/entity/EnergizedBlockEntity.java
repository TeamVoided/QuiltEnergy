package team.voided.quiltenergy.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import team.voided.quiltenergy.energy.EnergyContainer;
import team.voided.quiltenergy.energy.EnergyUnit;
import team.voided.quiltenergy.energy.IEnergyContainer;
import team.voided.quiltenergy.numerics.Decimal;

import java.util.HashMap;
import java.util.Map;

public class EnergizedBlockEntity extends BlockEntity {
	private final IEnergyContainer container;
	private final Map<Direction, Boolean> allowEnergyTransfer = new HashMap<>();

	public EnergizedBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, IEnergyContainer container, Direction... energyTransferAllowed) {
		super(blockEntityType, blockPos, blockState);
		this.container = container;
		defaultAllow();
		for (Direction direction : energyTransferAllowed) {
			allowEnergyTransfer.put(direction, true);
		}
	}

	public EnergizedBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, EnergyUnit unit, Decimal maxCapacity, Direction... energyTransferAllowed) {
		super(blockEntityType, blockPos, blockState);
		this.container = new EnergyContainer(unit, maxCapacity);
		defaultAllow();
		for (Direction direction : energyTransferAllowed) {
			allowEnergyTransfer.put(direction, true);
		}
	}

	private void defaultAllow() {
		allowEnergyTransfer.put(Direction.DOWN, false);
		allowEnergyTransfer.put(Direction.UP, false);
		allowEnergyTransfer.put(Direction.NORTH, false);
		allowEnergyTransfer.put(Direction.SOUTH, false);
		allowEnergyTransfer.put(Direction.WEST, false);
		allowEnergyTransfer.put(Direction.EAST, false);
	}

	public boolean energyTransferAllowedForDirection(Direction direction) {
		return allowEnergyTransfer.get(direction);
	}

	public IEnergyContainer getContainer() {
		return container;
	}
}
