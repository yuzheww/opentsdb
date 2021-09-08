package net.opentsdb.query.processor.expressions2.eval;

import net.opentsdb.query.processor.expressions2.ExpressionException;
import net.opentsdb.query.processor.expressions2.ExpressionParser;
import net.opentsdb.query.processor.expressions2.nodes.Addition;
import net.opentsdb.query.processor.expressions2.nodes.Bool;
import net.opentsdb.query.processor.expressions2.nodes.Double;
import net.opentsdb.query.processor.expressions2.nodes.ExpressionNode;
import net.opentsdb.query.processor.expressions2.nodes.ExpressionVisitor;
import net.opentsdb.query.processor.expressions2.nodes.LogicalNegation;
import net.opentsdb.query.processor.expressions2.nodes.Long;
import net.opentsdb.query.processor.expressions2.nodes.Metric;
import net.opentsdb.query.processor.expressions2.nodes.NumericNegation;
import net.opentsdb.query.processor.expressions2.nodes.Subtraction;

public class Evaluator implements ExpressionVisitor {
    private final ExpressionFactory factory;
    private final EvaluationContext context;
    private final ExpressionParser parser;

    public Evaluator(final ExpressionFactory factory,
            final EvaluationContext context) {
        this.factory = factory;
        this.context = context;

        parser = new ExpressionParser();
    }

    /**
     * Evaluate the given expression using the configured context.
     * @return An AutoCloseable object with backing storage that may still be
     * held by an object pool.
     */
    public ExpressionValue evaluate(final String expression) {
        final ExpressionNode parseTree = parser.parse(expression);

        parseTree.accept(this);

        if (context.stackSize() != 1) {
            throw new ExpressionException("postcondition violation in Evaluator");
        }

        return context.pop();
    }

    @Override public void enterAddition(final Addition a) {}
    @Override public void leaveAddition(final Addition a) {
        final ExpressionValue rhs = context.pop();
        final ExpressionValue lhs = context.pop();
        context.push(lhs.add(rhs));
    }

    @Override public void enterSubtraction(Subtraction s) {}
    @Override public void leaveSubtraction(Subtraction s) {
        final ExpressionValue rhs = context.pop();
        final ExpressionValue lhs = context.pop();
        context.push(lhs.subtract(rhs));
    }

    @Override public void enterBool(final Bool b) {}
    @Override public void leaveBool(final Bool b) {
        context.push(Bool.TRUE == b ?
            BooleanConstantValue.TRUE :
            BooleanConstantValue.FALSE);
    }

    @Override public void enterLogicalNegation(final LogicalNegation n) {}
    @Override public void leaveLogicalNegation(final LogicalNegation n) {
        context.push(context.pop().complement()); // bools are immutable
    }

    @Override public void enterMetric(final Metric m) {}
    @Override public void leaveMetric(final Metric m) {
        context.push(context.lookup(m.getName()));
    }

    @Override public void enterDouble(final Double d) {}
    @Override public void leaveDouble(final Double d) {
        context.push(factory.makeValueFrom(d.getValue()));
    }

    @Override public void enterLong(final Long l) {}
    @Override public void leaveLong(final Long l) {
        context.push(factory.makeValueFrom(l.getValue()));
    }

    @Override public void enterNumericNegation(final NumericNegation n) {}
    @Override public void leaveNumericNegation(final NumericNegation n) {
        context.peek().negate();
    }
}
