package net.opentsdb.query.processor.expressions2.eval;

import com.google.common.math.DoubleMath;
import com.google.common.primitives.Doubles;
import java.util.Arrays;

public class DoubleArrayValue extends NumericValue<Double> {
    final double[] underlying;

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
    public ExpressionValue makeCopy() {
        return new DoubleArrayValue(Arrays.copyOf(underlying, underlying.length));
    }

    @Override
    public ExpressionValue negate() {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] = -underlying[i];
        }
        return this;
    }

    @Override
    public ExpressionValue add(final ExpressionValue value) {
        return value.add(this);
    }

    @Override
    public ExpressionValue add(final LongValue value) {
        final double addend = (double) value.getValue();
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] += addend;
        }
        return this;
    }

    @Override
    public ExpressionValue add(final DoubleValue value) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] += value.getValue();
        }
        return this;
    }

    @Override
    public ExpressionValue add(final LongArrayValue values) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] += (double) values.getValueAt(i);
        }
        return this;
    }

    @Override
    public ExpressionValue add(final DoubleArrayValue values) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] += values.getValueAt(i);
        }
        return this;
    }

    @Override
    public ExpressionValue subtract(final ExpressionValue value) {
        return value.negate().add(this);
    }

    @Override
    public ExpressionValue subtract(final LongValue value) {
        final double subtrahend = (double) value.getValue();
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] -= subtrahend;
        }
        return this;
    }

    @Override
    public ExpressionValue subtract(final DoubleValue value) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] -= value.getValue();
        }
        return this;
    }

    @Override
    public ExpressionValue subtract(final LongArrayValue values) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] -= (double) values.getValueAt(i);
        }
        return this;
    }

    @Override
    public ExpressionValue subtract(final DoubleArrayValue values) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] -= values.getValueAt(i);
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

            if (this.underlying.length != that.underlying.length) {
                return false;
            }

            for (int i = 0; i < this.underlying.length; ++i) {
                if (!DoubleMath.fuzzyEquals(this.underlying[i], that.underlying[i], EPSILON)) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "DoubleArrayValue{" + Doubles.join(",", underlying) + "}";
    }
}
