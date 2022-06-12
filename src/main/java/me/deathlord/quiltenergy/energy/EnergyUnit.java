package me.deathlord.quiltenergy.energy;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Objects;

public class EnergyUnit {
	private final double value;
	private final Identifier id;
	private final Text name;

	public EnergyUnit(double value, Identifier id, Text name) {
		this.value = value;
		this.id = id;
		this.name = name;
	}

	public double convertTo(EnergyUnit other, double amount) {
		return amount * (this.value / other.value());
	}

	public double convertFrom(EnergyUnit other, double amount) {
		return other.convertTo(this, amount);
	}

	public double value() {
		return value;
	}

	public Identifier id() {
		return id;
	}

	public Text getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		EnergyUnit that = (EnergyUnit) o;
		return Double.compare(that.value, value) == 0 && Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value, id);
	}
}
