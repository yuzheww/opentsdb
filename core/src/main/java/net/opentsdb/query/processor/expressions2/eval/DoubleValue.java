package net.opentsdb.query.processor.expressions2.eval;

import com.google.common.math.DoubleMath;

public class DoubleValue extends NumericValue<Double> {
    public static final DoubleValue NAN = new DoubleValue(Double.NaN);

    double underlying;

    public DoubleValue(final double value) {
        this.underlying = value;
    }

    public double getValue() {
        return underlying;
    }

    @Override
    public void close() { }

    @Override
    public ExpressionValue makeCopy() {
        return new DoubleValue(underlying);
    }

    @Override
    public ExpressionValue negate() {
        underlying = -underlying;
        return this;
    }

    @Override
    public ExpressionValue add(final ExpressionValue value) {
        return value.add(this);
    }

    @Override
    public ExpressionValue add(final LongValue value) {
        this.underlying += (double) value.getValue();
        return this;
    }

    @Override
    public ExpressionValue add(final DoubleValue value) {
        this.underlying += value.underlying;
        return this;
    }

    @Override
    public ExpressionValue add(final LongArrayValue values) {
        // Defer to LongArrayValue implementation.
        return values.add(this);
    }

    @Override
    public ExpressionValue add(final DoubleArrayValue values) {
        // Defer to DoubleArrayValue implementation.
        return values.add(this);
    }

    @Override
    public ExpressionValue subtract(final ExpressionValue value) {
        return value.negate().add(this);
    }

    @Override
    public ExpressionValue subtract(final LongValue value) {
        this.underlying -= (double) value.getValue();
        return this;
    }

    @Override
    public ExpressionValue subtract(final DoubleValue value) {
        this.underlying -= value.underlying;
        return this;
    }

    @Override
    public ExpressionValue subtract(final LongArrayValue values) {
        final long[] longs = values.underlying;
        final double[] doubles = new double[longs.length];
        for (int i = 0; i < longs.length; ++i) {
            doubles[i] = underlying - (double) longs[i];
        }
        return new DoubleArrayValue(doubles);
    }

    @Override
    public ExpressionValue subtract(final DoubleArrayValue values) {
        for (int i = 0; i < values.underlying.length; ++i) {
            values.underlying[i] = this.underlying - values.underlying[i];
        }
        return values;
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
            final DoubleValue that = (DoubleValue) other;
            return DoubleMath.fuzzyEquals(this.underlying, that.underlying, EPSILON);
        }

        return false;
    }

    @Override
    public String toString() {
        return "DoubleValue{" + underlying + "}";
    }
}
