package net.opentsdb.query.processor.expressions2;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import net.opentsdb.query.processor.expressions2.eval.ExpressionValue;

public class EvaluationContext {
    public static class Builder {
        private EvaluationOptions options;
        private Map<String, ExpressionValue> metrics;

        public Builder(final EvaluationOptions options) {
            this.options = options;

            metrics = new HashMap<>();
        }

        public Builder define(final String metricName, final ExpressionValue value) {
            metrics.put(metricName, value);
            return this;
        }

        public EvaluationContext build() {
            return new EvaluationContext(this);
        }
    }

    private final EvaluationOptions options;
    private final Map<String, ExpressionValue> metrics;
    private final Deque<ExpressionValue> stack;

    private EvaluationContext(final Builder builder) {
        this.options = builder.options;
        this.metrics = Collections.unmodifiableMap(builder.metrics);

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
}
