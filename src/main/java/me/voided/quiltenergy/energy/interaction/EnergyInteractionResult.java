package me.voided.quiltenergy.energy.interaction;

import me.voided.quiltenergy.energy.EnergyUnit;

public record EnergyInteractionResult(EnergyUnit unit, double originalAmount, double newAmount, boolean succeeded) { }
