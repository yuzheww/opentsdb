package net.opentsdb.query.processor.expressions2.nodes;

import net.opentsdb.query.processor.expressions2.types.TypeLiteral;

public class Double extends Terminal {
    private final double value;
    private final String strValue;

    public Double(final double value) {
        super(TypeLiteral.NUMERIC);
        this.value = value;
        strValue = String.valueOf(value);
    }

    public double getValue() {
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

        final Double that = (Double) other;
        return Math.abs(this.value - that.value) < 1e-6d;
    }

    @Override
    public void accept(final ExpressionVisitor visitor) {
        visitor.enterDouble(this);
        visitor.leaveDouble(this);
    }
}
