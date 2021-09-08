package net.opentsdb.query.processor.expressions2.eval;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestLongValue extends FactoryBasedTest {
    public TestLongValue() {
        super(2);
    }

    @Test
    public void testGetValue() {
        final LongValue x = new LongValue(factory, 42L);

        assertEquals(42L, x.getValue());
    }

    @Test
    public void testMakeCopy() {
        final LongValue x = new LongValue(factory, 42L);
        final ExpressionValue y = x.makeCopy();

        assertEquals(x, y); // same underlying value
        assertTrue(x != y); // different object
    }

    @Test
    public void testAddLong() {
        final LongValue x = new LongValue(factory, 7L);
        final LongValue y = new LongValue(factory, 11L);
        x.add(y);

        assertEquals(18L, x.getValue()); // LHS mutated
        assertEquals(11L, y.getValue()); // RHS unchanged
    }

    @Test
    public void testAddLongArray() {
        final LongValue x = new LongValue(factory, 1L);
        final LongArrayValue u = new LongArrayValue(factory, new long[] {0L, 5L});
        x.add(u);

        assertEquals(1L, x.getValue()); // LHS unchanged
        assertEquals(2, u.getLength());
        assertEquals(1L, u.getValueAt(0)); // RHS mutated
        assertEquals(6L, u.getValueAt(1)); // RHS mutated
    }
}
