package net.opentsdb.query.processor.expressions2.eval;

import java.util.HashMap;
import java.util.Map;
import net.opentsdb.query.processor.expressions2.ExpressionException;
import net.opentsdb.query.processor.expressions2.ExpressionParser;
import net.opentsdb.query.processor.expressions2.nodes.Addition;
import net.opentsdb.query.processor.expressions2.nodes.Bool;
import net.opentsdb.query.processor.expressions2.nodes.DefaultExpressionVisitor;
import net.opentsdb.query.processor.expressions2.nodes.Double;
import net.opentsdb.query.processor.expressions2.nodes.ExpressionNode;
import net.opentsdb.query.processor.expressions2.nodes.LogicalNegation;
import net.opentsdb.query.processor.expressions2.nodes.Long;
import net.opentsdb.query.processor.expressions2.nodes.Metric;
import net.opentsdb.query.processor.expressions2.nodes.NumericNegation;
import net.opentsdb.query.processor.expressions2.nodes.Subtraction;

public class Evaluator extends DefaultExpressionVisitor {
    static final class TerminalState {
        private final ExpressionValue value;
        private int usesRemaining;

        TerminalState(final ExpressionValue v, final int uses) {
            value = v;
            usesRemaining = uses;
        }

        ExpressionValue use() {
            --usesRemaining;

            if (usesRemaining > 0) {
                return value.makeCopy();
            } else if (0 == usesRemaining) {
                return value;
            } else {
                throw new ExpressionException("tried to use terminal too many times in Evaluator");
            }
        }
    }

    private final ExpressionFactory factory;
    private final EvaluationContext context;
    private final ExpressionParser parser;
    private final Map<String, TerminalState> terminals;

    public Evaluator(final ExpressionFactory factory,
            final EvaluationContext context) {
        this.factory = factory;
        this.context = context;

        parser = new ExpressionParser();
        terminals = new HashMap<>();
    }

    void reset() {
        terminals.clear();
    }

    /**
     * Evaluate the given expression using the configured context.
     * @return An AutoCloseable object with backing storage that may still be
     * held by an object pool.
     */
    public ExpressionValue evaluate(final String expression) {
        reset();

        final ExpressionNode parseTree = parser.parse(expression);

        parseTree.accept(this);

        if (context.stackSize() != 1) {
            throw new ExpressionException("postcondition violation in Evaluator");
        }

        return context.pop();
    }

    @Override
    public void leaveAddition(final Addition a) {
        final ExpressionValue rhs = context.pop();
        final ExpressionValue lhs = context.pop();
        context.push(lhs.add(rhs));
    }

    @Override
    public void leaveSubtraction(Subtraction s) {
        final ExpressionValue rhs = context.pop();
        final ExpressionValue lhs = context.pop();
        context.push(lhs.subtract(rhs));
    }

    @Override
    public void leaveBool(final Bool b) {
        context.push(Bool.TRUE == b ?
            BooleanConstantValue.TRUE :
            BooleanConstantValue.FALSE);
    }

    @Override
    public void leaveLogicalNegation(final LogicalNegation n) {
        context.push(context.pop().complement()); // bools are immutable
    }

    ExpressionValue getTerminalValue(final Metric m) {
        TerminalState s = terminals.get(m.getName());
        if (null == s) {
            ExpressionValue v = context.lookup(m.getName());
            if (factory.getOptions().getAllowMetricReuse()) {
                v = v.makeCopy();
            }

            s = new TerminalState(v, m.getUses());
            terminals.put(m.getName(), s);
        }
        return s.use();
    }

    @Override
    public void leaveMetric(final Metric m) {
        final ExpressionValue v = getTerminalValue(m);
        context.push(v);
    }

    @Override
    public void leaveDouble(final Double d) {
        context.push(factory.makeValueFrom(d.getValue()));
    }

    @Override
    public void leaveLong(final Long l) {
        context.push(factory.makeValueFrom(l.getValue()));
    }

    @Override
    public void leaveNumericNegation(final NumericNegation n) {
        context.peek().negate();
    }
}
