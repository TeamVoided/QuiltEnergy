package team.voided.quiltenergy.numerics;

import net.minecraft.nbt.CompoundTag;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Decimal extends Number implements Comparable<Decimal> {
	private BigDecimal decimal;

	public Decimal(String val) {
		decimal = new BigDecimal(val);
	}

	public Decimal(BigDecimal decimal) {
		this.decimal = decimal;
	}

	public Decimal add(String val, boolean set) {
		if (set) {
			decimal = decimal.add(new BigDecimal(val));
			return this;
		}

		return new Decimal(decimal.add(new BigDecimal(val)));
	}

	public Decimal add(Decimal val, boolean set) {
		if (set) {
			decimal = decimal.add(val.decimal);
			return this;
		}

		return new Decimal(decimal.add(val.decimal));
	}

	public Decimal subtract(String val, boolean set) {
		if (set) {
			decimal = decimal.subtract(new BigDecimal(val));
			return this;
		}

		return new Decimal(decimal.subtract(new BigDecimal(val)));
	}

	public Decimal subtract(Decimal val, boolean set) {
		if (set) {
			decimal = decimal.subtract(val.decimal);
			return this;
		}

		return new Decimal(decimal.subtract(val.decimal));
	}

	public Decimal multiply(String val, boolean set) {
		if (set) {
			decimal = decimal.multiply(new BigDecimal(val));
			return this;
		}

		return new Decimal(decimal.multiply(new BigDecimal(val)));
	}

	public Decimal multiply(Decimal val, boolean set) {
		if (set) {
			decimal = decimal.multiply(val.decimal);
			return this;
		}

		return new Decimal(decimal.multiply(val.decimal));
	}

	public Decimal divide(String val, boolean set) {
		divide(val, set, RoundingMode.UNNECESSARY);
		return this;
	}

	public Decimal divide(Decimal val, boolean set) {
		divide(val, set, RoundingMode.UNNECESSARY);
		return this;
	}

	public Decimal divide(String val, boolean set, RoundingMode rm) {
		if (set) {
			decimal = decimal.divide(new BigDecimal(val), rm);
			return this;
		}

		return new Decimal(decimal.divide(new BigDecimal(val), rm));
	}

	public Decimal divide(Decimal val, boolean set, RoundingMode rm) {
		if (set) {
			decimal = decimal.divide(val.decimal, rm);
			return this;
		}

		return new Decimal(decimal.divide(val.decimal, rm));
	}

	public Decimal abs() {
		return new Decimal(decimal.abs());
	}

	public Decimal abs(MathContext context) {
		return new Decimal(decimal.abs(context));
	}

	public Decimal setToAbs() {
		decimal = abs().getDecimal();
		return this;
	}

	public Decimal setToAbs(MathContext context) {
		decimal = abs(context).getDecimal();
		return this;
	}

	public Decimal max(String val, boolean set) {
		if (set) {
			decimal = decimal.max(new BigDecimal(val));
			return this;
		}

		return new Decimal(decimal.max(new BigDecimal(val)));
	}

	public Decimal max(Decimal val, boolean set) {
		if (set) {
			decimal = decimal.max(val.decimal);
			return this;
		}

		return new Decimal(decimal.max(val.decimal));
	}

	public Decimal min(String val, boolean set) {
		if (set) {
			decimal = decimal.min(new BigDecimal(val));
			return this;
		}

		return new Decimal(decimal.min(new BigDecimal(val)));
	}

	public Decimal min(Decimal val, boolean set) {
		if (set) {
			decimal = decimal.min(val.decimal);
			return this;
		}

		return new Decimal(decimal.min(val.decimal));
	}

	public Decimal pow(int val, boolean set) {
		if (set) {
			decimal = decimal.pow(val);
			return this;
		}

		return new Decimal(decimal.pow(val));
	}

	public Decimal pow(int val, boolean set, MathContext context) {
		if (set) {
			decimal = decimal.pow(val, context);
			return this;
		}

		return new Decimal(decimal.pow(val, context));
	}

	public Decimal round(MathContext context, boolean set) {
		if (set) {
			decimal = decimal.round(context);
			return this;
		}

		return new Decimal(decimal.round(context));
	}

	public int precision() {
		return decimal.precision();
	}

	public int scale() {
		return decimal.scale();
	}

	public Decimal stripTrailingZeros() {
		decimal = decimal.stripTrailingZeros();
		return this;
	}

	@Override
	public int compareTo(Decimal decimal) {
		return this.decimal.compareTo(decimal.decimal);
	}

	public boolean greaterThan(Decimal decimal) {
		return compareTo(decimal) > 0;
	}

	public boolean lessThan(Decimal decimal) {
		return compareTo(decimal) < 0;
	}

	public boolean equals(Decimal decimal) {
		return compareTo(decimal) == 0;
	}

	public boolean greaterThanOrEqualTo(Decimal decimal) {
		return compareTo(decimal) >= 0;
	}

	public boolean lessThanOrEqualTo(Decimal decimal) {
		return compareTo(decimal) <= 0;
	}

	public BigDecimal getDecimal() {
		return decimal;
	}

	@Override
	public int intValue() {
		return decimal.intValueExact();
	}

	@Override
	public long longValue() {
		return decimal.longValueExact();
	}

	@Override
	public float floatValue() {
		return decimal.floatValue();
	}

	@Override
	public double doubleValue() {
		return decimal.doubleValue();
	}

	@Override
	public String toString() {
		return decimal.toPlainString();
	}

	public static class NBTHelper {
		public static void writeToCompound(CompoundTag compound, String key, Decimal decimal) {
			compound.putString(key, decimal.toString());
		}

		public static Decimal readFromCompound(CompoundTag compound, String key) {
			return new Decimal(compound.getString(key));
		}
	}
}
