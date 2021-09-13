package net.opentsdb.query.processor.expressions2.nodes;

import net.opentsdb.query.processor.expressions2.types.TypeLiteral;

public class Bool extends Terminal {
    private final boolean value;

    private Bool(final boolean value) {
        super(TypeLiteral.BOOLEAN);
        this.value = value;
    }

    public static final Bool TRUE = new Bool(true);
    public static final Bool FALSE = new Bool(false);

    public boolean getValue() {
        return value;
    }

    @Override
    public int getUses() {
        // We act as if each Boolean value appears once in each expression, no
        // matter how often it is actually used. This will eliminate all copies,
        // which is safe because Boolean values should be immutable.
        return 1;
    }

    @Override
    public void recordUse() { /* noop */ }

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

    @Override
    public void accept(final ExpressionVisitor visitor) {
        visitor.enterBool(this);
        visitor.leaveBool(this);
    }
}
