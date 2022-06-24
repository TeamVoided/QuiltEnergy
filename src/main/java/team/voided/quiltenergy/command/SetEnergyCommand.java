package team.voided.quiltenergy.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import team.voided.quiltenergy.item.IEnergizedItem;

import java.util.Collection;

import static team.voided.quiltenergy.QuiltEnergy.formatDouble;

public class SetEnergyCommand {
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext) {
		dispatcher.register(Commands.literal("setenergy")
				.requires((source) -> source.hasPermission(2))
				.then(Commands.argument("targets", EntityArgument.players())
						.then(Commands.argument("energy", DoubleArgumentType.doubleArg())
								.executes(SetEnergyCommand::setEnergy))));
	}

	private static int setEnergy(CommandContext<CommandSourceStack> source) throws CommandSyntaxException {
		Collection<ServerPlayer> players = EntityArgument.getPlayers(source, "targets");
		double energy = DoubleArgumentType.getDouble(source, "energy");

		for (ServerPlayer player : players) {
			ItemStack stackInHand = player.getItemInHand(InteractionHand.MAIN_HAND);

			if (!(stackInHand.getItem() instanceof IEnergizedItem item)) {
				source.getSource().sendFailure(Component.translatable("commands.set.energy.not_energized_item", player.getName().getString()));
				return 0;
			}

			item.setStored(stackInHand, energy);
		}

		source.getSource().sendSuccess(Component.translatable("commands.set.energy.success", formatDouble(energy, 64), players.size()), true);

		return 1;
	}
}
