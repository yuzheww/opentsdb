package net.opentsdb.query.processor.expressions2.eval;

import net.opentsdb.query.processor.expressions2.ExpressionException;
import net.opentsdb.query.processor.expressions2.nodes.Bool;
import net.opentsdb.query.processor.expressions2.nodes.ExpressionNode;

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
    public ExpressionNode asNode() {
        return TRUE == this ? Bool.TRUE : Bool.FALSE;
    }

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
    public ExpressionValue multiply(ExpressionValue value) {
        throw new ExpressionException("illegal call of multiply() on BooleanConstantValue");
    }

    @Override
    public ExpressionValue multiply(LongValue value) {
        throw new ExpressionException("illegal call of multiply() on BooleanConstantValue");
    }

    @Override
    public ExpressionValue multiply(DoubleValue value) {
        throw new ExpressionException("illegal call of multiply() on BooleanConstantValue");
    }

    @Override
    public ExpressionValue multiply(LongArrayValue values) {
        throw new ExpressionException("illegal call of multiply() on BooleanConstantValue");
    }

    @Override
    public ExpressionValue multiply(DoubleArrayValue values) {
        throw new ExpressionException("illegal call of multiply() on BooleanConstantValue");
    }

    @Override
    public ExpressionValue divide(ExpressionValue value) {
        throw new ExpressionException("illegal call of divide() on BooleanConstantValue");
    }

    @Override
    public ExpressionValue divide(LongValue value) {
        throw new ExpressionException("illegal call of divide() on BooleanConstantValue");
    }

    @Override
    public ExpressionValue divide(DoubleValue value) {
        throw new ExpressionException("illegal call of divide() on BooleanConstantValue");
    }

    @Override
    public ExpressionValue divide(LongArrayValue values) {
        throw new ExpressionException("illegal call of divide() on BooleanConstantValue");
    }

    @Override
    public ExpressionValue divide(DoubleArrayValue values) {
        throw new ExpressionException("illegal call of divide() on BooleanConstantValue");
    }

    @Override
    public ExpressionValue mod(ExpressionValue value) {
        throw new ExpressionException("illegal call of modulo() on BooleanConstantValue");
    }

    @Override
    public ExpressionValue mod(LongValue value) {
        throw new ExpressionException("illegal call of modulo() on BooleanConstantValue");
    }

    @Override
    public ExpressionValue mod(DoubleValue value) {
        throw new ExpressionException("illegal call of modulo() on BooleanConstantValue");
    }

    @Override
    public ExpressionValue mod(LongArrayValue values) {
        throw new ExpressionException("illegal call of modulo() on BooleanConstantValue");
    }

    @Override
    public ExpressionValue mod(DoubleArrayValue values) {
        throw new ExpressionException("illegal call of modulo() on BooleanConstantValue");
    }

    @Override
    public ExpressionValue power(ExpressionValue value) {
        throw new ExpressionException("illegal call of power() on BooleanConstantValue");
    }

    @Override
    public ExpressionValue power(LongValue value) {
        throw new ExpressionException("illegal call of power() on BooleanConstantValue");
    }

    @Override
    public ExpressionValue power(DoubleValue value) {
        throw new ExpressionException("illegal call of power() on BooleanConstantValue");
    }

    @Override
    public ExpressionValue power(LongArrayValue values) {
        throw new ExpressionException("illegal call of power() on BooleanConstantValue");
    }

    @Override
    public ExpressionValue power(DoubleArrayValue values) {
        throw new ExpressionException("illegal call of power() on BooleanConstantValue");
    }

    @Override
    public int compare(DoubleValue value) {
        throw new ExpressionException("illegal call of compare() on BooleanConstantValue");
    }

    @Override
    public int compare(LongValue value) {
        throw new ExpressionException("illegal call of compare() on BooleanConstantValue");
    }

    @Override
    public int compare(BooleanConstantValue value) {
        return this.equals(value) ? 0 : -1;
    }

    @Override
    public int compare(ExpressionValue value) {
        throw new ExpressionException("illegal call of compare() on BooleanConstantValue");
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
