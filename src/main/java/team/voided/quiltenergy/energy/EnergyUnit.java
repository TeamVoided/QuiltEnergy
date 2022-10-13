package team.voided.quiltenergy.energy;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import team.voided.quiltenergy.RGB;
import team.voided.quiltenergy.numerics.Decimal;

import java.util.Objects;

public class EnergyUnit {
	private final Decimal value;
	private final ResourceLocation id;
	private final Component name;

	private final RGB energyBarColor;

	public EnergyUnit(Decimal value, ResourceLocation id, RGB energyBarColor) {
		this.value = value;
		this.id = id;
		this.name = Component.translatable(id.getNamespace() + ".unit." + id.getPath());
		this.energyBarColor = energyBarColor;
	}

	public Decimal convertTo(EnergyUnit other, Decimal amount) {
		return amount.multiply(this.value.divide(other.value(), false), false);
	}

	public Decimal convertFrom(EnergyUnit other, Decimal amount) {
		return other.convertTo(this, amount);
	}

	public Decimal value() {
		return value;
	}

	public ResourceLocation id() {
		return id;
	}

	public Component getName() {
		return name;
	}

	public RGB getEnergyBarColor() {
		return energyBarColor;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		EnergyUnit that = (EnergyUnit) o;
		return value.equals(that.value) && Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value, id);
	}
}
