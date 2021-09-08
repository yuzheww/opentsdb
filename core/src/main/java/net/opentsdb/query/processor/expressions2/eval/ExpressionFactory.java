package net.opentsdb.query.processor.expressions2.eval;

import net.opentsdb.pools.ArrayObjectPool;
import net.opentsdb.pools.PooledObject;

public class ExpressionFactory {
    private final EvaluationOptions options;
    private final ArrayObjectPool longPool;
    private final ArrayObjectPool doublePool;

    public ExpressionFactory(final EvaluationOptions options,
            final ArrayObjectPool longPool, final ArrayObjectPool doublePool) {
        this.options = options;
        this.longPool = longPool;
        this.doublePool = doublePool;
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

    public ExpressionValue makeValueFrom(final long n) {
        return new LongValue(this, n);
    }

    public ExpressionValue makeValueFrom(final long[] a) {
        return new LongArrayValue(this, a);
    }

    public ExpressionValue makeValueFrom(final double x) {
        return new DoubleValue(this, x);
    }

    public ExpressionValue makeValueFrom(final double[] a) {
        return new DoubleArrayValue(this, a);
    }
}
