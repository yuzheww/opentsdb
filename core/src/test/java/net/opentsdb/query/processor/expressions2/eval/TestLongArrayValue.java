package net.opentsdb.query.processor.expressions2.eval;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class TestLongArrayValue {
    @Test
    public void testGetUnderlying() {
        final LongArrayValue u = new LongArrayValue(new long[] {7L, 11L});

        assertArrayEquals(new long[] {7L, 11L}, u.getUnderlying());
    }
    @Test
    public void testGetValueAt() {
        final LongArrayValue u = new LongArrayValue(new long[] {7L, 11L});

        assertEquals(7L, u.getValueAt(0));
        assertEquals(11, u.getValueAt(1));
    }

    @Test
    public void testMakeCopy() {
        final LongArrayValue u = new LongArrayValue(new long[] {1L, 0L});
        final ExpressionValue v = u.makeCopy();

        assertEquals(u, v); // same underlying value
        assertFalse(u == v); // different object
    }

    @Test
    public void testAddLong() {
        final LongArrayValue u = new LongArrayValue(new long[] {14L, 5L});
        final LongValue x = new LongValue(3L);
        u.add(x);

        assertArrayEquals(new long[] {17L, 8L}, u.getUnderlying()); // LHS mutated
        assertEquals(3L, x.getValue()); // RHS unchanged
    }

    @Test
    public void testAddLongArray() {
        final LongArrayValue u = new LongArrayValue(new long[] {1L, 2L});
        final LongArrayValue v = new LongArrayValue(new long[] {3L, 4L});
        u.add(v);

        assertArrayEquals(new long[] {4L, 6L}, u.getUnderlying()); // LHS mutated
        assertArrayEquals(new long[] {3L, 4L}, v.getUnderlying()); // RHS unchanged
    }
}
