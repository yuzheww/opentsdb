package net.opentsdb.query.processor.expressions2.eval;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class TestDoubleArrayValue extends FactoryBasedTest {
    public TestDoubleArrayValue() {
        super(2);
    }

    @Test
    public void testGetValueAt() {
        final DoubleArrayValue u = new DoubleArrayValue(factory, new double[] {3.14159, 2.71828});

        assertEquals(2, u.getLength());
        assertEquals(3.14159, u.getValueAt(0), 1e-6);
        assertEquals(2.71828, u.getValueAt(1), 1e-6);
    }

    @Test
    public void testMakeCopy() {
        final DoubleArrayValue u = new DoubleArrayValue(factory, new double[] {1d, 0d});
        final ExpressionValue v = u.makeCopy();

        assertEquals(u, v); // same underlying value
        assertFalse(u == v); // different object
    }

    @Test
    public void testAddDouble() {
        final DoubleArrayValue u = new DoubleArrayValue(factory, new double[] {3.25, 7.5});
        final DoubleValue x = new DoubleValue(factory, 2.1);
        u.add(x);

        assertEquals(2, u.getLength());
        assertEquals(5.35, u.getValueAt(0), 1e-3); // LHS mutated
        assertEquals(9.6, u.getValueAt(1), 1e-3); // LHS mutated
        assertEquals(2.1, x.getValue(), 1e-3); // RHS unchanged
    }

    @Test
    public void testAddDoubleArray() {
        final DoubleArrayValue u = new DoubleArrayValue(factory, new double[] {2.25, 4d});
        final DoubleArrayValue v = new DoubleArrayValue(factory, new double[] {0.5, 3.25});
        u.add(v);

        assertEquals(2, u.getLength());
        assertEquals(2.75, u.getValueAt(0), 1e-3); // LHS mutated
        assertEquals(7.25, u.getValueAt(1), 1e-3); // LHS mutated
        assertFalse(v.isLive()); // RHS released
    }
}
