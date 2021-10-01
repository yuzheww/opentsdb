package net.opentsdb.query.processor.expressions2;

import net.opentsdb.query.processor.expressions2.types.ExpressionType;
import net.opentsdb.query.processor.expressions2.types.FunctionType;

/**
 * An operator error is a special case of a function error.
 *
 * Humans, for whatever reason, perceive a meaningful difference between
 * functions (typically written in prefix position with human-readable names)
 * and operators (typically written in infix position with symbolic names);
 * therefore, I consider it important to distinguish them in the exception
 * messages.
 */
public class OperatorTypeError extends FunctionTypeError {
    /**
     * C-tor.
     * @param message Human-readable description.
     * @param symbol Of this operator.
     * @param signatures That are valid for this operator.
     * @param actualType That the actual type of the user operands given.
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
