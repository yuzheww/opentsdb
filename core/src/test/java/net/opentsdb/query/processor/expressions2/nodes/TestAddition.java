package net.opentsdb.query.processor.expressions2.nodes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import net.opentsdb.query.processor.expressions2.ExpressionException;
import net.opentsdb.query.processor.expressions2.types.TypeLiteral;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestAddition {
    @Rule
    public ExpectedException exnRule = ExpectedException.none();

    @Test
    public void testArity() {
        final Addition a = new Addition(new Number(7), new Number(11));
        assertEquals(2, a.getArity());
    }

    @Test
    public void testGetType() {
        final Addition a = new Addition(new Number(3.14159), new Number(2.71828));
        assertEquals(TypeLiteral.NUMERIC, a.getType());
    }

    @Test
    public void testTypeErrorBooleanRHS() {
        exnRule.expect(ExpressionException.class);
        exnRule.expectMessage("could not match given domain type to any valid signature in ExpressionOperator");
        new Addition(new Number(3.14159), Bool.TRUE);
    }

    @Test
    public void testTypeErrorBooleanLHS() {
        exnRule.expect(ExpressionException.class);
        exnRule.expectMessage("could not match given domain type to any valid signature in ExpressionOperator");
        new Addition(Bool.FALSE, new Number(2.71828));
    }

    @Test
    public void testEquals() {
        final Addition a = new Addition(new Number(3.14159), new Number(2.71828));
        assertEquals(a, a);

        final Addition b = new Addition(new Number(7), new Number(11));
        assertNotEquals(a, b);
        assertNotEquals(b, a);
    }
}
