package me.deathlord.quiltenergy.registry;

import me.deathlord.quiltenergy.QuiltEnergy;
import me.deathlord.quiltenergy.energy.EnergyUnit;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.util.registry.Registry;

public final class EnergyRegistries {
	public static final Registry<EnergyUnit> UNIT = FabricRegistryBuilder.createSimple(EnergyUnit.class, QuiltEnergy.modLoc("energy_unit_reg")).buildAndRegister();
}
