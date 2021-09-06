package net.opentsdb.query.processor.expressions2;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import net.opentsdb.query.processor.expressions2.eval.BooleanConstantValue;
import net.opentsdb.query.processor.expressions2.eval.DoubleArrayValue;
import net.opentsdb.query.processor.expressions2.eval.DoubleValue;
import net.opentsdb.query.processor.expressions2.eval.ExpressionValue;
import net.opentsdb.query.processor.expressions2.eval.LongArrayValue;
import net.opentsdb.query.processor.expressions2.eval.LongValue;
import org.junit.Test;

public class TestEvaluator {
    @Test
    public void testByExample() {
        final EvaluationOptions options = new EvaluationOptions.Builder().
            setInfectiousNaN(false).
            build();

        final EvaluationContext context = new EvaluationContext.Builder(options).
            define("nat", new LongArrayValue(new long[] {0, 1, 2, 3, 4})).
            define("natSquared", new LongArrayValue(new long[] {0, 1, 4, 9, 16})).
            define("ieee754", new DoubleArrayValue(new double[] {1.25, 3d, 0.7, 8.1, 4.75})).
            build();

        final Evaluator evaluator = new Evaluator(context);

        final Map<String, ExpressionValue> examples = new HashMap<String, ExpressionValue>() {{
            put("true", BooleanConstantValue.TRUE);
            put("FaLsE", BooleanConstantValue.FALSE);
            put("!TrUe", BooleanConstantValue.FALSE);
            put("!false", BooleanConstantValue.TRUE);
            put("1 + 2", new LongValue(3));
            put("2 + 3.14159265", new DoubleValue(5.14159265));
            put("nat + 1", new LongArrayValue(new long[] {1, 2, 3, 4, 5}));
            put("nat + 2.5", new DoubleArrayValue(new double[] {2.5, 3.5, 4.5, 5.5, 6.5}));
            put("nat + 2.0", new LongArrayValue(new long [] {2, 3, 4, 5, 6}));
            put("2.0 + nat", new LongArrayValue(new long [] {2, 3, 4, 5, 6}));
            put("nat + natSquared", new LongArrayValue(new long[] {0, 2, 6, 12, 20}));
            put("natSquared + ieee754 + nat", new DoubleArrayValue(new double[] {1.25, 5d, 6.7, 20.1, 24.75}));
            put("7 - 3", new LongValue(4));
            put("nat - 2", new LongArrayValue(new long[] {-2, -1, 0, 1, 2}));
            put("-(nat - 2)", new LongArrayValue(new long[] {2, 1, 0, -1, -2}));
            put("nat - 1.5", new DoubleArrayValue(new double[] {-1.5, -0.5, 0.5, 1.5, 2.5}));
            put("nat - 1.0", new LongArrayValue(new long[] {-1, 0, 1, 2, 3}));
            put("1 - nat", new LongArrayValue(new long[] {1, 0, -1, -2, -3}));
            put("natSquared - nat", new LongArrayValue(new long[] {0, 0, 2, 6, 12}));
            put("ieee754 + 1", new DoubleArrayValue(new double[] {2.25, 4d, 1.7, 9.1, 5.75}));
            put("1 + ieee754", new DoubleArrayValue(new double[] {2.25, 4d, 1.7, 9.1, 5.75}));
            put("ieee754 - 3", new DoubleArrayValue(new double[] {-1.75, 0d, -2.3, 5.1, 1.75}));
            put("-(ieee754 - 3)", new DoubleArrayValue(new double[] {1.75, -0d, 2.3, -5.1, -1.75}));
            put("natSquared - ieee754 + nat", new DoubleArrayValue(new double[] {-1.25, -1d, 5.3, 3.9, 15.25}));
        }};

        for (final Map.Entry<String, ExpressionValue> example : examples.entrySet()) {
            assertEquals(example.getValue(), evaluator.evaluate(example.getKey()));
        }
    }
}
