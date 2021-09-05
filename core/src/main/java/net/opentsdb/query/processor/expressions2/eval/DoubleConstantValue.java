package net.opentsdb.query.processor.expressions2.eval;

import com.google.common.math.DoubleMath;

public class DoubleConstantValue extends NumericValue<Double> {
    double underlying;

    public DoubleConstantValue(final double value) {
        this.underlying = value;
    }

    public double getValue() {
        return underlying;
    }

    @Override
    public Value makeCopy() {
        return new DoubleConstantValue(underlying);
    }

    @Override
    public Value negate() {
        underlying = -underlying;
        return this;
    }

    @Override
    public Value add(final Value value) {
        return value.add(this);
    }

    @Override
    public Value add(final LongConstantValue value) {
        this.underlying += (double) value.getValue();
        return this;
    }

    @Override
    public Value add(final DoubleConstantValue value) {
        this.underlying += value.underlying;
        return this;
    }

    @Override
    public Value add(final LongArrayValue values) {
        if (DoubleMath.isMathematicalInteger(underlying)) {
            // This double constant is representable as a long.
            final long addend = (long) underlying;
            for (int i = 0; i < values.underlying.length; ++i) {
                values.underlying[i] += addend;
            }
            return values;
        } else {
            // Must convert to double array.
            final long[] longs = values.underlying;
            final double[] doubles = new double[longs.length];
            for (int i = 0; i < longs.length; ++i) {
                doubles[i] = underlying + (double) longs[i];
            }
            return new DoubleArrayValue(doubles);
        }
    }

    @Override
    public Value add(final DoubleArrayValue values) {
        return values.add(this);
    }

    @Override
    public Value subtract(final Value value) {
        return value.negate().add(this);
    }

    @Override
    public Value subtract(final LongConstantValue value) {
        this.underlying -= (double) value.getValue();
        return this;
    }

    @Override
    public Value subtract(final DoubleConstantValue value) {
        this.underlying -= value.underlying;
        return this;
    }

    @Override
    public Value subtract(final LongArrayValue values) {
        final long[] longs = values.getUnderlying();
        final double[] doubles = new double[longs.length];
        for (int i = 0; i < longs.length; ++i) {
            doubles[i] = underlying - (double) longs[i];
        }
        return new DoubleArrayValue(doubles);
    }

    @Override
    public Value subtract(final DoubleArrayValue values) {
        return values.negate().add(this);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (other == null) {
            return false;
        }

        if (this.getClass() == other.getClass()) {
            final DoubleConstantValue that = (DoubleConstantValue) other;
            return this.underlying == that.underlying;
        }

        return false;
    }

    @Override
    public String toString() {
        return "DoubleConstantValue{" + underlying + "}";
    }
}
