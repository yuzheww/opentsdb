package net.opentsdb.query.processor.expressions2.eval;

public interface Value {
    Value makeCopy();

    Value add(Value value);
    Value add(LongConstantValue value);
    Value add(DoubleConstantValue value);
    Value add(LongArrayValue values);
    Value add(DoubleArrayValue values);

    Value subtract(Value value);
    Value subtract(LongConstantValue value);
    Value subtract(DoubleConstantValue value);
    Value subtract(LongArrayValue values);
    Value subtract(DoubleArrayValue values);

    Value negate();
}
