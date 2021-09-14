package net.opentsdb.query.processor.expressions2.nodes;

import net.opentsdb.query.processor.expressions2.OperatorTypeError;
import net.opentsdb.query.processor.expressions2.types.ExpressionType;
import net.opentsdb.query.processor.expressions2.types.FunctionType;

public abstract class ExpressionOperator extends NonTerminal {
    private final String symbol;

    public ExpressionOperator(final String symbol, final ExpressionType domainType) {
        super();

        this.symbol = symbol;

        setType(findMatchingSignature(domainType).getRange());
    }

    /**
     * Get this operator's symbol.
     * @return This operator's symbol.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Return how many operands this operator requires.
     * @return How many operands this operator requires.
     */
    public abstract int getArity();

    /**
     * Yield all valid type signatures for this operator. In each list of types,
     * the domain type should be unique because findMatchingSignature() will
     * select only the first matching type.
     * @return All valid type signatures for this operator.
     */
    public abstract FunctionType[] getTypeSignatures();

    /**
     * Find the first type signature whose domain is satisfied by domainType.
     * @param domainType
     * @return The first signature satisfied by domainType.
     */
    public FunctionType findMatchingSignature(final ExpressionType domainType) {
        for (final FunctionType sig : getTypeSignatures()) {
            if (sig.getDomain().equals(domainType)) {
                return sig;
            }
        }

        throw new OperatorTypeError("type mismatch", getSymbol(), getTypeSignatures(), domainType);
    }

    @Override
    public boolean isTerminal() {
        return false;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (other == null) {
            return false;
        }

        if (other instanceof ExpressionOperator) {
            final ExpressionOperator that = (ExpressionOperator) other;
            return this.symbol.equals(that.symbol);
        }

        return false;
    }
}
