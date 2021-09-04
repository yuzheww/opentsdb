package net.opentsdb.query.processor.expressions2.nodes;

public class Subtraction extends ArithmeticBinaryOperator {
    public Subtraction(final ExpressionNode leftOperand, final ExpressionNode rightOperand) {
        super("-", leftOperand, rightOperand);
    }

    @Override
    public void accept(final ExpressionVisitor visitor) {
        visitor.enterSubtraction(this);
        lhs.accept(visitor);
        rhs.accept(visitor);
        visitor.leaveSubtraction(this);
    }
}
