package net.opentsdb.query.processor.expressions2.nodes;

public abstract class NonTerminal extends ExpressionNode {
    /**
     * Creates a new NonTerminal object.
     */
    public NonTerminal() {
        super();
    }

    /**
     * @param replaceThis
     * @param withThis
     */
    public abstract void replaceChild(final ExpressionNode replaceThis,
        final ExpressionNode withThis);

    @Override
    public boolean isTerminal() {
        return false;
    }
}
