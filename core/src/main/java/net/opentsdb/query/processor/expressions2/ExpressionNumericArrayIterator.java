package net.opentsdb.query.processor.expressions2;

import java.util.Map;
import net.opentsdb.core.TSDB;
import net.opentsdb.data.TimeSeries;
import net.opentsdb.data.TimeSeriesDataType;
import net.opentsdb.data.TimeSeriesValue;
import net.opentsdb.data.types.numeric.NumericArrayType;
import net.opentsdb.query.QueryNode;
import net.opentsdb.query.QueryResult;
import net.opentsdb.query.processor.expressions.ExpressionConfig;

public class ExpressionNumericArrayIterator
        extends BaseExpressionNumericArrayIterator<NumericArrayType>
        implements NumericArrayType {
    public ExpressionNumericArrayIterator(final TSDB tsdb,
            final ExpressionConfig config, final QueryResult result,
            final Map<String, TimeSeries> sources) {
        super(tsdb, config, result, sources);

        // We can immediately evaluate because we have arrays.
        final ExpressionValue value = evaluator.evaluate(config.getExpression());

        // TODO: process the value
    }

    @Override
    public TimeSeriesValue<? extends TimeSeriesDataType> next() {
        // TODO

        return this;
    }

    @Override
    public TimeStamp timestamp() {
        return nextTimestamp;
    }

    @Override
    public NumericArrayType value() {
        return this;
    }

}
