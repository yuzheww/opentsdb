package net.opentsdb.query.processor.expressions2.eval;

import com.google.common.math.DoubleMath;
import com.google.common.primitives.Longs;
import java.util.Arrays;
import net.opentsdb.pools.PooledObject;

public class LongArrayValue extends NumericValue<Long> {
    private final PooledObject pooledObject;
    long[] underlying;

    /**
     * Construct directly from a raw array.
     * @param values
     */
    public LongArrayValue(final long[] values) {
        pooledObject = null;
        underlying = values;
    }

    /**
     * Construct from a pooled array.
     * @param arrObj
     */
    public LongArrayValue(final PooledObject arrObj) {
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
        return new LongArrayValue(Arrays.copyOf(underlying, underlying.length));
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
        final double addend = value.getValue();
        if (DoubleMath.isMathematicalInteger(addend)) {
            // Yes, the addend is exactly representable as a long.
            final long lval = (long) addend;
            for (int i = 0; i < underlying.length; ++i) {
                underlying[i] += lval;
            }
            return this;
        } else {
            // No, we need to create a double array.
            final double[] doubles = new double[underlying.length];
            for (int i = 0; i < underlying.length; ++i) {
                doubles[i] = (double) underlying[i] + addend;
            }
            this.close();
            return new DoubleArrayValue(doubles);
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
        final double subtrahend = value.getValue();
        if (DoubleMath.isMathematicalInteger(subtrahend)) {
            // Yes, the subtrahend is exactly representable as a long.
            final long lval = (long) subtrahend;
            for (int i = 0; i < underlying.length; ++i) {
                underlying[i] -= lval;
            }
            return this;
        } else {
            // No, we need to create a double array.
            final double[] doubles = new double[underlying.length];
            for (int i = 0; i < underlying.length; ++i) {
                doubles[i] = (double) underlying[i] - subtrahend;
            }
            return new DoubleArrayValue(doubles);
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
