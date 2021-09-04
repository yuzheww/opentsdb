package net.opentsdb.query.processor.expressions2.nodes;

import net.opentsdb.query.processor.expressions2.types.FunctionType;
import net.opentsdb.query.processor.expressions2.types.TupleType;
import net.opentsdb.query.processor.expressions2.types.TypeLiteral;

/**
 * EquivalenceBinaryOperator constrains its two operands to have the same type, and
 * it sets its output type as Boolean.
 */
public abstract class EquivalenceBinaryOperator extends BinaryOperator {
    public EquivalenceBinaryOperator(final String symbol, final ExpressionNode leftOperand, final ExpressionNode rightOperand) {
        super(symbol, leftOperand, rightOperand);
    }

    private static FunctionType[] SIGNATURES = {
        new FunctionType(new TupleType(TypeLiteral.NUMERIC, TypeLiteral.NUMERIC), TypeLiteral.BOOLEAN),
        new FunctionType(new TupleType(TypeLiteral.BOOLEAN, TypeLiteral.BOOLEAN), TypeLiteral.BOOLEAN),
    };

    @Override
    public FunctionType[] getTypeSignatures() {
        return SIGNATURES;
    }
}
