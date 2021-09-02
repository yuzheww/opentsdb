package net.opentsdb.query.processor.expressions2.nodes;

import net.opentsdb.query.processor.expressions2.types.TypeLiteral;

public class Bool extends Terminal {
    private final boolean value;

    private Bool(final boolean value) {
        super(TypeLiteral.BOOLEAN);
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    public static final Bool TRUE = new Bool(true);
    public static final Bool FALSE = new Bool(false);

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (other == null) {
            return false;
        }

        if (other instanceof Bool) {
            final Bool that = (Bool) other;
            return this.value == that.value;
        }

        return false;
    }
}
