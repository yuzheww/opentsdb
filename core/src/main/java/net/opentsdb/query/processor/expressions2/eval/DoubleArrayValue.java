package net.opentsdb.query.processor.expressions2.eval;

import java.util.Arrays;

public class DoubleArrayValue implements ArrayValue<Double> {
    private final double[] underlying;

    public DoubleArrayValue(final double[] values) {
        underlying = values;
    }

    public double getValueAt(int idx) {
        return underlying[idx];
    }

    public double[] getUnderlying() {
        return underlying;
    }

    @Override
    public Value makeCopy() {
        return new DoubleArrayValue(Arrays.copyOf(underlying, underlying.length));
    }

    @Override
    public Value add(final Value value) {
        return value.add(this);
    }

    @Override
    public Value add(final LongConstantValue value) {
        final double addend = (double) value.getValue();
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] += addend;
        }
        return this;
    }

    @Override
    public Value add(final DoubleConstantValue value) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] += value.getValue();
        }
        return this;
    }

    @Override
    public Value add(final LongArrayValue values) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] += (double) values.getValueAt(i);
        }
        return this;
    }

    @Override
    public Value add(final DoubleArrayValue values) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] += values.getValueAt(i);
        }
        return this;
    }

    @Override
    public Value subtract(final Value value) {
        return value.negate().add(this);
    }

    @Override
    public Value subtract(final LongConstantValue value) {
        final double subtrahend = (double) value.getValue();
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] -= subtrahend;
        }
        return this;
    }

    @Override
    public Value subtract(final DoubleConstantValue value) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] -= value.getValue();
        }
        return this;
    }

    @Override
    public Value subtract(final LongArrayValue values) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] -= (double) values.getValueAt(i);
        }
        return this;
    }

    @Override
    public Value subtract(final DoubleArrayValue values) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] -= values.getValueAt(i);
        }
        return this;
    }

    @Override
    public Value negate() {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] = -underlying[i];
        }
        return this;
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
            final DoubleArrayValue that = (DoubleArrayValue) other;
            return Arrays.equals(this.underlying, that.underlying);
        }

        return false;
    }
}
