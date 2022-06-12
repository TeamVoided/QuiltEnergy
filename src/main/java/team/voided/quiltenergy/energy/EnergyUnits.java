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

import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import team.voided.quiltenergy.HSV;
import team.voided.quiltenergy.QuiltEnergy;
import team.voided.quiltenergy.registry.EnergyRegistries;

public final class EnergyUnits {
	public static final EnergyUnit REDSTONE_FLUX = new EnergyUnit(1.0D, QuiltEnergy.modLoc("redstone_flux_unit"), Component.translatable("quilt_energy.unit.redstone_flux"), new HSV(0, 100, 100));
	public static final EnergyUnit PURE_AMETHYST = new EnergyUnit(4.0D, QuiltEnergy.modLoc("pure_amethyst_unit"), Component.translatable("quilt_energy.unit.pure_amethyst"), new HSV(295, 70, 100));

	public static void register() {
		Registry.register(EnergyRegistries.UNIT, REDSTONE_FLUX.id(), REDSTONE_FLUX);
		Registry.register(EnergyRegistries.UNIT, PURE_AMETHYST.id(), PURE_AMETHYST);
	}
}
