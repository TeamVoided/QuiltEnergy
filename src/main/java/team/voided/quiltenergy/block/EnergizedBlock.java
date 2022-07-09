package team.voided.quiltenergy.block;

import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import team.voided.quiltenergy.block.entity.EnergizedBlockEntity;
import team.voided.quiltenergy.item.IEnergizedItem;

import javax.annotation.ParametersAreNonnullByDefault;

public abstract class EnergizedBlock extends Block {
	public EnergizedBlock(Properties properties) {
		super(properties);
	}

	@Override
	@ParametersAreNonnullByDefault
	public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
		if (shouldDropWithEnergy() && blockEntity instanceof EnergizedBlockEntity entity && entity.getBlockState().getBlock().asItem() instanceof IEnergizedItem item) {
			ItemStack replace = new ItemStack(entity.getBlockState().getBlock(), 1);

			player.awardStat(Stats.BLOCK_MINED.get(this));
			player.causeFoodExhaustion(0.005F);

			item.setEnergy(replace, entity.getContainer().stored());

			popResource(level, pos, replace);

			return;
		}
		super.playerDestroy(level, player, pos, state, blockEntity, stack);
	}

	protected abstract boolean shouldDropWithEnergy();
}
