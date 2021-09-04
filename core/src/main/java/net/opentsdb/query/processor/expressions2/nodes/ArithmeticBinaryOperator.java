package net.opentsdb.query.processor.expressions2.nodes;

import net.opentsdb.query.processor.expressions2.types.FunctionType;
import net.opentsdb.query.processor.expressions2.types.TupleType;
import net.opentsdb.query.processor.expressions2.types.TypeLiteral;

/**
 * ArithmeticBinaryOperator constrains its two operands to have numeric type.
 */
public abstract class ArithmeticBinaryOperator extends BinaryOperator {
    public ArithmeticBinaryOperator(final String symbol, final ExpressionNode leftOperand, final ExpressionNode rightOperand) {
        super(symbol, leftOperand, rightOperand);
    }

    private static FunctionType[] SIGNATURES = {
        new FunctionType(new TupleType(TypeLiteral.NUMERIC, TypeLiteral.NUMERIC), TypeLiteral.NUMERIC),
    };

    @Override
    public FunctionType[] getTypeSignatures() {
        return SIGNATURES;
    }
}
