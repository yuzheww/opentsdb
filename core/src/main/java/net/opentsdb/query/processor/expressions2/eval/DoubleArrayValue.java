package net.opentsdb.query.processor.expressions2.eval;

import com.google.common.math.DoubleMath;
import com.google.common.primitives.Doubles;

import java.util.Arrays;

import net.opentsdb.pools.PooledObject;
import net.opentsdb.query.processor.expressions2.ExpressionException;
import net.opentsdb.query.processor.expressions2.nodes.ExpressionNode;

public class DoubleArrayValue extends NumericValue {
    private final PooledObject pooledObject;
    double[] underlying;

    /**
     * Construct directly from a raw array.
     *
     * @param factory
     * @param values
     */
    public DoubleArrayValue(final ExpressionFactory factory, final double[] values) {
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
    public DoubleArrayValue(final ExpressionFactory factory, final PooledObject arrObj) {
        super(factory);
        pooledObject = arrObj;
        underlying = (double[]) arrObj.object();
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

    public double getValueAt(int idx) {
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
        throw new UnsupportedOperationException("cannot transform DoubleArrayValue into ExpressionNode subclass");
    }

    @Override
    public ExpressionValue makeCopy() {
        final PooledObject newUnderlying = getFactory().copyDoubleArray(underlying);
        return new DoubleArrayValue(getFactory(), newUnderlying);
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
        values.close();
        return this;
    }

    @Override
    public ExpressionValue add(final DoubleArrayValue values) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] += values.getValueAt(i);
        }
        values.close();
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
        values.close();
        return this;
    }

    @Override
    public ExpressionValue subtract(final DoubleArrayValue values) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] -= values.getValueAt(i);
        }
        values.close();
        return this;
    }

    @Override
    public ExpressionValue multiply(final ExpressionValue value) {
        return value.multiply(this);
    }

    @Override
    public ExpressionValue multiply(final LongValue value) {
        final double subtrahend = (double) value.getValue();
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] *= subtrahend;
        }
        return this;
    }

    @Override
    public ExpressionValue multiply(final DoubleValue value) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] *= value.getValue();
        }
        return this;
    }

    @Override
    public ExpressionValue multiply(final LongArrayValue values) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] *= (double) values.getValueAt(i);
        }
        values.close();
        return this;
    }

    @Override
    public ExpressionValue multiply(final DoubleArrayValue values) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] *= values.getValueAt(i);
        }
        values.close();
        return this;
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

        throw new ExpressionException("unsupported expression operation: DoubleArrayValue.divide(unknown ExpressionValue)");
    }

    // TODO: handle divide-by-zero exception
    @Override
    public ExpressionValue divide(final LongValue value) {
        final double subtrahend = (double) value.getValue();
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] /= subtrahend;
        }
        return this;
    }

    @Override
    public ExpressionValue divide(final DoubleValue value) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] /= value.getValue();
        }
        return this;
    }

    @Override
    public ExpressionValue divide(final LongArrayValue values) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] /= (double) values.getValueAt(i);
        }
        values.close();
        return this;
    }

    @Override
    public ExpressionValue divide(final DoubleArrayValue values) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] /= values.getValueAt(i);
        }
        values.close();
        return this;
    }

    // TODO: handle divide-by-zero exception
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

        throw new ExpressionException("unsupported expression operation: DoubleArrayValue.mod(unknown ExpressionValue)");
    }

    @Override
    public ExpressionValue mod(final LongValue value) {
        final double subtrahend = (double) value.getValue();
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] %= subtrahend;
        }
        return this;
    }

    @Override
    public ExpressionValue mod(final DoubleValue value) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] %= value.getValue();
        }
        return this;
    }

    @Override
    public ExpressionValue mod(final LongArrayValue values) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] %= (double) values.getValueAt(i);
        }
        values.close();
        return this;
    }

    @Override
    public ExpressionValue mod(final DoubleArrayValue values) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] %= values.getValueAt(i);
        }
        values.close();
        return this;
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

        throw new ExpressionException("unsupported expression operation: DoubleArrayValue.power(unknown ExpressionValue)");
    }

    @Override
    public ExpressionValue power(final LongValue value) {
        final double subtrahend = (double) value.getValue();
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] = Math.pow(underlying[i], subtrahend);
        }
        return this;
    }

    @Override
    public ExpressionValue power(final DoubleValue value) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] = Math.pow(underlying[i], value.getValue());
        }
        return this;
    }

    @Override
    public ExpressionValue power(final LongArrayValue values) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] = Math.pow(underlying[i], (double) values.getValueAt(i));
        }
        values.close();
        return this;
    }

    @Override
    public ExpressionValue power(final DoubleArrayValue values) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] = Math.pow(underlying[i], values.getValueAt(i));
        }
        values.close();
        return this;
    }

    @Override
    public int compare(DoubleValue value) {
        throw new ExpressionException("illegal call of compare() on DoubleArrayValue");
    }

    @Override
    public int compare(LongValue value) {
        throw new ExpressionException("illegal call of compare() on DoubleArrayValue");
    }

    @Override
    public int compare(BooleanConstantValue value) {
        throw new ExpressionException("illegal call of compare() on DoubleArrayValue");
    }

    @Override
    public int compare(ExpressionValue value) {
        throw new ExpressionException("illegal call of compare() on DoubleArrayValue");
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
