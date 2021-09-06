package net.opentsdb.query.processor.expressions2.nodes;

import net.opentsdb.query.processor.expressions2.ExpressionException;
import net.opentsdb.query.processor.expressions2.types.TupleType;

/**
 * BinaryOperator constrains its two operands to have the same type, and it
 * assumes that its output type is the same as its left-hand operand.
 */
public abstract class BinaryOperator extends ExpressionOperator {
    protected ExpressionNode lhs;
    protected ExpressionNode rhs;

    public BinaryOperator(final String symbol, final ExpressionNode leftOperand, final ExpressionNode rightOperand) {
        super(symbol, new TupleType(leftOperand.getType(), rightOperand.getType()));

        lhs = leftOperand;
        rhs = rightOperand;
    }

    @Override
    public int getArity() {
        return 2;
    }

    public ExpressionNode getLeftOperand() {
        return lhs;
    }

    public ExpressionNode getRightOperand() {
        return rhs;
    }

    public void setLeftOperand(final ExpressionNode lhs) {
        if (!this.lhs.getType().equals(lhs.getType())) {
            throw new ExpressionException("bad type for left operand in BinaryOperator");
        }
        this.lhs = lhs;
    }

    public void setRightOperand(final ExpressionNode rhs) {
        if (!this.rhs.getType().equals(rhs.getType())) {
            throw new ExpressionException("bad type for right operand in BinaryOperator");
        }
        this.rhs = rhs;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (other == null) {
            return false;
        }

        if (other instanceof BinaryOperator) {
            final BinaryOperator that = (BinaryOperator) other;
            return this.lhs.equals(that.lhs) &&
                this.rhs.equals(that.rhs) &&
                super.equals(that);
        }

        return false;
    }
}
