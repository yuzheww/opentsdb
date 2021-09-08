package net.opentsdb.query.processor.expressions2;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import net.opentsdb.pools.ArrayObjectPool;
import net.opentsdb.pools.DoubleArrayPool;
import net.opentsdb.pools.LongArrayPool;
import net.opentsdb.pools.PooledObject;
import net.opentsdb.query.processor.expressions2.eval.ExpressionValue;

/**
 * This class provides information that evaluation operations may need to do
 * their work.
 */
public class EvaluationContext {
    public static class Builder {
        private EvaluationOptions options;
        private Map<String, ExpressionValue> metrics;
        private LongArrayPool longPool;
        private DoubleArrayPool doublePool;

        public Builder(final EvaluationOptions options) {
            this.options = options;

            metrics = new HashMap<>();
        }

        public Builder define(final String metricName, final ExpressionValue value) {
            metrics.put(metricName, value);
            return this;
        }

        public Builder setLongPool(final LongArrayPool longPool) {
            this.longPool = longPool;
            return this;
        }

        public Builder setDoublePool(final DoubleArrayPool doublePool) {
            this.doublePool = doublePool;
            return this;
        }

        public EvaluationContext build() {
            return new EvaluationContext(this);
        }
    }

    private final EvaluationOptions options;
    private final Map<String, ExpressionValue> metrics;
    private final ArrayObjectPool longPool;
    private final ArrayObjectPool doublePool;
    private final Deque<ExpressionValue> stack;

    private EvaluationContext(final Builder builder) {
        this.options = builder.options;
        this.metrics = Collections.unmodifiableMap(builder.metrics);
        this.longPool = (ArrayObjectPool) builder.longPool;
        this.doublePool = (ArrayObjectPool) builder.doublePool;

        stack = new ArrayDeque<>();
    }

    public void push(final ExpressionValue value) {
        stack.addFirst(value);
    }

    public ExpressionValue pop() {
        final ExpressionValue result = stack.pollFirst();
        if (null == result) {
            throw new ExpressionException("tried to pop from empty stack in EvaluationContext");
        }
        return result;
    }

    public ExpressionValue peek() {
        final ExpressionValue result = stack.peekFirst();
        if (null == result) {
            throw new ExpressionException("tried to peek at top of empty stack in EvaluationContext");
        }
        return result;
    }

    public int stackSize() {
        return stack.size();
    }

    public ExpressionValue lookup(final String metricName) {
        final ExpressionValue result = metrics.get(metricName);
        if (null == result) {
            throw new ExpressionException("tried to look up an undefined metric name in EvaluationContext");
        }
        return result.makeCopy();
    }

    public EvaluationOptions getOptions() {
        return options;
    }

    public PooledObject makeLongArray(final int length) {
        return longPool.claim(length);
    }

    public PooledObject copyLongArray(final long[] values) {
        final PooledObject pooled = makeLongArray(values.length);
        System.arraycopy(values, 0, (long[]) pooled.object(), 0, values.length);
        return pooled;
    }

    public PooledObject makeDoubleArray(final int length) {
        return doublePool.claim(length);
    }

    public PooledObject copyDoubleArray(final double[] values) {
        final PooledObject pooled = makeDoubleArray(values.length);
        System.arraycopy(values, 0, (double[]) pooled.object(), 0, values.length);
        return pooled;
    }
}
