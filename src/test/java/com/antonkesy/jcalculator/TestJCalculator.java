package com.antonkesy.jcalculator;

import com.antonkesy.jcalculator.parser.exception.MissingTokenException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class TestJCalculator {

    boolean testString(String input, BigDecimal expected) {
        try {
            BigDecimal result = JCalculator.calculate(input);
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
        assertThrows(Exception.class, () -> JCalculator.calculate(""));
    }

    @Test
    void testSingleFactor() {
        assertTrue(testString("1", BigDecimal.ONE));
        assertTrue(testString("12", new BigDecimal(12)));
        assertTrue(testString("42", new BigDecimal(42)));
        assertTrue(testString("e", new BigDecimal(Math.E)));
        assertTrue(testString("pi", new BigDecimal(Math.PI)));
    }

    @Test
    void testAddition() {
        assertTrue(testString("1+1", new BigDecimal(2)));
        assertTrue(testString("10+1", new BigDecimal(11)));
        assertTrue(testString("1+2+3+4+5+6", new BigDecimal(21)));
        assertTrue(testString("9950+50", new BigDecimal(10000)));
    }

    @Test
    void testSubtraction() {
        assertTrue(testString("1-1", new BigDecimal(0)));
        assertTrue(testString("10-1", new BigDecimal(9)));
        assertTrue(testString("9950-50", new BigDecimal(9900)));
    }

    @Test
    void testMultiplication() {
        assertTrue(testString("1*1", new BigDecimal(1)));
        assertTrue(testString("10*1", new BigDecimal(10)));
        assertTrue(testString("1*2*3*4*5*6", new BigDecimal(720)));
        assertTrue(testString("9950*50", new BigDecimal(497500)));
    }

    @Test
    void testDivision() {
        assertTrue(testString("1/1", new BigDecimal(1)));
        assertTrue(testString("10/1", new BigDecimal(10)));
        assertTrue(testString("9950/50", new BigDecimal(199)));
    }

    @Test
    void testPEMDAS() {
        assertTrue(testString("1+1*1", new BigDecimal(2)));
        assertTrue(testString("1*1+1", new BigDecimal(2)));
        assertTrue(testString("3+7*10", new BigDecimal(73)));
        assertTrue(testString("3*7+10", new BigDecimal(31)));
        assertTrue(testString("3*(7+10)", new BigDecimal(51)));
        assertTrue(testString("3^2*(1+10)", new BigDecimal(99)));
        assertTrue(testString("2^6*(8+4)^2", new BigDecimal(9216)));
    }

    @Test
    void testParentheses() {
        assertTrue(testString("3*(3+1)", new BigDecimal(12)));
        assertTrue(testString("3*((3+1)*5)", new BigDecimal(60)));
        assertTrue(testString("3*(3)", new BigDecimal(9)));
        assertTrue(testString("3()", new BigDecimal(3)));
    }

    @Test
    void testExponent() {
        assertTrue(testString("2^2", new BigDecimal(4)));
        assertTrue(testString("2^2^2", new BigDecimal(16)));
        assertTrue(testString("22^2", new BigDecimal(484)));
        assertTrue(testString("2^2*2", new BigDecimal(8)));
        assertTrue(testString("4*3^2", new BigDecimal(36)));
        assertTrue(testString("(4*3)^2", new BigDecimal(144)));
    }

    @Test
    void testRandom() {
        assertTrue(testString("5^3*4.5/12+1", new BigDecimal("47.875")));
        //does not support -(...)
        assertTrue(testString("-1*(5+3)*3/5", new BigDecimal("-4.8")));
        //TODO
        assertTrue(testString("(2*12+17)-(-122*513)+3*(14*2-3*-4+4*3+13)", new BigDecimal("62822")));
    }

    @Test
    void testSignedFactor() {
        assertTrue(testString("-1", new BigDecimal(-1)));
    }
}
