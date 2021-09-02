package net.opentsdb.query.processor.expressions2.nodes;

import net.opentsdb.query.processor.expressions2.ExpressionException;
import net.opentsdb.query.processor.expressions2.types.ExpressionType;
import net.opentsdb.query.processor.expressions2.types.FunctionType;

public abstract class ExpressionOperator extends ExpressionNode {
    private final String symbol;

    public ExpressionOperator(final String symbol, final ExpressionType domainType) {
        super();

        this.symbol = symbol;

        FunctionType signature = null;
        for (final FunctionType sig : this.getTypeSignatures()) {
            if (sig.getDomain().equals(domainType)) {
                signature = sig;
                break;
            }
        }

        if (null == signature) {
            throw new ExpressionException("could not match given domain type to any valid signature in ExpressionOperator");
        }

        setType(signature.getRange());
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
     * Yield all valid type signatures for this operator.
     */
    public abstract FunctionType[] getTypeSignatures();

    public String getSymbol() {
        return symbol;
    }

    @Override
    public boolean equals(final Object other) {
        System.out.println("ExpressionOperator.equals()");
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
