package net.opentsdb.query.processor.expressions2.eval;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class TestDoubleArrayValue {
    @Test
    public void testGetUnderlying() {
        final DoubleArrayValue u = new DoubleArrayValue(new double[] {2.71828, 3.14159});

        assertArrayEquals(new double[] {2.71828, 3.14159}, u.getUnderlying(), 1e-6);
    }
    @Test
    public void testGetValueAt() {
        final DoubleArrayValue u = new DoubleArrayValue(new double[] {3.14159, 2.71828});

        assertEquals(3.14159, u.getValueAt(0), 1e-6);
        assertEquals(2.71828, u.getValueAt(1), 1e-6);
    }

    @Test
    public void testMakeCopy() {
        final DoubleArrayValue u = new DoubleArrayValue(new double[] {1d, 0d});
        final Value v = u.makeCopy();

        assertEquals(u, v); // same underlying value
        assertFalse(u == v); // different object
    }

    @Test
    public void testAddDouble() {
        final DoubleArrayValue u = new DoubleArrayValue(new double[] {3.25, 7.5});
        final DoubleConstantValue x = new DoubleConstantValue(2.1);
        u.add(x);

        assertArrayEquals(new double[] {5.35, 9.6}, u.getUnderlying(), 1e-3); // LHS mutated
        assertEquals(2.1, x.getValue(), 1e-2); // RHS unchanged
    }

    @Test
    public void testAddDoubleArray() {
        final DoubleArrayValue u = new DoubleArrayValue(new double[] {2.25, 4d});
        final DoubleArrayValue v = new DoubleArrayValue(new double[] {0.5, 3.25});
        u.add(v);

        assertArrayEquals(new double[] {2.75, 7.25}, u.getUnderlying(), 1e-3); // LHS mutated
        assertArrayEquals(new double[] {0.5, 3.25}, v.getUnderlying(), 1e-3); // RHS unchanged
    }
}
