package net.opentsdb.query.processor.expressions2.eval;

public class LongConstantValue extends NumericValue<Long> {
    long underlying;

    public LongConstantValue(final long value) {
        this.underlying = value;
    }

    public long getValue() {
        return underlying;
    }

    @Override
    public Value makeCopy() {
        return new LongConstantValue(underlying);
    }

    @Override
    public Value negate() {
        underlying = -underlying;
        return this;
    }

    @Override
    public Value add(final Value value) {
        return value.add(this);
    }

    @Override
    public Value add(final LongConstantValue value) {
        this.underlying += value.underlying;
        return this;
    }

    @Override
    public Value add(final DoubleConstantValue value) {
        return value.add(this);
    }

    @Override
    public Value add(final LongArrayValue values) {
        return values.add(this);
    }

    @Override
    public Value add(final DoubleArrayValue values) {
        return values.add(this);
    }

    @Override
    public Value subtract(final Value value) {
        return value.negate().add(this);
    }

    @Override
    public Value subtract(final LongConstantValue value) {
        this.underlying -= value.underlying;
        return this;
    }

    @Override
    public Value subtract(final DoubleConstantValue value) {
        return value.negate().add(this);
    }

    @Override
    public Value subtract(final LongArrayValue values) {
        return values.negate().add(this);
    }

    @Override
    public Value subtract(final DoubleArrayValue values) {
        return values.negate().add(this);
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
            final LongConstantValue that = (LongConstantValue) other;
            return this.underlying == that.underlying;
        }

        return false;
    }

    @Override
    public String toString() {
        return "LongConstantValue{" + underlying + "}";
    }
}
