package net.opentsdb.query.processor.expressions2.eval;

import com.google.common.primitives.Longs;

import java.util.Arrays;

import net.opentsdb.pools.PooledObject;
import net.opentsdb.query.processor.expressions2.ExpressionException;
import net.opentsdb.query.processor.expressions2.nodes.ExpressionNode;

public class LongArrayValue extends NumericValue {
    private final PooledObject pooledObject;
    long[] underlying;

    /**
     * Construct directly from a raw array.
     *
     * @param factory
     * @param values
     */
    public LongArrayValue(final ExpressionFactory factory, final long[] values) {
        super(factory);
        pooledObject = null;
        underlying = values;
    }

    /**
     * Construct from a pooled array.
     *
     * @param factory
     * @param arrObj
     */
    public LongArrayValue(final ExpressionFactory factory, final PooledObject arrObj) {
        super(factory);
        pooledObject = arrObj;
        underlying = (long[]) arrObj.object();
    }

    /**
     * @return True only if this value still has backing storage.
     */
    public boolean isLive() {
        return null != underlying;
    }

    public int getLength() {
        return underlying.length;
    }

    public long getValueAt(int idx) {
        return underlying[idx];
    }

    @Override
    public void close() {
        underlying = null;
        if (null != pooledObject) {
            pooledObject.release();
        }
    }

    @Override
    public ExpressionNode asNode() {
        throw new UnsupportedOperationException("cannot transform LongArrayValue into ExpressionNode subclass");
    }

    @Override
    public ExpressionValue makeCopy() {
        final PooledObject newUnderlying = getFactory().copyLongArray(underlying);
        return new LongArrayValue(getFactory(), newUnderlying);
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
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] += value.getValue();
        }
        return this;
    }

    @Override
    public ExpressionValue add(final DoubleValue value) {
        // Can we possibly avoid converting this array?
        if (value.isExactLong()) {
            // Yes, the addend is exactly representable as a long.
            final long addend = (long) value.getValue();
            for (int i = 0; i < underlying.length; ++i) {
                underlying[i] += addend;
            }
            return this;
        } else {
            // No, we need to create a double array.
            final double addend = value.getValue();
            final PooledObject newUnderlying = getFactory().makeDoubleArray(
                    underlying.length);
            final DoubleArrayValue newValue = new DoubleArrayValue(getFactory(),
                    newUnderlying);
            for (int i = 0; i < underlying.length; ++i) {
                newValue.underlying[i] = (double) underlying[i] + addend;
            }
            this.close();
            return newValue;
        }
    }

    @Override
    public ExpressionValue add(final LongArrayValue values) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] += values.getValueAt(i);
        }
        values.close();
        return this;
    }

    @Override
    public ExpressionValue add(final DoubleArrayValue values) {
        // We do not want to convert this array. Addition is commutative, so we
        // will defer to the double array implementation.
        return values.add(this);
    }

    @Override
    public ExpressionValue subtract(final ExpressionValue value) {
        return value.negate().add(this);
    }

    @Override
    public ExpressionValue subtract(final LongValue value) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] -= value.getValue();
        }
        return this;
    }

    @Override
    public ExpressionValue subtract(final DoubleValue value) {
        // Can we possibly avoid converting this array?
        if (value.isExactLong()) {
            // Yes, the subtrahend is exactly representable as a long.
            final long subtrahend = (long) value.getValue();
            for (int i = 0; i < underlying.length; ++i) {
                underlying[i] -= subtrahend;
            }
            return this;
        } else {
            // No, we need to create a double array.
            final double subtrahend = value.getValue();
            final PooledObject newUnderlying = getFactory().makeDoubleArray(
                    underlying.length);
            final DoubleArrayValue newValue = new DoubleArrayValue(getFactory(),
                    newUnderlying);
            for (int i = 0; i < underlying.length; ++i) {
                newValue.underlying[i] = (double) underlying[i] - subtrahend;
            }
            this.close();
            return newValue;
        }
    }

    @Override
    public ExpressionValue subtract(final LongArrayValue values) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] -= values.getValueAt(i);
        }
        values.close();
        return this;
    }

    @Override
    public ExpressionValue subtract(final DoubleArrayValue values) {
        for (int i = 0; i < values.underlying.length; ++i) {
            values.underlying[i] = (double) this.underlying[i] - values.underlying[i];
        }
        this.close();
        return values;
    }

    @Override
    public ExpressionValue multiply(final ExpressionValue value) {
        return value.multiply(this);
    }

    @Override
    public ExpressionValue multiply(final LongValue value) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] *= value.getValue();
        }
        return this;
    }

    @Override
    public ExpressionValue multiply(final DoubleValue value) {
        // Can we possibly avoid converting this array?
        if (value.isExactLong()) {
            // Yes, the subtrahend is exactly representable as a long.
            final long subtrahend = (long) value.getValue();
            for (int i = 0; i < underlying.length; ++i) {
                underlying[i] *= subtrahend;
            }
            return this;
        } else {
            // No, we need to create a double array.
            final double subtrahend = value.getValue();
            final PooledObject newUnderlying = getFactory().makeDoubleArray(
                    underlying.length);
            final DoubleArrayValue newValue = new DoubleArrayValue(getFactory(),
                    newUnderlying);
            for (int i = 0; i < underlying.length; ++i) {
                newValue.underlying[i] = (double) underlying[i] * subtrahend;
            }
            this.close();
            return newValue;
        }
    }

    @Override
    public ExpressionValue multiply(final LongArrayValue values) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] *= values.getValueAt(i);
        }
        values.close();
        return this;
    }

    @Override
    public ExpressionValue multiply(final DoubleArrayValue values) {
        // Defer to DoubleArrayValue
        return values.multiply(this);
    }

    // TODO: handle divide-by-zero exception
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

        throw new ExpressionException("unsupported expression operation: LongArrayValue.divide(unknown ExpressionValue)");
    }

    @Override
    public ExpressionValue divide(final LongValue value) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] /= value.getValue();
        }
        return this;
    }

    @Override
    public ExpressionValue divide(final DoubleValue value) {
        // Can we possibly avoid converting this array?
        if (value.isExactLong()) {
            // Yes, the subtrahend is exactly representable as a long.
            final long subtrahend = (long) value.getValue();
            for (int i = 0; i < underlying.length; ++i) {
                underlying[i] /= subtrahend;
            }
            return this;
        } else {
            // No, we need to create a double array.
            final double subtrahend = value.getValue();
            final PooledObject newUnderlying = getFactory().makeDoubleArray(
                    underlying.length);
            final DoubleArrayValue newValue = new DoubleArrayValue(getFactory(),
                    newUnderlying);
            for (int i = 0; i < underlying.length; ++i) {
                newValue.underlying[i] = (double) underlying[i] / subtrahend;
            }
            this.close();
            return newValue;
        }
    }

    @Override
    public ExpressionValue divide(final LongArrayValue values) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] /= values.getValueAt(i);
        }
        values.close();
        return this;
    }

    @Override
    public ExpressionValue divide(final DoubleArrayValue values) {
        for (int i = 0; i < values.underlying.length; ++i) {
            values.underlying[i] = (double) this.underlying[i] / values.underlying[i];
        }
        this.close();
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

        throw new ExpressionException("unsupported expression operation: LongArrayValue.mod(unknown ExpressionValue)");
    }

    // TODO: handle divide-by-zero exception
    @Override
    public ExpressionValue mod(final LongValue value) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] %= value.getValue();
        }
        return this;
    }

    @Override
    public ExpressionValue mod(final DoubleValue value) {
        // Can we possibly avoid converting this array?
        if (value.isExactLong()) {
            // Yes, the subtrahend is exactly representable as a long.
            final long subtrahend = (long) value.getValue();
            for (int i = 0; i < underlying.length; ++i) {
                underlying[i] %= subtrahend;
            }
            return this;
        } else {
            // No, we need to create a double array.
            final double subtrahend = value.getValue();
            final PooledObject newUnderlying = getFactory().makeDoubleArray(
                    underlying.length);
            final DoubleArrayValue newValue = new DoubleArrayValue(getFactory(),
                    newUnderlying);
            for (int i = 0; i < underlying.length; ++i) {
                newValue.underlying[i] = (double) underlying[i] % subtrahend;
            }
            this.close();
            return newValue;
        }
    }

    @Override
    public ExpressionValue mod(final LongArrayValue values) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] %= values.getValueAt(i);
        }
        values.close();
        return this;
    }

    @Override
    public ExpressionValue mod(final DoubleArrayValue values) {
        for (int i = 0; i < values.underlying.length; ++i) {
            values.underlying[i] = (double) this.underlying[i] % values.underlying[i];
        }
        this.close();
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

        throw new ExpressionException("unsupported expression operation: LongArrayValue.power(unknown ExpressionValue)");
    }

    @Override
    public ExpressionValue power(final LongValue value) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] = (long) Math.pow(underlying[i], value.getValue());
        }
        return this;
    }

    @Override
    public ExpressionValue power(final DoubleValue value) {
        // Can we possibly avoid converting this array?
        if (value.isExactLong()) {
            // Yes, the subtrahend is exactly representable as a long.
            final long subtrahend = (long) value.getValue();
            for (int i = 0; i < underlying.length; ++i) {
                underlying[i] = (long) Math.pow(underlying[i], subtrahend);
            }
            return this;
        } else {
            // No, we need to create a double array.
            final double subtrahend = value.getValue();
            final PooledObject newUnderlying = getFactory().makeDoubleArray(
                    underlying.length);
            final DoubleArrayValue newValue = new DoubleArrayValue(getFactory(),
                    newUnderlying);
            for (int i = 0; i < underlying.length; ++i) {
                newValue.underlying[i] = Math.pow((double) underlying[i], subtrahend);
            }
            this.close();
            return newValue;
        }
    }

    @Override
    public ExpressionValue power(final LongArrayValue values) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] = (long) Math.pow(underlying[i], values.getValueAt(i));
        }
        values.close();
        return this;
    }

    @Override
    public ExpressionValue power(final DoubleArrayValue values) {
        for (int i = 0; i < values.underlying.length; ++i) {
            values.underlying[i] = Math.pow((double) this.underlying[i], values.underlying[i]);
        }
        this.close();
        return values;
    }

    @Override
    public int compare(DoubleValue value) {
        throw new ExpressionException("illegal call of compare() on LongArrayValue");
    }

    @Override
    public int compare(LongValue value) {
        throw new ExpressionException("illegal call of compare() on LongArrayValue");
    }

    @Override
    public int compare(BooleanConstantValue value) {
        throw new ExpressionException("illegal call of compare() on LongArrayValue");
    }

    @Override
    public int compare(ExpressionValue value) {
        throw new ExpressionException("illegal call of compare() on LongArrayValue");
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
            final LongArrayValue that = (LongArrayValue) other;
            return Arrays.equals(this.underlying, that.underlying);
        }

        return false;
    }

    @Override
    public String toString() {
        return "LongArrayValue{" + Longs.join(",", underlying) + "}";
    }
}
