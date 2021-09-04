package net.opentsdb.query.processor.expressions2.eval;

public interface Value {
    Value makeCopy();

    Value add(Value value);
    Value add(LongConstantValue value);
    Value add(LongArrayValue values);
    //Value add(DoubleConstantValue value);
    //Value add(DoubleArrayValue values);
}
