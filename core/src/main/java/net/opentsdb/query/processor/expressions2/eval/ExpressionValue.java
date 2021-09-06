package net.opentsdb.query.processor.expressions2.eval;

public interface ExpressionValue {
    ExpressionValue makeCopy();

    ExpressionValue negate();
    ExpressionValue complement();

    ExpressionValue add(ExpressionValue value);
    ExpressionValue add(LongValue value);
    ExpressionValue add(DoubleValue value);
    ExpressionValue add(LongArrayValue values);
    ExpressionValue add(DoubleArrayValue values);

    ExpressionValue subtract(ExpressionValue value);
    ExpressionValue subtract(LongValue value);
    ExpressionValue subtract(DoubleValue value);
    ExpressionValue subtract(LongArrayValue values);
    ExpressionValue subtract(DoubleArrayValue values);
}
