package net.opentsdb.query.processor.expressions2;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import net.opentsdb.query.processor.expressions2.eval.Value;

public class EvaluationContext {
    public static class Builder {
        private EvaluationOptions options;
        private Map<String, Value> metrics;

        public Builder(final EvaluationOptions options) {
            this.options = options;

            metrics = new HashMap<>();
        }

        public Builder define(final String metricName, final Value value) {
            metrics.put(metricName, value);
            return this;
        }

        public EvaluationContext build() {
            return new EvaluationContext(this);
        }
    }

    private final EvaluationOptions options;
    private final Map<String, Value> metrics;
    private final Deque<Value> stack;

    private EvaluationContext(final Builder builder) {
        this.options = builder.options;
        this.metrics = Collections.unmodifiableMap(builder.metrics);

        stack = new ArrayDeque<>();
    }

    public void push(final Value value) {
        stack.addFirst(value);
    }

    public Value pop() {
        final Value result = stack.pollFirst();
        if (null == result) {
            throw new ExpressionException("tried to pop from empty stack in EvaluationContext");
        }
        return result;
    }

    public int stackSize() {
        return stack.size();
    }

    public Value lookup(final String metricName) {
        final Value result = metrics.get(metricName);
        if (null == result) {
            throw new ExpressionException("tried to look up an undefined metric name in EvaluationContext");
        }
        return result.makeCopy();
    }

    public EvaluationOptions getOptions() {
        return options;
    }
}
