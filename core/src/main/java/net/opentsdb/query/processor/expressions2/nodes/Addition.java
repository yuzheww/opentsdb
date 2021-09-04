package net.opentsdb.query.processor.expressions2.nodes;

public class Addition extends ArithmeticBinaryOperator {
    public Addition(final ExpressionNode leftOperand, final ExpressionNode rightOperand) {
        super("+", leftOperand, rightOperand);
    }

    @Override
    public void accept(final ExpressionVisitor visitor) {
        visitor.enterAddition(this);
        lhs.accept(visitor);
        rhs.accept(visitor);
        visitor.leaveAddition(this);
    }
}
