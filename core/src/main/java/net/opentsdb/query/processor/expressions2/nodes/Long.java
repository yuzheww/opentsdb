package net.opentsdb.query.processor.expressions2.nodes;

import net.opentsdb.query.processor.expressions2.types.TypeLiteral;

public class Long extends Terminal {
    private final long value;
    private final String strValue;

    public Long(final long value) {
        super(TypeLiteral.NUMERIC);
        this.value = value;
        strValue = String.valueOf(value);
    }

    public long getValue() {
        return value;
    }

    @Override
    public String getCanonicalRepresentation() {
        return strValue;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (this.getClass() != other.getClass()) {
            return false;
        }

        final Long that = (Long) other;
        return this.value == that.value;
    }

    @Override
    public void accept(final ExpressionVisitor visitor) {
        visitor.enterLong(this);
        visitor.leaveLong(this);
    }
}
