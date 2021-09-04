package net.opentsdb.query.processor.expressions2.nodes;

public abstract class UnaryOperator extends ExpressionOperator {
    protected ExpressionNode operand;

    public UnaryOperator(final String symbol, final ExpressionNode operand) {
        super(symbol, operand.getType());
        this.operand = operand;
    }

    @Override
    public int getArity() {
        return 1;
    }

    public ExpressionNode getOperand() {
        return operand;
    }

    public void setOperand(final ExpressionNode operand) {
        setType(operand.getType());
        this.operand = operand;
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
