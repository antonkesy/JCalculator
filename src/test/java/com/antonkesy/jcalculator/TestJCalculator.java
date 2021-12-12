package com.antonkesy.jcalculator;

import com.antonkesy.jcalculator.parser.exception.MissingTokenException;
import com.antonkesy.jcalculator.tokenizer.exception.UnknownTokenException;
import com.antonkesy.jcalculator.tokenizer.token.Token;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestJCalculator {

    boolean testString(String input, int expected) {
        try {
            int result = JCalculator.calculate(input);
            if (result == expected) {
                return true;
            } else {
                fail("expected = " + expected + " actual = " + result);
            }
        } catch (IOException | MissingTokenException ignore) {
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
        assertTrue(testString("1", 1));
        assertTrue(testString("12", 12));
        assertTrue(testString("42", 42));
        assertTrue(testString("e", (int) Math.E));
        assertTrue(testString("pi", (int) Math.PI));
    }

    @Test
    void testAddition() {
        assertTrue(testString("1+1", 2));
        assertTrue(testString("10+1", 11));
        assertTrue(testString("1+2+3+4+5+6", 21));
        assertTrue(testString("9950+50", 10000));
    }

    @Test
    void testSubtraction() {
        assertTrue(testString("1-1", 0));
        assertTrue(testString("10-1", 9));
        assertTrue(testString("9950-50", 9900));
    }

    @Test
    void testMultiplication() {
        assertTrue(testString("1*1", 1));
        assertTrue(testString("10*1", 10));
        assertTrue(testString("1*2*3*4*5*6", 720));
        assertTrue(testString("9950*50", 497500));
    }

    @Test
    void testDivision() {
        assertTrue(testString("1/1", 1));
        assertTrue(testString("10/1", 10));
        assertTrue(testString("9950/50", 199));
    }

    @Test
    void testPEMDAS() {
        assertTrue(testString("1+1*1", 2));
        assertTrue(testString("1*1+1", 2));
        assertTrue(testString("3+7*10", 73));
        assertTrue(testString("3*7+10", 31));
        assertTrue(testString("3*(7+10)", 51));
        assertTrue(testString("3^2*(1+10)", 99));
        assertTrue(testString("2^6*(8+4)^2", 9216));
    }

    @Test
    void testP() {
        assertTrue(testString("3*(3+1)", 12));
        assertTrue(testString("3*((3+1)*5)", 60));
    }

    @Test
    void testExponent() {
        assertTrue(testString("2^2", 4));
        assertTrue(testString("2^2^2", 16));
        assertTrue(testString("22^2", 484));
        assertTrue(testString("2^2*2", 8));
        assertTrue(testString("4*3^2", 36));
        assertTrue(testString("(4*3)^2", 144));
    }

}
