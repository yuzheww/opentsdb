package net.opentsdb.query.processor.expressions2.eval;

import com.google.common.math.DoubleMath;
import net.opentsdb.pools.PooledObject;
import net.opentsdb.query.processor.expressions2.nodes.Double;
import net.opentsdb.query.processor.expressions2.nodes.ExpressionNode;

public class DoubleValue extends NumericValue {
    double underlying;

    public DoubleValue(final ExpressionFactory factory, final double value) {
        super(factory);
        this.underlying = value;
    }

    public double getValue() {
        return underlying;
    }

    public boolean isExactLong() {
        return DoubleMath.isMathematicalInteger(underlying);
    }

    @Override
    public void close() { }

    @Override
    public ExpressionNode asNode() {
        return new Double(underlying);
    }

    @Override
    public ExpressionValue makeCopy() {
        return new DoubleValue(getFactory(), underlying);
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
        final PooledObject newUnderlying = getFactory().makeDoubleArray(values.underlying.length);
        final DoubleArrayValue newValue = new DoubleArrayValue(getFactory(), newUnderlying);
        for (int i = 0; i < values.underlying.length; ++i) {
            newValue.underlying[i] = this.underlying - (double) values.underlying[i];
        }
        values.close();
        return newValue;
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
