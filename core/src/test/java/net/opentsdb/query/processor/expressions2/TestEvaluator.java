package net.opentsdb.query.processor.expressions2;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import net.opentsdb.query.processor.expressions2.eval.LongArrayValue;
import net.opentsdb.query.processor.expressions2.eval.LongConstantValue;
import net.opentsdb.query.processor.expressions2.eval.Value;
import org.junit.Test;

public class TestEvaluator {
    final EvaluationContext exampleContext;
    private final Map<String, Value> examples;

    public TestEvaluator() {
        exampleContext = new EvaluationContext.Builder().
            define("nat", new LongArrayValue(new long[] {0, 1, 2, 3, 4})).
            build();

        examples = new HashMap<String, Value>() {{
            put("1 + 2", new LongConstantValue(3));
            put("nat + 1", new LongArrayValue(new long[] {1, 2, 3, 4, 5}));
        }};
    }

    @Test
    public void testByExample() {
        final Evaluator evaluator = new Evaluator(exampleContext);
        for (final Map.Entry<String, Value> example : examples.entrySet()) {
            final Value result = evaluator.evaluate(example.getKey());
            assertEquals(example.getValue(), result);
        }
    }
}
