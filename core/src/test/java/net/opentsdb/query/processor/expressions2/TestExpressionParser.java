package net.opentsdb.query.processor.expressions2;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import net.opentsdb.query.processor.expressions2.nodes.Addition;
import net.opentsdb.query.processor.expressions2.nodes.Bool;
import net.opentsdb.query.processor.expressions2.nodes.Double;
import net.opentsdb.query.processor.expressions2.nodes.ExpressionNode;
import net.opentsdb.query.processor.expressions2.nodes.LogicalNegation;
import net.opentsdb.query.processor.expressions2.nodes.Long;
import net.opentsdb.query.processor.expressions2.nodes.Metric;
import net.opentsdb.query.processor.expressions2.nodes.NumericNegation;
//import org.junit.Rule;
import org.junit.Test;
//import org.junit.rules.ExpectedException;

public class TestExpressionParser {
    private final Map<String, ExpressionNode> examples;

    public TestExpressionParser() {
        examples = new HashMap<String, ExpressionNode>() {{
            put("0", new Long(0));
            put("2.5", new Double(2.5));
            put("1 + 2", new Addition(new Long(1), new Long(2)));
            put("3.14159 + 2.71828", new Addition(new Double(3.14159), new Double(2.71828)));
            put("m1 + 0", new Addition(new Metric("m1"), new Long(0)));
            put("25 + m3", new Addition(new Long(25), new Metric("m3")));
            put("1 + (2 + 3)", new Addition(new Long(1), new Addition(new Long(2), new Long(3))));
            put("TrUe", Bool.TRUE);
            put("FaLsE", Bool.FALSE);
            put("!true", new LogicalNegation(Bool.TRUE));
            put("-42", new NumericNegation(new Long(42)));
            put("-foo", new NumericNegation(new Metric("foo")));
            put("-(m1 + 12.5)", new NumericNegation(new Addition(new Metric("m1"), new Double(12.5))));
        }};
    }
    /*
    @Rule
    public ExpectedException exnRule = ExpectedException.none();
    */

    @Test
    public void testParseByExample() {
        for (final Map.Entry<String, ExpressionNode> example : examples.entrySet()) {
            final ExpressionParser parser = new ExpressionParser();
            final ExpressionNode actual = parser.parse(example.getKey());
            assertEquals(example.getValue(), actual);
        }
    }

    /*
    @Test
    public void testTypeError() {
        exnRule.expect(ExpressionException.class);
        exnRule.expectMessage("could not match given domain type to any valid signature in ExpressionOperator");
        new ExpressionParser(new Number(3.14159265));
    }
    */
}
