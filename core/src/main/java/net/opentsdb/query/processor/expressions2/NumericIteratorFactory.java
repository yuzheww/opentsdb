package net.opentsdb.query.processor.expressions2;

import java.util.Collection;
import java.util.Map;

import com.google.common.reflect.TypeToken;

import net.opentsdb.data.TimeSeries;
import net.opentsdb.data.TimeSeriesDataType;
import net.opentsdb.data.TypedTimeSeriesIterator;
import net.opentsdb.data.types.numeric.NumericArrayType;
import net.opentsdb.query.QueryIteratorFactory;
import net.opentsdb.query.processor.BaseQueryNodeFactory;

public class NumericIteratorFactory extends BaseQueryNodeFactory<ExpressionParseNode, ExpressionNode> {
    public NumericIteratorFactory() {
        super();
        registerIteratorFactory(NumericArrayType.TYPE,
            new NumericArrayIteratorFactory());
    }

    class NumericArrayIteratorFactory implements QueryIteratorFactory<ExpressionNode, NumericArrayType> {
        @Override
        public TypedTimeSeriesIterator<T> newIterator(
                final ExpressionNode node,
                final QueryResult result,
                final Collection<TimeSeries> sources,
                final TypeToken<? extends TimeSeriesDataType> type) {
            throw new UnsupportedOperationException("Expression iterators must have a map.");
        }

        @Override
        public TypedTimeSeriesIterator<T> newIterator(
                final ExpressionNode node,
                final QueryResult result,
                final Map<String, TimeSeries> sources,
                final TypeToken<? extends TimeSeriesDataType> type) {
            return new ExpressionNumericArrayIterator(tsdb, config /* TODO */, sources);
        }

        @Override
        public Collection<TypeToken<? extends TimeSeriesDataType>> types() {
            return NumericArrayType.SINGLE_LIST;
        }
    }
}
