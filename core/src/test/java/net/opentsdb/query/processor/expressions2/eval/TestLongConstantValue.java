package net.opentsdb.query.processor.expressions2.eval;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestLongConstantValue {
    @Test
    public void testGetValue() {
        final LongConstantValue x = new LongConstantValue(42L);

        assertEquals(42L, x.getValue());
    }

    @Test
    public void testMakeCopy() {
        final LongConstantValue x = new LongConstantValue(42L);
        final Value y = x.makeCopy();

        assertEquals(x, y); // same underlying value
        assertTrue(x != y); // different object
    }

    @Test
    public void testAddLong() {
        final LongConstantValue x = new LongConstantValue(7L);
        final LongConstantValue y = new LongConstantValue(11L);
        x.add(y);

        assertEquals(18L, x.getValue()); // LHS mutated
        assertEquals(11L, y.getValue()); // RHS unchanged
    }

    @Test
    public void testAddLongArray() {
        final LongConstantValue x = new LongConstantValue(1L);
        final LongArrayValue u = new LongArrayValue(new long[] {0L, 5L});
        x.add(u);

        assertEquals(1L, x.getValue()); // LHS unchanged
        assertArrayEquals(new long[] {1L, 6L}, u.getUnderlying()); // RHS mutated
    }
}
