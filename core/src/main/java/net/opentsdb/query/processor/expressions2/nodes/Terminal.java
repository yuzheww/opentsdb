package net.opentsdb.query.processor.expressions2.nodes;

import net.opentsdb.query.processor.expressions2.types.ExpressionType;

public class Terminal extends ExpressionNode {
    public Terminal(final ExpressionType type) {
        super(type);
    }

    @Override
    public boolean isTerminal() {
        return true;
    }
}
