package net.opentsdb.query.processor.expressions2.nodes;

import net.opentsdb.query.processor.expressions2.ExpressionException;
import net.opentsdb.query.processor.expressions2.types.ExpressionType;
import net.opentsdb.query.processor.expressions2.types.FunctionType;

public abstract class ExpressionOperator extends ExpressionNode {
    private final String symbol;

    public ExpressionOperator(final String symbol, final ExpressionType domainType) {
        super();

        this.symbol = symbol;

        setType(findMatchingSignature(domainType).getRange());
    }

    @Override
    public boolean isTerminal() {
        return false;
    }

    /**
     * Return how many operands this operator requires.
     */
    public abstract int getArity();

    /**
     * Yield all valid type signatures for this operator. In each list of types,
     * the domain type should be unique because findMatchingSignature() will
     * select only the first matching type.
     */
    public abstract FunctionType[] getTypeSignatures();

    /**
     * Find the first function signature satisfied by the given domainType.
     * @param domainType
     * @return The first signature satisfied by domainType.
     */
    public FunctionType findMatchingSignature(final ExpressionType domainType) {
        for (final FunctionType sig : getTypeSignatures()) {
            if (sig.getDomain().equals(domainType)) {
                return sig;
            }
        }

        throw new ExpressionException("could not match given domain type to any valid signature in ExpressionOperator");
    }

    public String getSymbol() {
        return symbol;
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
