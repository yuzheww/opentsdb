package net.opentsdb.query.processor.expressions2;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import net.opentsdb.expressions.parser.MetricExpression2Lexer;
import net.opentsdb.expressions.parser.MetricExpression2Listener;
import net.opentsdb.expressions.parser.MetricExpression2Parser;
import net.opentsdb.query.processor.expressions2.nodes.Addition;
import net.opentsdb.query.processor.expressions2.nodes.Bool;
import net.opentsdb.query.processor.expressions2.nodes.Double;
import net.opentsdb.query.processor.expressions2.nodes.ExpressionNode;
import net.opentsdb.query.processor.expressions2.nodes.LogicalNegation;
import net.opentsdb.query.processor.expressions2.nodes.Long;
import net.opentsdb.query.processor.expressions2.nodes.Metric;
import net.opentsdb.query.processor.expressions2.nodes.NumericNegation;
import net.opentsdb.query.processor.expressions2.nodes.Subtraction;
import net.opentsdb.query.processor.expressions2.nodes.Terminal;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

public class ExpressionParser extends DefaultErrorStrategy
        implements MetricExpression2Listener {
    private final Deque<ExpressionNode> stack;
    private final Map<String, Terminal> terminals;

    public ExpressionParser() {
        stack = new ArrayDeque<ExpressionNode>();
        terminals = new HashMap<String, Terminal>();
    }

    void reset() {
        stack.clear();
        terminals.clear();
    }

    public ExpressionNode parse(final String expression) {
        reset();

        final ANTLRInputStream istream = new ANTLRInputStream(expression);
        final MetricExpression2Lexer lexer = new MetricExpression2Lexer(istream);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final MetricExpression2Parser parser = new MetricExpression2Parser(tokens);

        //parser.removeErrorListeners();
        //parser.setErrorHandler(this);

        final MetricExpression2Parser.ProgContext parseTree = parser.prog();

        final ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(this, parseTree);

        if (1 != stack.size()) {
            throw new ExpressionException("postcondition violated: after parsing, stack must have only 1 element");
        }

        return stack.pop();
    }

    void push(final ExpressionNode node) {
        stack.addFirst(node);
    }

    ExpressionNode pop() {
        final ExpressionNode result = stack.pollFirst();
        if (null == result) {
            throw new ExpressionException("tried to pop from empty stack in ExpressionParser");
        }
        return result;
    }

    @Override public void enterProg(final MetricExpression2Parser.ProgContext ctx) {}
    @Override public void exitProg(final MetricExpression2Parser.ProgContext ctx) {}
    @Override public void enterAdd(final MetricExpression2Parser.AddContext ctx) {}

    @Override public void exitAdd(final MetricExpression2Parser.AddContext ctx) {
        final ExpressionNode rhs = pop();
        final ExpressionNode lhs = pop();

        switch (ctx.op.getType()) {
        case MetricExpression2Parser.ADD:
            push(new Addition(lhs, rhs));
            break;

        case MetricExpression2Parser.SUB:
            push(new Subtraction(lhs, rhs));
            break;
        }
    }

    @Override public void enterOr(final MetricExpression2Parser.OrContext ctx) {}
    @Override public void exitOr(final MetricExpression2Parser.OrContext ctx) {}
    @Override public void enterMul(final MetricExpression2Parser.MulContext ctx) {}
    @Override public void exitMul(final MetricExpression2Parser.MulContext ctx) {}
    @Override public void enterAnd(final MetricExpression2Parser.AndContext ctx) {}
    @Override public void exitAnd(final MetricExpression2Parser.AndContext ctx) {}
    @Override public void enterCmp(final MetricExpression2Parser.CmpContext ctx) {}
    @Override public void exitCmp(final MetricExpression2Parser.CmpContext ctx) {}
    @Override public void enterPow(final MetricExpression2Parser.PowContext ctx) {}
    @Override public void exitPow(final MetricExpression2Parser.PowContext ctx) {}
    @Override public void enterUnary(final MetricExpression2Parser.UnaryContext ctx) {}

    @Override public void exitUnary(final MetricExpression2Parser.UnaryContext ctx) {
        if (ctx.op.getType() == MetricExpression2Parser.NOT) {
            push(new LogicalNegation(pop()));
        } else if (ctx.op.getType() == MetricExpression2Parser.SUB) {
            push(new NumericNegation(pop()));
        } else {
            throw new ExpressionException("parser bug: unhandled operator '" + ctx.op.getText() + "'");
        }
    }

    @Override public void enterAtom(final MetricExpression2Parser.AtomContext ctx) {}
    @Override public void exitAtom(final MetricExpression2Parser.AtomContext ctx) {}
    @Override public void enterTernary(final MetricExpression2Parser.TernaryContext ctx) {}
    @Override public void exitTernary(final MetricExpression2Parser.TernaryContext ctx) {}
    @Override public void enterOperand(final MetricExpression2Parser.OperandContext ctx) {}
    @Override public void exitOperand(final MetricExpression2Parser.OperandContext ctx) {}
    @Override public void enterNumeric_literal(final MetricExpression2Parser.Numeric_literalContext ctx) {}

    @Override public void exitNumeric_literal(final MetricExpression2Parser.Numeric_literalContext ctx) {
        final String encoded = ctx.getText();

        // We want to treat every number as a long, if possible.
        try {
            final long lval = java.lang.Long.parseLong(encoded);
            push(new Long(lval));
            return;
        } catch (final NumberFormatException e) { /* unsurprising */ }

        // If necessary, we will resort to floating-point representation.
        try {
            final double dval = java.lang.Double.parseDouble(encoded);
            push(new Double(dval));
        } catch (final NumberFormatException e) {
            throw new ExpressionException("could not parse '" + encoded + "' as long or double: " + e.getMessage());
        }
    }

    @Override public void enterBoolean_literal(final MetricExpression2Parser.Boolean_literalContext ctx) {}

    @Override public void exitBoolean_literal(final MetricExpression2Parser.Boolean_literalContext ctx) {
        final String encoded = ctx.getText().toLowerCase();
        if (encoded.equals("true")) {
            push(Bool.TRUE);
        } else if (encoded.equals("false")) {
            push(Bool.FALSE);
        } else {
            throw new ExpressionException("parser bug: should not have received '" + encoded + "' in exitBoolean_literal()");
        }
    }

    @Override public void enterMetric(final MetricExpression2Parser.MetricContext ctx) {}
    @Override public void exitMetric(final MetricExpression2Parser.MetricContext ctx) {
        final String metricName = ctx.getText();
        Terminal result = terminals.get(metricName);
        if (null == result) {
            result = new Metric(metricName);
            terminals.put(metricName, result);
        } else {
            result.recordUse();
        }
        push(result);
    }

    @Override public void enterEveryRule(final ParserRuleContext ctx) {}
    @Override public void exitEveryRule(final ParserRuleContext ctx) {}
    @Override public void visitTerminal(final TerminalNode node) {}
    @Override public void visitErrorNode(final ErrorNode node) {}
}
