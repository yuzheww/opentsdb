package net.opentsdb.query.processor.expressions2.types;

import java.util.Arrays;

public class TupleType implements ExpressionType {
    private final ExpressionType[] children;

    public TupleType(final ExpressionType... types) {
        children = types;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (null == other) {
            return false;
        }
        if (this.getClass() != other.getClass()) {
            return false;
        }

        final TupleType otherTuple = (TupleType) other;
        return Arrays.equals(this.children, otherTuple.children);
    }
}
