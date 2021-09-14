package net.opentsdb.query.processor.expressions2.nodes;

public class DefaultExpressionVisitor implements ExpressionVisitor {
    @Override
    public void enterAddition(final Addition a) {
    }
    @Override
    public void leaveAddition(final Addition a) {
    }

    @Override
    public void enterSubtraction(final Subtraction s) {
    }
    @Override
    public void leaveSubtraction(final Subtraction s) {
    }

    @Override
    public void enterBool(final Bool b) {
    }
    @Override
    public void leaveBool(final Bool b) {
    }

    @Override
    public void enterLogicalNegation(final LogicalNegation n) {
    }
    @Override
    public void leaveLogicalNegation(final LogicalNegation n) {
    }

    @Override
    public void enterMetric(final Metric m) {
    }
    @Override
    public void leaveMetric(final Metric m) {
    }

    @Override
    public void enterDouble(final Double d) {
    }
    @Override
    public void leaveDouble(final Double d) {
    }

    @Override
    public void enterLong(final Long l) {
    }
    @Override
    public void leaveLong(final Long l) {
    }

    @Override
    public void enterNumericNegation(final NumericNegation n) {
    }
    @Override
    public void leaveNumericNegation(final NumericNegation n) {
    }
}
