package net.opentsdb.query.processor.expressions2.nodes;

import static org.junit.Assert.assertEquals;

import net.opentsdb.query.processor.expressions2.ExpressionException;
import net.opentsdb.query.processor.expressions2.types.TypeLiteral;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestNumericNegation {
    @Rule
    public ExpectedException exnRule = ExpectedException.none();

    @Test
    public void testArity() {
        final NumericNegation neg = new NumericNegation(new Double(2.71828));
        assertEquals(1, neg.getArity());
    }

    @Test
    public void testGetSymbol() {
        final NumericNegation neg = new NumericNegation(new Double(3.14159265));
        assertEquals("-", neg.getSymbol());
    }

    @Test
    public void testGetType() {
        final NumericNegation neg = new NumericNegation(new Long(16L));
        assertEquals(TypeLiteral.NUMERIC, neg.getType());
    }

    @Test
    public void testTypeErrorBool() {
        exnRule.expect(ExpressionException.class);
        exnRule.expectMessage("could not match given domain type to any valid signature in ExpressionOperator");
        new NumericNegation(Bool.TRUE);
    }
}
