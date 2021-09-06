package net.opentsdb.query.processor.expressions2.eval;

import net.opentsdb.query.processor.expressions2.ExpressionException;

public abstract class NumericValue<T> implements ExpressionValue {
    public static final double EPSILON = 1e-14;

    @Override
    public ExpressionValue complement() {
        throw new ExpressionException("illegal call of complement() on NumericValue");
    }
}
