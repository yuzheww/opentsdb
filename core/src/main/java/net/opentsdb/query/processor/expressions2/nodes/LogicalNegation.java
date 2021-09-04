package net.opentsdb.query.processor.expressions2.nodes;

import net.opentsdb.query.processor.expressions2.types.FunctionType;
import net.opentsdb.query.processor.expressions2.types.TypeLiteral;

public class LogicalNegation extends UnaryOperator {
    public LogicalNegation(final ExpressionNode operand) {
        super("!", operand);
    }

    private static FunctionType[] SIGNATURES = {
        new FunctionType(TypeLiteral.BOOLEAN, TypeLiteral.BOOLEAN),
    };

    @Override
    public FunctionType[] getTypeSignatures() {
        return SIGNATURES;
    }

    @Override
    public void accept(final ExpressionVisitor visitor) {
        visitor.enterLogicalNegation(this);
        operand.accept(visitor);
        visitor.leaveLogicalNegation(this);
    }
}
