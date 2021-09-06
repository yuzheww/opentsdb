package net.opentsdb.query.processor.expressions2.eval;

public class LongValue extends NumericValue<Long> {
    long underlying;

    public LongValue(final long value) {
        this.underlying = value;
    }

    public long getValue() {
        return underlying;
    }

    @Override
    public ExpressionValue makeCopy() {
        return new LongValue(underlying);
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
        return value.add(this);
    }

    @Override
    public ExpressionValue add(final LongArrayValue values) {
        return values.add(this);
    }

    @Override
    public ExpressionValue add(final DoubleArrayValue values) {
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
        return value.negate().add(this);
    }

    @Override
    public ExpressionValue subtract(final LongArrayValue values) {
        return values.negate().add(this);
    }

    @Override
    public ExpressionValue subtract(final DoubleArrayValue values) {
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
