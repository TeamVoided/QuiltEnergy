package team.voided.quiltenergy.energy.interaction;

import team.voided.quiltenergy.energy.EnergyUnit;
import team.voided.quiltenergy.numerics.Decimal;

public record EnergyInteractionResult(EnergyUnit unit, Decimal originalAmount, Decimal newAmount, boolean succeeded) { }
