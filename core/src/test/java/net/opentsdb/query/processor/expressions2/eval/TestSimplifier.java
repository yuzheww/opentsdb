package net.opentsdb.query.processor.expressions2.eval;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import net.opentsdb.query.processor.expressions2.ExpressionParser;
import net.opentsdb.query.processor.expressions2.nodes.Addition;
import net.opentsdb.query.processor.expressions2.nodes.Bool;
import net.opentsdb.query.processor.expressions2.nodes.Double;
import net.opentsdb.query.processor.expressions2.nodes.ExpressionNode;
import net.opentsdb.query.processor.expressions2.nodes.LogicalNegation;
import net.opentsdb.query.processor.expressions2.nodes.Long;
import net.opentsdb.query.processor.expressions2.nodes.Metric;
import net.opentsdb.query.processor.expressions2.nodes.NumericNegation;
import net.opentsdb.query.processor.expressions2.nodes.Subtraction;
import org.junit.Test;

public class TestSimplifier {
    private final Map<String, ExpressionNode> examples;

    public TestSimplifier() {
        examples = new HashMap<String, ExpressionNode>() {{
            put("1 + 2", new Long(3));
            put("2 - 1", new Long(1));
        }};
    }

    @Test
    public void testSimplifyByExample() {
        for (final Map.Entry<String, ExpressionNode> example : examples.entrySet()) {
            // Here, we assume the parser, which we test elsewhere, is correct.
            final ExpressionParser parser = new ExpressionParser();
            ExpressionNode parseTree = parser.parse(example.getKey());

            // Then we test the simplifier, assuming the parser is correct.
            final Simplifier simplifier = new Simplifier();
            parseTree = simplifier.simplify(parseTree);

            assertEquals(example.getValue(), parseTree);
        }
    }
}
