package net.opentsdb.query.processor.expressions2.eval;

public abstract class ExpressionValue implements AutoCloseable {
    private final ExpressionFactory factory;

    public ExpressionValue(final ExpressionFactory factory) {
        this.factory = factory;
    }

    public ExpressionFactory getFactory() {
        return factory;
    }

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
}
