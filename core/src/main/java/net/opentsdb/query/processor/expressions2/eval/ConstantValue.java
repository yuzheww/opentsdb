package net.opentsdb.query.processor.expressions2.eval;

public abstract class ConstantValue<T> implements Value {
    @Override
    public Value makeCopy() {
        return this; // constant values must be immutable
    }
}
