package com.antonkesy.jcalculator.tokenizer;

import com.antonkesy.jcalculator.number.bigdecimal.BigDecimalFactory;
import com.antonkesy.jcalculator.number.bigdecimal.BigDecimalNumber;
import com.antonkesy.jcalculator.tokenizer.exception.UnknownTokenException;
import com.antonkesy.jcalculator.tokenizer.token.Token;
import com.antonkesy.jcalculator.tokenizer.token.operator.OperatorToken;
import com.antonkesy.jcalculator.tokenizer.token.operator.OperatorType;
import com.antonkesy.jcalculator.tokenizer.token.separator.SeparatorToken;
import com.antonkesy.jcalculator.tokenizer.token.separator.SeparatorType;
import com.antonkesy.jcalculator.tokenizer.token.value.constant.ConstantToken;
import com.antonkesy.jcalculator.tokenizer.token.value.constant.ConstantType;
import com.antonkesy.jcalculator.tokenizer.token.value.literal.LiteralToken;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestTokenizer {

    private final BigDecimalFactory numberFactory = new BigDecimalFactory();
    private final ConstantToken PI = new ConstantToken(new ConstantType("pi", new BigDecimalNumber(Math.PI)));

    @Test
    void testGetTokenFromStringLiteral() {
        try {
            testTokenizeCase(new LiteralToken(new BigDecimalNumber(42)), "42");
            testTokenizeCase(new LiteralToken(new BigDecimalNumber(-42)), "-42");
            testTokenizeCase(new LiteralToken(new BigDecimalNumber(123)), "0123");
            testTokenizeCase(new LiteralToken(new BigDecimalNumber(0)), "0");

            assertEquals(new LiteralToken(new BigDecimalNumber(123)), new Tokenizer("0123", numberFactory).getToken().get(0));
            assertEquals(new LiteralToken(new BigDecimalNumber(123)), new Tokenizer("0123", numberFactory).getToken().get(0));
        } catch (UnknownTokenException e) {
            fail();
        }
    }

    void testTokenizeCase(Token token, String input) {
        ArrayList<Token> tokenList = new ArrayList<>();
        tokenList.add(token);
        testTokenizeCase(tokenList, input);
    }

    void testTokenizeCase(ArrayList<Token> expected, String input) {
        try {
            assertEquals(expected, new Tokenizer(input, numberFactory).getToken());
        } catch (UnknownTokenException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void testTokenizeSingleTokenList() {
        testTokenizeCase(new LiteralToken(new BigDecimalNumber(42)), "42");
        testTokenizeCase(new OperatorToken(OperatorType.ADD), "+");
        assertThrows(UnknownTokenException.class, () -> new Tokenizer("un", numberFactory));
        assertThrows(UnknownTokenException.class, () -> new Tokenizer("", numberFactory));
    }

    @Test
    void testTokenizeMultiple1() {
        ArrayList<Token> multiTokenList = new ArrayList<>();
        multiTokenList.add(new LiteralToken(new BigDecimalNumber(42)));
        multiTokenList.add(new OperatorToken(OperatorType.ADD));
        multiTokenList.add(PI);

        testTokenizeCase(multiTokenList, "42+pi");
        testTokenizeCase(multiTokenList, "42 +pi");
        testTokenizeCase(multiTokenList, "42 + pi");
        testTokenizeCase(multiTokenList, "42   +pi");
    }

    @Test
    void testTokenizeMultiple2() {
        ArrayList<Token> multiTokenList = new ArrayList<>();
        multiTokenList.add(new SeparatorToken(SeparatorType.OPEN));
        multiTokenList.add(new LiteralToken(new BigDecimalNumber(42)));
        multiTokenList.add(new OperatorToken(OperatorType.DIVIDE));
        multiTokenList.add(new LiteralToken(new BigDecimalNumber(73)));
        multiTokenList.add(new SeparatorToken(SeparatorType.CLOSE));

        testTokenizeCase(multiTokenList, "(42/73)");
        testTokenizeCase(multiTokenList, "(42 /73)");
        testTokenizeCase(multiTokenList, "(42 / 73)");
        testTokenizeCase(multiTokenList, "( 42   /73)");
    }

    @Test
    void testExponent() {
        testTokenizeCase(new OperatorToken(OperatorType.EXPONENT), "^");
        ArrayList<Token> expectList = new ArrayList<>();
        expectList.add(new LiteralToken(new BigDecimalNumber(2)));
        expectList.add(new OperatorToken(OperatorType.EXPONENT));
        expectList.add(new LiteralToken(new BigDecimalNumber(2)));
        testTokenizeCase(expectList, "2^2");
    }

    @Test
    void testSignedLiterals() {
        //42+(-3+5) = 44
        ArrayList<Token> expected = new ArrayList<>();
        expected.add(new LiteralToken(new BigDecimalNumber(42)));
        expected.add(new OperatorToken(OperatorType.ADD));
        expected.add(new SeparatorToken(SeparatorType.OPEN));
        expected.add(new LiteralToken(new BigDecimalNumber(-3)));
        expected.add(new OperatorToken(OperatorType.ADD));
        expected.add(new LiteralToken(new BigDecimalNumber(5)));
        expected.add(new SeparatorToken(SeparatorType.CLOSE));
        testTokenizeCase(expected, "42+(-3+5)");
    }

    @Test
    void testDecimalLiterals() {
        testTokenizeCase(new LiteralToken(new BigDecimalNumber("1.1")), "1.1");
        testTokenizeCase(new LiteralToken(new BigDecimalNumber("-521.1")), "-521.1");
        testTokenizeCase(new LiteralToken(new BigDecimalNumber("-42.1234567891011121314151617181920212223")), "-42.1234567891011121314151617181920212223");
    }

    @Test
    void testAddMultiplyLiteralParentheses() {
        ArrayList<Token> expected = new ArrayList<>();
        expected.add(new LiteralToken(new BigDecimalNumber(3)));
        expected.add(new OperatorToken(OperatorType.MULTIPLY));
        expected.add(new SeparatorToken(SeparatorType.OPEN));
        expected.add(new LiteralToken(new BigDecimalNumber(-5)));
        expected.add(new OperatorToken(OperatorType.ADD));
        expected.add(new LiteralToken(new BigDecimalNumber(4)));
        expected.add(new SeparatorToken(SeparatorType.CLOSE));
        testTokenizeCase(expected, "3(-5+4)");
    }

    @Test
    void testAddMultiplyLiteralConstant() {
        ArrayList<Token> expected = new ArrayList<>();
        expected.add(new LiteralToken(new BigDecimalNumber(3)));
        expected.add(new OperatorToken(OperatorType.MULTIPLY));
        expected.add(PI);
        testTokenizeCase(expected, "3pi");
    }
}
