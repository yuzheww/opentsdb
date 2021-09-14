package net.opentsdb.query.processor.expressions2.eval;

import net.opentsdb.query.processor.expressions2.nodes.Addition;
import net.opentsdb.query.processor.expressions2.nodes.BinaryOperator;
import net.opentsdb.query.processor.expressions2.nodes.Bool;
import net.opentsdb.query.processor.expressions2.nodes.DefaultExpressionVisitor;
import net.opentsdb.query.processor.expressions2.nodes.Double;
import net.opentsdb.query.processor.expressions2.nodes.ExpressionNode;
import net.opentsdb.query.processor.expressions2.nodes.LogicalNegation;
import net.opentsdb.query.processor.expressions2.nodes.Long;
import net.opentsdb.query.processor.expressions2.nodes.Metric;
import net.opentsdb.query.processor.expressions2.nodes.NonTerminal;
import net.opentsdb.query.processor.expressions2.nodes.NumericNegation;
import net.opentsdb.query.processor.expressions2.nodes.Subtraction;
import net.opentsdb.query.processor.expressions2.nodes.UnaryOperator;

public class Simplifier extends DefaultExpressionVisitor {
    static final class ValueTransformer extends DefaultExpressionVisitor {
        ExpressionValue value;

        ValueTransformer() {
            value = null;
        }

        @Override
        public void leaveBool(final Bool b) {
            value = Bool.TRUE == b ?
                BooleanConstantValue.TRUE :
                BooleanConstantValue.FALSE;
        }

        @Override
        public void leaveDouble(final Double d) {
            value = new DoubleValue(null, d.getValue());
        }

        @Override
        public void leaveLong(final Long l) {
            value = new LongValue(null, l.getValue());
        }
    }

    ExpressionNode root;
    ValueTransformer valueTransformer;

    public Simplifier() {
        valueTransformer = new ValueTransformer();
    }

    /**
     * @return A possibly-different root node of a possibly-mutated tree.
     */
    public ExpressionNode simplify(final ExpressionNode expression) {
        root = null;
        expression.accept(this);
        return root;
    }

    ExpressionValue getValueForTerminal(final ExpressionNode node) {
        valueTransformer.value = null;
        node.accept(valueTransformer);
        return valueTransformer.value;
    }

    void simplifyBinaryOperation(final BinaryOperator node, final java.util.function.BinaryOperator<ExpressionValue> operator) {
        final NonTerminal parent = node.getParent();
        if (null == parent) {
            root = node;
        }

        final ExpressionNode lhsOperand = node.getLeftOperand();
        final ExpressionNode rhsOperand = node.getRightOperand();
        if (lhsOperand.isTerminal() && rhsOperand.isTerminal()) {
            final ExpressionValue lhsValue = getValueForTerminal(lhsOperand);
            final ExpressionValue rhsValue = getValueForTerminal(rhsOperand);
            if (lhsValue != null && rhsValue != null) {
                final ExpressionNode simplified = operator.apply(lhsValue, rhsValue).asNode();
                if (parent != null) {
                    parent.replaceChild(node, simplified);
                } else {
                    root = simplified;
                }
            }
        }
    }

    void simplifyUnaryOperation(final UnaryOperator node, final java.util.function.UnaryOperator<ExpressionValue> operator) {
        final NonTerminal parent = node.getParent();
        if (null == parent) {
            root = node;
        }

        final ExpressionNode operand = node.getOperand();
        if (operand.isTerminal()) {
            final ExpressionValue value = getValueForTerminal(operand);
            if (value != null) {
                final ExpressionNode simplified = operator.apply(value).asNode();
                if (parent != null) {
                    parent.replaceChild(node, simplified);
                } else {
                    root = simplified;
                }
            }
        }
    }

    @Override
    public void leaveAddition(final Addition a) {
        simplifyBinaryOperation(a, (l, r) -> l.add(r));
    }

    @Override
    public void leaveSubtraction(Subtraction s) {
        simplifyBinaryOperation(s, (l, r) -> l.subtract(r));
    }

    @Override
    public void leaveLogicalNegation(final LogicalNegation n) {
        simplifyUnaryOperation(n, x -> x.complement());
    }

    @Override
    public void leaveNumericNegation(final NumericNegation n) {
        simplifyUnaryOperation(n, x -> x.negate());
    }
}
