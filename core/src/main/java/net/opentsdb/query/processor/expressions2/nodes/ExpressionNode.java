package net.opentsdb.query.processor.expressions2.nodes;

import net.opentsdb.query.processor.expressions2.types.ExpressionType;

/**
 * ExpressionNode is the root class of the node hierarchy.
 */
public abstract class ExpressionNode {
    private ExpressionType type;
    private NonTerminal parent;

    public ExpressionNode() {
        this.type = null; // better set this...
        this.parent = null;
    }

    public ExpressionNode(final ExpressionType type) {
        this.type = type;
        this.parent = null;
    }

    public ExpressionType getType() {
        return type;
    }

    public void setType(final ExpressionType type) {
        this.type = type;
    }

    public NonTerminal getParent() {
        return parent;
    }

    public void setParent(final NonTerminal parent) {
        this.parent = parent;
    }

    public abstract boolean isTerminal();

    public abstract void accept(ExpressionVisitor visitor);
}
