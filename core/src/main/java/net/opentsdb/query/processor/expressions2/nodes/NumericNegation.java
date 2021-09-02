package net.opentsdb.query.processor.expressions2.nodes;

import net.opentsdb.query.processor.expressions2.types.FunctionType;
import net.opentsdb.query.processor.expressions2.types.TypeLiteral;

public class NumericNegation extends UnaryOperator {
    public NumericNegation(final ExpressionNode operand) {
        super("-", operand);
    }

    private static FunctionType[] SIGNATURES = {
        new FunctionType(TypeLiteral.NUMERIC, TypeLiteral.NUMERIC),
    };

    @Override
    public FunctionType[] getTypeSignatures() {
        return SIGNATURES;
    }
}
