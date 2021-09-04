package net.opentsdb.query.processor.expressions2.nodes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import net.opentsdb.query.processor.expressions2.types.TypeLiteral;
import org.junit.Test;

public class TestDouble {
    @Test
    public void testGetValue() {
        final Double d = new Double(3.14159265);
        assertEquals(3.14159265, d.getValue(), 1e-8);
    }

    @Test
    public void testGetType() {
        final Double d = new Double(2.618);
        assertEquals(TypeLiteral.NUMERIC, d.getType());
    }

    @Test
    public void testEquals() {
        final Double pi = new Double(3.14159);
        assertEquals(pi, pi);

        final Double e = new Double(2.71828);
        assertNotEquals(pi, e);
        assertNotEquals(e, pi);
    }
}
