package net.opentsdb.query.processor.expressions2.nodes;

import net.opentsdb.query.processor.expressions2.types.TypeLiteral;

public class Number extends Terminal {
    private final double value;

    public Number(final double value) {
        super(TypeLiteral.NUMERIC);
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object other) {
        System.out.println("Number.equals()");
        if (this == other) {
            return true;
        }

        if (other == null) {
            return false;
        }

        if (this.getClass() == other.getClass()) {
            final Number that = (Number) other;
            return Math.abs(this.value - that.value) < 1e-6d;
        }

        return false;
    }
}
