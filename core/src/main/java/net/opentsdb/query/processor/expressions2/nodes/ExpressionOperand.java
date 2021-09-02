package net.opentsdb.query.processor.expressions2.nodes;

public abstract class ExpressionOperand extends ExpressionNode {
    @Override
    public boolean isTerminal() {
        return true;
    }
}
