package net.opentsdb.query.processor.expressions2.nodes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import net.opentsdb.query.processor.expressions2.types.TypeLiteral;
import org.junit.Test;

public class TestBool {
    @Test
    public void testGetValue() {
        assertTrue(Bool.TRUE.getValue());
        assertFalse(Bool.FALSE.getValue());
    }

    @Test
    public void testGetType() {
        assertEquals(TypeLiteral.BOOLEAN, Bool.TRUE.getType());
        assertEquals(TypeLiteral.BOOLEAN, Bool.FALSE.getType());
    }

    @Test
    public void testEquals() {
        assertEquals(Bool.TRUE, Bool.TRUE);
        assertEquals(Bool.FALSE, Bool.FALSE);
        assertNotEquals(Bool.TRUE, Bool.FALSE);
        assertNotEquals(Bool.FALSE, Bool.TRUE);
    }
}
