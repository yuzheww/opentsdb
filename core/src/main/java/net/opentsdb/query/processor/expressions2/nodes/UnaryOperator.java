package net.opentsdb.query.processor.expressions2.nodes;

import net.opentsdb.query.processor.expressions2.ExpressionException;

public abstract class UnaryOperator extends ExpressionOperator {
    protected ExpressionNode operand;

    public UnaryOperator(final String symbol, final ExpressionNode operand) {
        super(symbol, operand.getType());
        operand.setParent(this);
        this.operand = operand;
    }

    public ExpressionNode getOperand() {
        return operand;
    }

    public void setOperand(final ExpressionNode operand) {
        setType(operand.getType());
        this.operand = operand;
    }

    @Override
    public void replaceChild(final ExpressionNode replaceThis,
            final ExpressionNode withThis) {
        if (operand == replaceThis) {
            setOperand(withThis);
            return;
        }

        throw new ExpressionException("asked to replace child that does not exist in UnaryOperator");
    }

    @Override
    public int getArity() {
        return 1;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (other == null) {
            return false;
        }

        if (other instanceof UnaryOperator) {
            final UnaryOperator that = (UnaryOperator) other;
            return this.operand.equals(that.operand);
        }

        return false;
    }
}
