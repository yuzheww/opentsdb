package net.opentsdb.query.processor.expressions2.eval;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestDoubleConstantValue {
    @Test
    public void testGetValue() {
        final DoubleConstantValue x = new DoubleConstantValue(3.14159265);

        assertEquals(3.14159265, x.getValue(), 1e-9);
    }

    @Test
    public void testMakeCopy() {
        final DoubleConstantValue x = new DoubleConstantValue(2.71828);
        final Value y = x.makeCopy();

        assertEquals(x, y); // same underlying value
        assertTrue(x != y); // different object
    }

    @Test
    public void testAddDouble() {
        final DoubleConstantValue x = new DoubleConstantValue(2.5);
        final DoubleConstantValue y = new DoubleConstantValue(9.6);
        x.add(y);

        assertEquals(12.1, x.getValue(), 1e-2); // LHS mutated
        assertEquals(9.6, y.getValue(), 1e-2); // RHS unchanged
    }

    @Test
    public void testAddDoubleArray() {
        final DoubleConstantValue x = new DoubleConstantValue(7d);
        final DoubleArrayValue u = new DoubleArrayValue(new double[] {0.65, 4.3});
        x.add(u);

        assertEquals(7d, x.getValue(), 1e-1); // LHS unchanged
        assertArrayEquals(new double[] {7.65, 11.3}, u.getUnderlying(), 1e-3); // RHS mutated
    }
}
