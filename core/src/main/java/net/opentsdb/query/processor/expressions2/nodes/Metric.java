package net.opentsdb.query.processor.expressions2.nodes;

import net.opentsdb.query.processor.expressions2.types.TypeLiteral;

public class Metric extends Terminal {
    private final String name;

    public Metric(final String name) {
        super(TypeLiteral.NUMERIC); // metrics are presently numeric
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getCanonicalRepresentation() {
        return name;
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

        final Metric that = (Metric) other;
        return this.name.equals(that.name);
    }

    @Override
    public void accept(final ExpressionVisitor visitor) {
        visitor.enterMetric(this);
        visitor.leaveMetric(this);
    }
}
