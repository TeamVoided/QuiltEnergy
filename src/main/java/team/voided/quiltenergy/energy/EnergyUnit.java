/*
The MIT License (MIT)

Copyright (c) 2022 TeamVoided

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/

package team.voided.quiltenergy.energy;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import team.voided.quiltenergy.HSV;

import java.util.Objects;

public class EnergyUnit {
	private final double value;
	private final ResourceLocation id;
	private final Component name;

	private final HSV energyBarColor;

	public EnergyUnit(double value, ResourceLocation id, Component name, HSV energyBarColor) {
		this.value = value;
		this.id = id;
		this.name = name;
		this.energyBarColor = energyBarColor;
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

	public ResourceLocation id() {
		return id;
	}

	public Component getName() {
		return name;
	}

	public HSV getEnergyBarColor() {
		return energyBarColor;
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
