package net.opentsdb.query.processor.expressions2.nodes;

public interface ExpressionVisitor {
    void enterAddition(Addition a);
    void leaveAddition(Addition a);

    void enterBool(Bool b);
    void leaveBool(Bool b);

    void enterLogicalNegation(LogicalNegation n);
    void leaveLogicalNegation(LogicalNegation n);

    void enterMetric(Metric m);
    void leaveMetric(Metric m);

    void enterDouble(Double d);
    void leaveDouble(Double d);

    void enterLong(Long l);
    void leaveLong(Long l);

    void enterNumericNegation(NumericNegation n);
    void leaveNumericNegation(NumericNegation n);
}
