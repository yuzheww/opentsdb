package net.opentsdb.query.processor.expressions2;

import java.util.Map;
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
import net.opentsdb.query.QueryResult;
import net.opentsdb.query.processor.expressions.ExpressionConfig;

public abstract class BaseExpressionNumericIterator<T extends TimeSeriesDataType>
        implements QueryIterator, TimeSeriesValue<T> {
    protected final TimeStamp nextTimestamp;

    protected final Evaluator evaluator;

    public BaseExpressionNumericIterator(final TSDB tsdb,
            final ExpressionConfig config, final QueryResult result,
            final Map<String, TimeSeries> sources) {
        // Initialze state objects.
        nextTimestamp = new MillisecondTimeStamp(0);

        // Build options object based on expression configuration.
        final EvaluationOptions options = new EvaluationOptions.Builder().
            setInfectiousNaN(config.getInfectiousNan()).
            setForceFloatingPointDivision(true). // TODO: make this configurable
            build();

        // Fetch the pools we need from TSDB.
        final ArrayObjectPool longPool = tsdb.getRegistry().getObjectPool(
            LongArrayPool.TYPE);
        final ArrayObjectPool doublePool = tsdb.getRegistry().getObjectPool(
            DoubleArrayPool.TYPE);

        // With the above, we can build our factory.
        final ExpressionFactory factory = new ExpressionFactory(options,
            longPool, doublePool);

        // Build context object based on the sources.
        final EvaluationContext context = new EvaluationContext.Builder().
            // TODO: define metrics from sources
            build();

        // Finally, we can construct the evaluator itself.
        evaluator = new Evaluator(factory, context);
    }

    @Override
    public boolean hasNext() {
        return false; // TODO
    }

    @Override
    public TypeToken<? extends TimeSeriesDataType> getType() {
        return NumericType.TYPE;
    }
}
