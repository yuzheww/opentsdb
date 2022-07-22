package net.opentsdb.query.processor.expressions2.eval;

import net.opentsdb.query.processor.expressions2.ExpressionException;
import net.opentsdb.query.processor.expressions2.nodes.ExpressionNode;
import net.opentsdb.query.processor.expressions2.nodes.Long;

public class LongValue extends NumericValue {
    long underlying;

    public LongValue(final ExpressionFactory factory, final long value) {
        super(factory);
        this.underlying = value;
    }

    public long getValue() {
        return underlying;
    }

    @Override
    public void close() { }

    @Override
    public ExpressionNode asNode() {
        return new Long(underlying);
    }

    @Override
    public ExpressionValue makeCopy() {
        return new LongValue(getFactory(), underlying);
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
        this.underlying += value.underlying;
        return this;
    }

    @Override
    public ExpressionValue add(final DoubleValue value) {
        // Defer to DoubleValue implementation.
        return value.add(this);
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
        this.underlying -= value.underlying;
        return this;
    }

    @Override
    public ExpressionValue subtract(final DoubleValue value) {
        value.underlying = this.underlying - value.underlying;
        return value;
    }

    @Override
    public ExpressionValue subtract(final LongArrayValue values) {
        for (int i = 0; i < values.underlying.length; ++i) {
            values.underlying[i] = this.underlying - values.underlying[i];
        }
        return values;
    }

    @Override
    public ExpressionValue subtract(final DoubleArrayValue values) {
        for (int i = 0; i < values.underlying.length; ++i) {
            values.underlying[i] = (double) this.underlying - values.underlying[i];
        }
        return values;
    }

    @Override
    public ExpressionValue multiply(final ExpressionValue value) {
        return value.multiply(this);
    }

    @Override
    public ExpressionValue multiply(final LongValue value) {
        this.underlying *= value.underlying;
        return this;
    }

    @Override
    public ExpressionValue multiply(final DoubleValue value) {
        // Defer to DoubleValue
        return value.multiply(this);
    }

    @Override
    public ExpressionValue multiply(final LongArrayValue values) {
        // Defer to LongArrayValue
        return values.multiply(this);
    }

    @Override
    public ExpressionValue multiply(final DoubleArrayValue values) {
        // Defer to DoubleArrayValue
        return values.multiply(this);
    }

    @Override
    public ExpressionValue divide(final ExpressionValue value) {
        if (value instanceof LongValue) {
            return this.divide((LongValue) value);
        } else if (value instanceof LongArrayValue) {
            return this.divide((LongArrayValue) value);
        } else if (value instanceof DoubleValue) {
            return this.divide((DoubleValue) value);
        } else if (value instanceof DoubleArrayValue) {
            return this.divide((DoubleArrayValue) value);
        }

        throw new ExpressionException("unsupported expression operation: LongValue.divide(unknown ExpressionValue)");
    }

    // TODO: handle divide-by-zero exception
    @Override
    public ExpressionValue divide(final LongValue value) {
        this.underlying /= value.underlying;
        return this;
    }

    @Override
    public ExpressionValue divide(final DoubleValue value) {
        value.underlying = this.underlying / value.underlying;
        return value;
    }

    @Override
    public ExpressionValue divide(final LongArrayValue values) {
        for (int i = 0; i < values.underlying.length; ++i) {
            values.underlying[i] = this.underlying / values.underlying[i];
        }
        return values;
    }

    @Override
    public ExpressionValue divide(final DoubleArrayValue values) {
        for (int i = 0; i < values.underlying.length; ++i) {
            values.underlying[i] = (double) this.underlying / values.underlying[i];
        }
        return values;
    }

    @Override
    public ExpressionValue mod(final ExpressionValue value) {
        if (value instanceof LongValue) {
            return this.mod((LongValue) value);
        } else if (value instanceof LongArrayValue) {
            return this.mod((LongArrayValue) value);
        } else if (value instanceof DoubleValue) {
            return this.mod((DoubleValue) value);
        } else if (value instanceof DoubleArrayValue) {
            return this.mod((DoubleArrayValue) value);
        }

        throw new ExpressionException("unsupported expression operation: LongValue.mod(unknown ExpressionValue)");
    }

    // TODO: handle divide-by-zero exception
    @Override
    public ExpressionValue mod(final LongValue value) {
        this.underlying %= value.underlying;
        return this;
    }

    @Override
    public ExpressionValue mod(final DoubleValue value) {
        value.underlying = this.underlying % value.underlying;
        return value;
    }

    @Override
    public ExpressionValue mod(final LongArrayValue values) {
        for (int i = 0; i < values.underlying.length; ++i) {
            values.underlying[i] = this.underlying % values.underlying[i];
        }
        return values;
    }

    @Override
    public ExpressionValue mod(final DoubleArrayValue values) {
        for (int i = 0; i < values.underlying.length; ++i) {
            values.underlying[i] = (double) this.underlying % values.underlying[i];
        }
        return values;
    }

    @Override
    public ExpressionValue power(final ExpressionValue value) {
        if (value instanceof LongValue) {
            return this.power((LongValue) value);
        } else if (value instanceof LongArrayValue) {
            return this.power((LongArrayValue) value);
        } else if (value instanceof DoubleValue) {
            return this.power((DoubleValue) value);
        } else if (value instanceof DoubleArrayValue) {
            return this.power((DoubleArrayValue) value);
        }

        throw new ExpressionException("unsupported expression operation: LongValue.power(unknown ExpressionValue)");
    }

    @Override
    public ExpressionValue power(final LongValue value) {
        this.underlying = (long) Math.pow(this.underlying, value.underlying);
        return this;
    }

    @Override
    public ExpressionValue power(final DoubleValue value) {
        value.underlying = Math.pow(this.underlying, value.underlying);
        return value;
    }

    @Override
    public ExpressionValue power(final LongArrayValue values) {
        for (int i = 0; i < values.underlying.length; ++i) {
            values.underlying[i] = (long) Math.pow(this.underlying, values.underlying[i]);
        }
        return values;
    }

    @Override
    public ExpressionValue power(final DoubleArrayValue values) {
        for (int i = 0; i < values.underlying.length; ++i) {
            values.underlying[i] = Math.pow((double) this.underlying, values.underlying[i]);
        }
        return values;
    }

    @Override
    public int compare(DoubleValue value) {
        if (this.underlying == value.underlying) return 0;
        return this.underlying < value.underlying ? -1 : 1;
    }

    @Override
    public int compare(LongValue value) {
        if (this.underlying == value.underlying) return 0;
        return this.underlying < value.underlying ? -1 : 1;
    }

    @Override
    public int compare(BooleanConstantValue value) {
        throw new ExpressionException("illegal call of compare() on LongValue");
    }

    @Override
    public int compare(ExpressionValue value) {
        return -value.compare(this);
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
            final LongValue that = (LongValue) other;
            return this.underlying == that.underlying;
        }

        return false;
    }

    @Override
    public String toString() {
        return "LongValue{" + underlying + "}";
    }
}
