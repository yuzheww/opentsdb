package net.opentsdb.query.processor.expressions2;

import com.google.common.reflect.TypeToken;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.opentsdb.core.TSDB;
import net.opentsdb.data.MillisecondTimeStamp;
import net.opentsdb.data.TimeSeries;
import net.opentsdb.data.TimeSeriesDataType;
import net.opentsdb.data.TimeSeriesValue;
import net.opentsdb.data.TimeStamp;
import net.opentsdb.data.types.numeric.NumericType;
import net.opentsdb.pools.ArrayObjectPool;
import net.opentsdb.pools.DoubleArrayPool;
import net.opentsdb.pools.LongArrayPool;
import net.opentsdb.query.QueryIterator;
import net.opentsdb.query.QueryNode;
import net.opentsdb.query.processor.expressions.ExpressionConfig;
import net.opentsdb.query.processor.expressions2.eval.EvaluationContext;
import net.opentsdb.query.processor.expressions2.eval.EvaluationOptions;
import net.opentsdb.query.processor.expressions2.eval.ExpressionFactory;
import net.opentsdb.query.processor.expressions2.eval.Simplifier;
import net.opentsdb.query.processor.expressions2.nodes.DefaultExpressionVisitor;
import net.opentsdb.query.processor.expressions2.nodes.ExpressionNode;
import net.opentsdb.query.processor.expressions2.nodes.Metric;

public abstract class BaseExpressionNumericIterator<T extends TimeSeriesDataType>
        implements QueryIterator, TimeSeriesValue<T> {
    static final class Metadata {
        Set<String> variablesUsed;

        public Metadata() {
            variablesUsed = new HashSet<>();
        }
    }

    static final class MetadataCollector extends DefaultExpressionVisitor {
        final Metadata meta;

        MetadataCollector(final Metadata meta) {
            this.meta = meta;
        }

        @Override
        public void leaveMetric(final Metric m) {
            meta.variablesUsed.add(m.getName());
        }
    }

    /** TODO */
    protected TimeStamp nextTimestamp;

    /** TODO */
    protected boolean hasMoreValues;

    /** This factory enables any evaluator to create new objects. */
    protected final ExpressionFactory factory;

    /** This builder collects data as it arrives from other nodes. */
    protected final EvaluationContext.Builder contextBuilder;

    /** This metadata object describes the expression. */
    protected final Metadata metadata;

    /** This graph represents the expression. */
    protected final ExpressionNode expression;

    /**
     * C-tor.
     * @param tsdb
     * @param config
     * @param sources
     */
    public BaseExpressionNumericIterator(final TSDB tsdb,
            final ExpressionConfig config,
            final Map<String, TimeSeries> sources) {
        // Initialize state objects.
        nextTimestamp = new MillisecondTimeStamp(0);
        hasMoreValues = false;

        // Build options object based on expression configuration.
        final EvaluationOptions options = new EvaluationOptions.Builder().
            setInfectiousNaN(config.getInfectiousNan()).
            setForceFloatingPointDivision(true). // TODO: make this configurable
            setAllowMetricReuse(false). // contexts will not be reusable
            build();

        // Fetch the pools we need from TSDB.
        final ArrayObjectPool longPool = (ArrayObjectPool) tsdb.getRegistry().
            getObjectPool(LongArrayPool.TYPE);
        final ArrayObjectPool doublePool = (ArrayObjectPool) tsdb.getRegistry().
            getObjectPool(DoubleArrayPool.TYPE);

        // With the above, we can build our factory.
        factory = new ExpressionFactory(options, longPool, doublePool);

        // We will collect data here as it comes in.
        contextBuilder = new EvaluationContext.Builder();

        // Analyze the expression to be evaluated.
        final String rawExpression = config.getExpression();
        final ExpressionParser parser = new ExpressionParser();
        ExpressionNode parseTree = parser.parse(rawExpression);
        final Simplifier simplifier = new Simplifier();
        expression = simplifier.simplify(parseTree);

        // We can now collect metadata from the expression.
        metadata = new Metadata();
        final MetadataCollector collector = new MetadataCollector(metadata);
        expression.accept(collector);
    }

    @Override
    public boolean hasNext() {
        return hasMoreValues;
    }

    @Override
    public TypeToken<? extends TimeSeriesDataType> getType() {
        return NumericType.TYPE;
    }
}
