package me.deathlord.quiltenergy.energy;

import me.deathlord.quiltenergy.QuiltEnergy;
import me.deathlord.quiltenergy.registry.EnergyRegistries;
import net.minecraft.text.Text;
import net.minecraft.util.registry.Registry;

public final class EnergyUnits {
	public static final EnergyUnit REDSTONE_FLUX = new EnergyUnit(1.0D, QuiltEnergy.modLoc("redstone_flux_unit"), Text.translatable("quilt_energy.unit.redstone_flux"));
	public static final EnergyUnit PURE_AMETHYST = new EnergyUnit(4.0D, QuiltEnergy.modLoc("pure_amethyst_unit"), Text.translatable("quilt_energy.unit.pure_amethyst"));

	public static void register() {
		Registry.register(EnergyRegistries.UNIT, REDSTONE_FLUX.id(), REDSTONE_FLUX);
		Registry.register(EnergyRegistries.UNIT, PURE_AMETHYST.id(), PURE_AMETHYST);
	}
}
