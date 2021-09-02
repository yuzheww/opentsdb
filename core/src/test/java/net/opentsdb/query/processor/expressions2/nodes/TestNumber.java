package net.opentsdb.query.processor.expressions2.nodes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import net.opentsdb.query.processor.expressions2.types.TypeLiteral;
import org.junit.Test;

public class TestNumber {
    @Test
    public void testGetValue() {
        final Number n = new Number(3.14159265);
        assertEquals(3.14159265, n.getValue(), 1e-8);
    }

    @Test
    public void testGetType() {
        final Number n = new Number(2.618);
        assertEquals(TypeLiteral.NUMERIC, n.getType());
    }

    @Test
    public void testEquals() {
        final Number pi = new Number(3.14159);
        assertEquals(pi, pi);

        final Number e = new Number(2.71828);
        assertNotEquals(pi, e);
        assertNotEquals(e, pi);
    }
}
