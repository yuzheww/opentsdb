package net.opentsdb.query.processor.expressions2.eval;

public interface Value {
    Value makeCopy();

    Value add(Value value);
    Value add(LongConstantValue value);
    Value add(LongArrayValue values);
    //Value add(DoubleConstantValue value);
    //Value add(DoubleArrayValue values);

    Value subtract(Value value);
    Value subtract(LongConstantValue value);
    Value subtract(LongArrayValue values);
    //Value subtract(DoubleConstantValue value);
    //Value subtract(DoubleArrayValue values);

    Value negate();
}