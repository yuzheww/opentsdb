package net.opentsdb.query.processor.expressions2;

import net.opentsdb.query.processor.expressions2.types.ExpressionType;
import net.opentsdb.query.processor.expressions2.types.FunctionType;

public class OperatorTypeError extends FunctionTypeError {
    /**
     * C-tor.
     * @param message
     * @param symbol
     * @param signatures
     * @param actualType
     */
    public OperatorTypeError(final String message, final String symbol,
            final FunctionType[] signatures, final ExpressionType actualType) {
        super(message, symbol, signatures, actualType);
    }

    @Override
    public String getFunctionKind() {
        return "operator";
    }

    @Override
    public String getArgumentTerm() {
        return "operand";
    }
}
