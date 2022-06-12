package me.deathlord.quiltenergy.energy.interaction;

import me.deathlord.quiltenergy.energy.EnergyUnit;

public record EnergyInteractionResult(EnergyUnit unit, double originalAmount, double newAmount, boolean succeeded) { }
