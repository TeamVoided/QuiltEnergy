package team.voided.quiltenergy.energy.interaction;

import team.voided.quiltenergy.energy.EnergyUnit;

public record EnergyInteractionResult(EnergyUnit unit, double originalAmount, double newAmount, boolean succeeded) { }
