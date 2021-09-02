package net.opentsdb.query.processor.expressions2.nodes;

import static org.junit.Assert.assertEquals;

import net.opentsdb.query.processor.expressions2.ExpressionException;
import net.opentsdb.query.processor.expressions2.types.TypeLiteral;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestLogicalNegation {
    @Rule
    public ExpectedException exnRule = ExpectedException.none();

    @Test
    public void testArity() {
        final LogicalNegation neg = new LogicalNegation(Bool.TRUE);
        assertEquals(1, neg.getArity());
    }

    @Test
    public void testGetSymbol() {
        final LogicalNegation neg = new LogicalNegation(Bool.TRUE);
        assertEquals("!", neg.getSymbol());
    }

    @Test
    public void testGetType() {
        final LogicalNegation neg = new LogicalNegation(Bool.FALSE);
        assertEquals(TypeLiteral.BOOLEAN, neg.getType());
    }

    @Test
    public void testTypeError() {
        exnRule.expect(ExpressionException.class);
        exnRule.expectMessage("could not match given domain type to any valid signature in ExpressionOperator");
        new LogicalNegation(new Number(3.14159265));
    }
}
