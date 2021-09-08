package net.opentsdb.query.processor.expressions2.eval;

import net.opentsdb.query.processor.expressions2.ExpressionException;

public class BooleanConstantValue extends ExpressionValue {
    private final boolean underlying;

    private BooleanConstantValue(final ExpressionFactory factory, final boolean value) {
        super(factory);
        this.underlying = value;
    }

    public static final BooleanConstantValue TRUE = new BooleanConstantValue(null, true);
    public static final BooleanConstantValue FALSE = new BooleanConstantValue(null, false);

    @Override
    public void close() { }

    @Override
    public ExpressionValue makeCopy() {
        return this; // no such thing -- immutable object
    }

    @Override
    public ExpressionValue negate() {
        throw new ExpressionException("illegal call of negate() on BooleanConstantValue");
    }

    @Override
    public ExpressionValue complement() {
        return TRUE == this ? FALSE : TRUE;
    }

    @Override
    public ExpressionValue add(final ExpressionValue value) {
        throw new ExpressionException("illegal call of add() on BooleanConstantValue");
    }

    @Override
    public ExpressionValue add(final LongValue value) {
        throw new ExpressionException("illegal call of add() on LongValue");
    }

    @Override
    public ExpressionValue add(final DoubleValue value) {
        throw new ExpressionException("illegal call of add() on BooleanConstantValue");
    }

    @Override
    public ExpressionValue add(final LongArrayValue values) {
        throw new ExpressionException("illegal call of add() on LongValue");
    }

    @Override
    public ExpressionValue add(final DoubleArrayValue values) {
        throw new ExpressionException("illegal call of add() on BooleanConstantValue");
    }

    @Override
    public ExpressionValue subtract(final ExpressionValue value) {
        throw new ExpressionException("illegal call of subtract() on BooleanConstantValue");
    }

    @Override
    public ExpressionValue subtract(final LongValue value) {
        throw new ExpressionException("illegal call of subtract() on LongValue");
    }

    @Override
    public ExpressionValue subtract(final DoubleValue value) {
        throw new ExpressionException("illegal call of subtract() on BooleanConstantValue");
    }

    @Override
    public ExpressionValue subtract(final LongArrayValue values) {
        throw new ExpressionException("illegal call of subtract() on LongValue");
    }

    @Override
    public ExpressionValue subtract(final DoubleArrayValue values) {
        throw new ExpressionException("illegal call of subtract() on BooleanConstantValue");
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
            final BooleanConstantValue that = (BooleanConstantValue) other;
            return this.underlying == that.underlying;
        }

        return false;
    }

    @Override
    public String toString() {
        return "BooleanConstantValue{" + underlying + "}";
    }
}
