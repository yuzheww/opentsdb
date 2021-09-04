package net.opentsdb.query.processor.expressions2.nodes;

import net.opentsdb.query.processor.expressions2.types.TypeLiteral;

public class Double extends Terminal {
    private final double value;

    public Double(final double value) {
        super(TypeLiteral.NUMERIC);
        this.value = value;
    }

    public double getValue() {
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
            final Double that = (Double) other;
            return Math.abs(this.value - that.value) < 1e-6d;
        }

        return false;
    }

    @Override
    public void accept(final ExpressionVisitor visitor) {
        visitor.enterDouble(this);
        visitor.leaveDouble(this);
    }
}
