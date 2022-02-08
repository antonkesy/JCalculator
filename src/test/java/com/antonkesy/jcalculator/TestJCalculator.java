package com.antonkesy.jcalculator;

import com.antonkesy.jcalculator.number.bigdecimal.BigDecimalFactory;
import com.antonkesy.jcalculator.parser.exception.MissingTokenException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class TestJCalculator {
    private final JCalculator TESTCAL = new JCalculator(new BigDecimalFactory());

    boolean testString(String input, String expected) {
        try {
            String result = TESTCAL.calculate(input);
            if (result.equals(expected)) {
                return true;
            } else {
                fail("expected = " + expected + " actual = " + result);
            }
        } catch (IOException | MissingTokenException e) {
            e.printStackTrace();
            fail();
        }
        return false;
    }

    @Test
    void testEmptyString() {
        assertThrows(Exception.class, () -> TESTCAL.calculate(""));
    }

    @Test
    void testSingleFactor() {
        assertTrue(testString("1", "1"));
        assertTrue(testString("12", "12"));
        assertTrue(testString("42", "42"));
        assertTrue(testString("e", new BigDecimal(Math.E).toPlainString()));
        assertTrue(testString("pi", new BigDecimal(Math.PI).toPlainString()));
    }

    @Test
    void testAddition() {
        assertTrue(testString("1+1", "2"));
        assertTrue(testString("10+1", "11"));
        assertTrue(testString("1+2+3+4+5+6", "21"));
        assertTrue(testString("9950+50", "10000"));
    }

    @Test
    void testSubtraction() {
        assertTrue(testString("1-1", "0"));
        assertTrue(testString("10-1", "9"));
        assertTrue(testString("9950-50", "9900"));
    }

    @Test
    void testMultiplication() {
        assertTrue(testString("1*1", "1"));
        assertTrue(testString("10*1", "10"));
        assertTrue(testString("1*2*3*4*5*6", "720"));
        assertTrue(testString("9950*50", "497500"));
    }

    @Test
    void testDivision() {
        assertTrue(testString("1/1", "1"));
        assertTrue(testString("10/1", "10"));
        assertTrue(testString("9950/50", "199"));
        assertTrue(testString("0/1", "0"));
        assertThrows(ArithmeticException.class, () -> testString("42/0", ""));
        //does currently not support irrational division results
        assertThrows(ArithmeticException.class, () -> testString("10/3", "3.3"));
    }

    @Test
    void testPEMDAS() {
        assertTrue(testString("1+1*1", "2"));
        assertTrue(testString("1*1+1", "2"));
        assertTrue(testString("3+7*10", "73"));
        assertTrue(testString("3*7+10", "31"));
        assertTrue(testString("3*(7+10)", "51"));
        assertTrue(testString("3^2*(1+10)", "99"));
        assertTrue(testString("2^6*(8+4)^2", "9216"));
    }

    @Test
    void testParentheses() {
        assertTrue(testString("3*(3+1)", "12"));
        assertTrue(testString("3*((3+1)*5)", "60"));
        assertTrue(testString("3*(3)", "9"));
        assertTrue(testString("(3)+3", "6"));
        assertThrows(MissingTokenException.class, () -> TESTCAL.calculate("(3+3"));
        assertThrows(MissingTokenException.class, () -> TESTCAL.calculate("3+)3"));
        //TODO catch too many closing parentheses
        //assertThrows(MissingTokenException.class, () -> JCalculator.calculate("3+3)+3"));
        assertThrows(MissingTokenException.class, () -> TESTCAL.calculate("3+)3"));
        assertThrows(MissingTokenException.class, () -> TESTCAL.calculate("3()"));
    }

    @Test
    void testExponent() {
        assertTrue(testString("2^2", "4"));
        assertTrue(testString("2^2^2", "16"));
        assertTrue(testString("22^2", "484"));
        assertTrue(testString("2^2*2", "8"));
        assertTrue(testString("4*3^2", "36"));
        assertTrue(testString("(4*3)^2", "144"));
    }

    @Test
    void testRandom() {
        assertTrue(testString("5^3*4.5/12+1", "47.875"));
        //does not support -(...)
        assertTrue(testString("-1*(5+3)*3/5", "-4.8"));
        assertTrue(testString("(2*12+17)", "41"));
        assertTrue(testString("(-122*513)", "-62586"));
        assertTrue(testString("(14*2-3*-4+4*3+13)", "65"));
        assertTrue(testString("(41)-(-62586)+3*(65)", "62822"));
        assertTrue(testString("(2*12+17)-(-122*513)+3*(14*2-3*-4+4*3+13)", "62822"));
    }

    @Test
    void testSignedFactor() {
        assertTrue(testString("-1", "-1"));
    }
}
