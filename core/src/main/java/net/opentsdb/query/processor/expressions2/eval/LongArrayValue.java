package net.opentsdb.query.processor.expressions2.eval;

import com.google.common.primitives.Longs;
import java.util.Arrays;
import net.opentsdb.pools.PooledObject;

public class LongArrayValue extends NumericValue<Long> {
    private final PooledObject pooledObject;
    long[] underlying;

    /**
     * Construct directly from a raw array.
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
