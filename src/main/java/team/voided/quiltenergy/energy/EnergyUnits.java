package team.voided.quiltenergy.energy;

import net.minecraft.core.Registry;
import team.voided.quiltenergy.QuiltEnergy;
import team.voided.quiltenergy.RGB;
import team.voided.quiltenergy.numerics.Decimal;
import team.voided.quiltenergy.registry.EnergyRegistries;

public final class EnergyUnits {
	public static final EnergyUnit RAW_ENERGY = new EnergyUnit(new Decimal("100"), QuiltEnergy.modLoc("raw_energy_unit"), new RGB(255, 255, 255));

	public static final EnergyUnit REDSTONE_FLUX = new EnergyUnit(new Decimal("1"), QuiltEnergy.modLoc("redstone_flux_unit"), new RGB(233, 22, 32));

	public static final EnergyUnit QUILT_ENERGY = new EnergyUnit(new Decimal("2"), QuiltEnergy.modLoc("quilt_energy_unit"), new RGB(185, 52, 203));

	public static void register() {
		Registry.register(EnergyRegistries.UNIT, RAW_ENERGY.id(), RAW_ENERGY);
		Registry.register(EnergyRegistries.UNIT, REDSTONE_FLUX.id(), REDSTONE_FLUX);
		Registry.register(EnergyRegistries.UNIT, QUILT_ENERGY.id(), QUILT_ENERGY);
	}
}
