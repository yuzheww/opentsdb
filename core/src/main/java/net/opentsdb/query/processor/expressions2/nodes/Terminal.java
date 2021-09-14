package net.opentsdb.query.processor.expressions2.nodes;

import net.opentsdb.query.processor.expressions2.types.ExpressionType;

public abstract class Terminal extends ExpressionNode {
    // Mutable subclasses track how often they appear in the expression. We use
    // that information during evaluation to minimize copying mutable values.
    private int uses;

    /**
     * Creates a new Terminal object with a use count of one.
     * @param type
     */
    public Terminal(final ExpressionType type) {
        super(type);

        // By definition, if we are creating a new terminal, then it must have
        // been used once.
        uses = 1;
    }

    /**
     * Get this Terminal object's use count.
     * @return This Terminal object's use count.
     */
    public int getUses() {
        return uses;
    }

    /**
     * Increment this Terminal object's use count.
     */
    public void recordUse() {
        ++uses;
    }

    /**
     * Get the canonical, textual representation of this terminal.
     * @return The canonical, textual representation of this terminal.
     */
    public abstract String getCanonicalRepresentation();

    @Override
    public boolean isTerminal() {
        return true;
    }
}
