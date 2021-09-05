package net.opentsdb.query.processor.expressions2.eval;

import net.opentsdb.query.processor.expressions2.ExpressionException;

public class BooleanConstantValue implements Value {
    private final boolean underlying;

    private BooleanConstantValue(final boolean value) {
        this.underlying = value;
    }

    public static final BooleanConstantValue TRUE = new BooleanConstantValue(true);
    public static final BooleanConstantValue FALSE = new BooleanConstantValue(false);

    @Override
    public Value makeCopy() {
        return this; // no such thing -- immutable object
    }

    @Override
    public Value negate() {
        throw new ExpressionException("illegal call of negate() on BooleanConstantValue");
    }

    @Override
    public Value complement() {
        return TRUE == this ? FALSE : TRUE;
    }

    @Override
    public Value add(final Value value) {
        throw new ExpressionException("illegal call of add() on BooleanConstantValue");
    }

    @Override
    public Value add(final LongConstantValue value) {
        throw new ExpressionException("illegal call of add() on LongConstantValue");
    }

    @Override
    public Value add(final DoubleConstantValue value) {
        throw new ExpressionException("illegal call of add() on BooleanConstantValue");
    }

    @Override
    public Value add(final LongArrayValue values) {
        throw new ExpressionException("illegal call of add() on LongConstantValue");
    }

    @Override
    public Value add(final DoubleArrayValue values) {
        throw new ExpressionException("illegal call of add() on BooleanConstantValue");
    }

    @Override
    public Value subtract(final Value value) {
        throw new ExpressionException("illegal call of subtract() on BooleanConstantValue");
    }

    @Override
    public Value subtract(final LongConstantValue value) {
        throw new ExpressionException("illegal call of subtract() on LongConstantValue");
    }

    @Override
    public Value subtract(final DoubleConstantValue value) {
        throw new ExpressionException("illegal call of subtract() on BooleanConstantValue");
    }

    @Override
    public Value subtract(final LongArrayValue values) {
        throw new ExpressionException("illegal call of subtract() on LongConstantValue");
    }

    @Override
    public Value subtract(final DoubleArrayValue values) {
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
