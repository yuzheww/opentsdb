package net.opentsdb.query.processor.expressions2.eval;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class TestLongArrayValue {
    @Test
    public void testGetValueAt() {
        final LongArrayValue u = new LongArrayValue(new long[] {7L, 11L});

        assertEquals(2, u.getLength());
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

        assertEquals(2, u.getLength());
        assertEquals(17L, u.getValueAt(0)); // LHS mutated
        assertEquals(8L, u.getValueAt(1)); // LHS mutated
        assertEquals(3L, x.getValue()); // RHS unchanged
    }

    @Test
    public void testAddLongArray() {
        final LongArrayValue u = new LongArrayValue(new long[] {1L, 2L});
        final LongArrayValue v = new LongArrayValue(new long[] {3L, 4L});
        u.add(v);

        assertEquals(2, u.getLength());
        assertEquals(4L, u.getValueAt(0)); // LHS mutated
        assertEquals(6L, u.getValueAt(1)); // LHS mutated
        assertFalse(v.isLive()); // RHS released
    }
}
