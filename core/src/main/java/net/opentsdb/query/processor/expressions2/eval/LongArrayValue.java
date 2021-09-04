package net.opentsdb.query.processor.expressions2.eval;

import java.util.Arrays;

public class LongArrayValue implements ArrayValue<Long> {
    private final long[] underlying;

    public LongArrayValue(final long[] values) {
        underlying = values;
    }

    public long getValueAt(int idx) {
        return underlying[idx];
    }

    public long[] getUnderlying() {
        return underlying;
    }

    @Override
    public Value makeCopy() {
        return new LongArrayValue(Arrays.copyOf(underlying, underlying.length));
    }

    @Override
    public Value add(final Value value) {
        return value.add(this);
    }

    @Override
    public Value add(final LongConstantValue value) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] += value.getValue();
        }
        return this;
    }

    @Override
    public Value add(final LongArrayValue values) {
        for (int i = 0; i < underlying.length; ++i) {
            underlying[i] += values.getValueAt(i);
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
            final LongArrayValue that = (LongArrayValue) other;
            return Arrays.equals(this.underlying, that.underlying);
        }

        return false;
    }
}
