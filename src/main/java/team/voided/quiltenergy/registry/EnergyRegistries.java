package team.voided.quiltenergy.registry;

import team.voided.quiltenergy.QuiltEnergy;
import team.voided.quiltenergy.energy.EnergyUnit;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;

public final class EnergyRegistries {
	public static final Registry<EnergyUnit> UNIT = FabricRegistryBuilder.createSimple(EnergyUnit.class, QuiltEnergy.modLoc("energy_unit_reg")).buildAndRegister();
}
