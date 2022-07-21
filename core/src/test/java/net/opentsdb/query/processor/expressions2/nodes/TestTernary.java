package net.opentsdb.query.processor.expressions2.nodes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import net.opentsdb.query.processor.expressions2.OperatorTypeError;
import net.opentsdb.query.processor.expressions2.types.TypeLiteral;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class TestTernary {
    @Rule
    public ExpectedException exnRule = ExpectedException.none();
    final TernaryOperator a = new TernaryOperator(new Equal(new Long(1), new Long(2)), new Long(16), new Long(8));

    @Test
    public void testChildren() {
        final Equal condition = new Equal(new Long(1), new Long(2));
        assertEquals(new Long(16), a.getTrueCase());
        assertEquals(new Long(8), a.getFalseCase());
        assertEquals(condition, a.getCondition());
    }

    @Test
    public void testParents() {
        assertNull(a.getParent());
        assertTrue(a.getTrueCase().getParent() == a);
        assertTrue(a.getFalseCase().getParent() == a);
        assertTrue(a.getCondition().getParent() == a);
    }

    @Test
    public void testArity() {
        assertEquals(3, a.getArity());
    }

    @Test
    public void testGetType() {
        assertEquals(TypeLiteral.NUMERIC, a.getType());
    }

    @Test
    public void testTypeErrorBooleanTrueCase() {
        exnRule.expect(OperatorTypeError.class);
        exnRule.expectMessage("for operator ' ? : ': type mismatch\n" +
                "given operand(s) of type: BOOLEAN * BOOLEAN * NUMERIC\n" +
                "expected any of the following: BOOLEAN * NUMERIC * NUMERIC");
        new TernaryOperator(new Equal(new Long(1), new Long(2)), Bool.TRUE, new Long(8));
    }

    @Test
    public void testTypeErrorBooleanFalseCase() {
        exnRule.expect(OperatorTypeError.class);
        exnRule.expectMessage("for operator 'cond ? expr1 : expr2': type mismatch\n" +
                "given operand(s) of type: BOOLEAN * NUMERIC * BOOLEAN\n" +
                "expected any of the following: BOOLEAN * NUMERIC * NUMERIC");
        new TernaryOperator(new Equal(new Long(1), new Long(2)), new Long(8), Bool.TRUE);
    }

    @Test
    public void testTypeErrorNumericCondition() {
        exnRule.expect(OperatorTypeError.class);
        exnRule.expectMessage("for operator 'cond ? expr1 : expr2': type mismatch\n" +
                "given operand(s) of type: NUMERIC * NUMERIC * NUMERIC\n" +
                "expected any of the following: BOOLEAN * NUMERIC * NUMERIC");
        new TernaryOperator(new Long(1), new Long(8), new Long(1));
    }

    @Test
    public void testEquals() {
//        final Addition a = new Addition(new Double(3.14159), new Double(2.71828));
//        assertEquals(a, a);
//
//        final Addition b = new Addition(new Long(7), new Long(11));
//        assertEquals(b, b);
//        assertNotEquals(a, b);
//        assertNotEquals(b, a);
    }
}
