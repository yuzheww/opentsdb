package net.opentsdb.query.processor.expressions2;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import net.opentsdb.query.processor.expressions2.eval.LongArrayValue;
import net.opentsdb.query.processor.expressions2.eval.LongConstantValue;
import net.opentsdb.query.processor.expressions2.eval.Value;
import org.junit.Test;

public class TestEvaluator {
    @Test
    public void testByExample() {
        final EvaluationOptions options = new EvaluationOptions.Builder().
            build();
        final EvaluationContext context = new EvaluationContext.Builder(options).
            define("nat", new LongArrayValue(new long[] {0, 1, 2, 3, 4})).
            define("natSquared", new LongArrayValue(new long[] {0, 1, 4, 9, 16})).
            build();
        final Evaluator evaluator = new Evaluator(context);

        final Map<String, Value> examples = new HashMap<String, Value>() {{
            put("1 + 2", new LongConstantValue(3));
            put("nat + 1", new LongArrayValue(new long[] {1, 2, 3, 4, 5}));
            put("nat + natSquared", new LongArrayValue(new long[] {0, 2, 6, 12, 20}));
            put("7 - 3", new LongConstantValue(4));
            put("nat - 2", new LongArrayValue(new long[] {-2, -1, 0, 1, 2}));
            put("1 - nat", new LongArrayValue(new long[] {1, 0, -1, -2, -3}));
            put("natSquared - nat", new LongArrayValue(new long[] {0, 0, 2, 6, 12}));
        }};

        for (final Map.Entry<String, Value> example : examples.entrySet()) {
            final Value result = evaluator.evaluate(example.getKey());
            assertEquals(example.getValue(), result);
        }
    }
}