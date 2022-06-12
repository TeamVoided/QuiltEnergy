package team.voided.quiltenergy.energy;

import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import team.voided.quiltenergy.HSV;
import team.voided.quiltenergy.QuiltEnergy;
import team.voided.quiltenergy.registry.EnergyRegistries;

public final class EnergyUnits {
	public static final EnergyUnit REDSTONE_FLUX = new EnergyUnit(1.0D, QuiltEnergy.modLoc("redstone_flux_unit"), Component.translatable("quilt_energy.unit.redstone_flux"), new HSV(0, 100, 100));
	public static final EnergyUnit PURE_AMETHYST = new EnergyUnit(4.0D, QuiltEnergy.modLoc("pure_amethyst_unit"), Component.translatable("quilt_energy.unit.pure_amethyst"), new HSV(295, 70, 100));

	public static final EnergyUnit QUILT_ENERGY = new EnergyUnit(2.0D, QuiltEnergy.modLoc("quilt_energy_unit"), Component.translatable("quilt_energy.unit.quilt_energy"), new HSV(273, 47, 100));

	public static void register() {
		Registry.register(EnergyRegistries.UNIT, REDSTONE_FLUX.id(), REDSTONE_FLUX);
		Registry.register(EnergyRegistries.UNIT, PURE_AMETHYST.id(), PURE_AMETHYST);
		Registry.register(EnergyRegistries.UNIT, QUILT_ENERGY.id(), QUILT_ENERGY);
	}
}
