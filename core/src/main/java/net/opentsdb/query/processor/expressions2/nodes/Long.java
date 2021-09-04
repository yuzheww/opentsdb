package net.opentsdb.query.processor.expressions2.nodes;

import net.opentsdb.query.processor.expressions2.types.TypeLiteral;

public class Long extends Terminal {
    private final long value;

    public Long(final long value) {
        super(TypeLiteral.NUMERIC);
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (other == null) {
            return false;
        }

        if (this.getClass() == other.getClass()) {
            final Long that = (Long) other;
            return this.value == that.value;
        }

        return false;
    }

    @Override
    public void accept(final ExpressionVisitor visitor) {
        visitor.enterLong(this);
        visitor.leaveLong(this);
    }
}
