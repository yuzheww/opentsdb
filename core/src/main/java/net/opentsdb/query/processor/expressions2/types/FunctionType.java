package net.opentsdb.query.processor.expressions2.types;

public class FunctionType implements ExpressionType {
    private final ExpressionType domain;
    private final ExpressionType range;

    public FunctionType(final ExpressionType domain, final ExpressionType range) {
        this.domain = domain;
        this.range = range;
    }

    public ExpressionType getDomain() {
        return domain;
    }

    public ExpressionType getRange() {
        return range;
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

        final FunctionType otherFn = (FunctionType) other;
        return this.domain.equals(otherFn.domain) &&
            this.range.equals(otherFn.range);
    }

    @Override
    public String toString() {
        return domain.toString() + " -> " + range.toString();
    }
}
