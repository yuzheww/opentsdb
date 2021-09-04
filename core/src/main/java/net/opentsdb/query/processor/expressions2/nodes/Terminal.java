package net.opentsdb.query.processor.expressions2.nodes;

import net.opentsdb.query.processor.expressions2.types.ExpressionType;

public abstract class Terminal extends ExpressionNode {
    public Terminal(final ExpressionType type) {
        super(type);
    }

    @Override
    public boolean isTerminal() {
        return true;
    }
}
