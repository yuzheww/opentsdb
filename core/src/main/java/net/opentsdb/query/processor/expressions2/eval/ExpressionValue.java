package net.opentsdb.query.processor.expressions2.eval;

import net.opentsdb.query.processor.expressions2.nodes.ExpressionNode;

/**
 * ExpressionValue is the root class of the value hierarchy.
 */
public abstract class ExpressionValue implements AutoCloseable {
    private final ExpressionFactory factory;

    public ExpressionValue(final ExpressionFactory factory) {
        this.factory = factory;
    }

    public ExpressionFactory getFactory() {
        return factory;
    }

    public abstract ExpressionNode asNode();

    public abstract ExpressionValue makeCopy();

    public abstract ExpressionValue negate();
    public abstract ExpressionValue complement();

    public abstract ExpressionValue add(ExpressionValue value);
    public abstract ExpressionValue add(LongValue value);
    public abstract ExpressionValue add(DoubleValue value);
    public abstract ExpressionValue add(LongArrayValue values);
    public abstract ExpressionValue add(DoubleArrayValue values);

    public abstract ExpressionValue subtract(ExpressionValue value);
    public abstract ExpressionValue subtract(LongValue value);
    public abstract ExpressionValue subtract(DoubleValue value);
    public abstract ExpressionValue subtract(LongArrayValue values);
    public abstract ExpressionValue subtract(DoubleArrayValue values);

    public abstract ExpressionValue multiply(ExpressionValue value);
    public abstract ExpressionValue multiply(LongValue value);
    public abstract ExpressionValue multiply(DoubleValue value);
    public abstract ExpressionValue multiply(LongArrayValue values);
    public abstract ExpressionValue multiply(DoubleArrayValue values);

    public abstract ExpressionValue divide(ExpressionValue value);
    public abstract ExpressionValue divide(LongValue value);
    public abstract ExpressionValue divide(DoubleValue value);
    public abstract ExpressionValue divide(LongArrayValue values);
    public abstract ExpressionValue divide(DoubleArrayValue values);

    public abstract ExpressionValue mod(ExpressionValue value);
    public abstract ExpressionValue mod(LongValue value);
    public abstract ExpressionValue mod(DoubleValue value);
    public abstract ExpressionValue mod(LongArrayValue values);
    public abstract ExpressionValue mod(DoubleArrayValue values);

    public abstract ExpressionValue power(ExpressionValue value);
    public abstract ExpressionValue power(LongValue value);
    public abstract ExpressionValue power(DoubleValue value);
    public abstract ExpressionValue power(LongArrayValue values);
    public abstract ExpressionValue power(DoubleArrayValue values);
}
