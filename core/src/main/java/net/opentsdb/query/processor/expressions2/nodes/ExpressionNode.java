package net.opentsdb.query.processor.expressions2.nodes;

import net.opentsdb.query.processor.expressions2.types.ExpressionType;

public abstract class ExpressionNode {
    private ExpressionType type;

    public ExpressionNode() {
        this.type = null; // better set this...
    }

    public ExpressionNode(final ExpressionType type) {
        this.type = type;
    }

    public ExpressionType getType() {
        return type;
    }

    public void setType(final ExpressionType type) {
        this.type = type;
    }

    public abstract boolean isTerminal();

    public abstract void accept(ExpressionVisitor visitor);
}
