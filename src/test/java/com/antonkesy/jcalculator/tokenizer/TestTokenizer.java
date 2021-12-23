package com.antonkesy.jcalculator.tokenizer;

import com.antonkesy.jcalculator.tokenizer.token.Token;
import com.antonkesy.jcalculator.tokenizer.exception.UnknownTokenException;
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

    @Test
    void testGetTokenFromStringLiteral() {
        try {
            testTokenizeCase(new LiteralToken(42), "42");
            testTokenizeCase(new LiteralToken(-42), "-42");
            testTokenizeCase(new LiteralToken(123), "0123");
            testTokenizeCase(new LiteralToken(0), "0");

            assertEquals(new LiteralToken(123), new Tokenizer("0123").getToken().get(0));
            assertEquals(new LiteralToken(123), new Tokenizer("0123").getToken().get(0));
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
            assertEquals(expected, new Tokenizer(input).getToken());
        } catch (UnknownTokenException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void testTokenizeSingleTokenList() {
        testTokenizeCase(new LiteralToken(42), "42");
        testTokenizeCase(new ConstantToken(ConstantType.PI), ConstantType.PI.getTypeRepresentation());
        testTokenizeCase(new OperatorToken(OperatorType.ADD), "+");
        assertThrows(UnknownTokenException.class, () -> new Tokenizer("un"));
        assertThrows(UnknownTokenException.class, () -> new Tokenizer(""));
    }

    @Test
    void testTokenizeMultiple1() {
        ArrayList<Token> multiTokenList = new ArrayList<>();
        multiTokenList.add(new LiteralToken(42));
        multiTokenList.add(new OperatorToken(OperatorType.ADD));
        multiTokenList.add(new ConstantToken(ConstantType.PI));

        testTokenizeCase(multiTokenList, "42+pi");
        testTokenizeCase(multiTokenList, "42 +pi");
        testTokenizeCase(multiTokenList, "42 + pi");
        testTokenizeCase(multiTokenList, "42   +pi");
    }

    @Test
    void testTokenizeMultiple2() {
        ArrayList<Token> multiTokenList = new ArrayList<>();
        multiTokenList.add(new SeparatorToken(SeparatorType.OPEN));
        multiTokenList.add(new LiteralToken(42));
        multiTokenList.add(new OperatorToken(OperatorType.DIVIDE));
        multiTokenList.add(new LiteralToken(73));
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
        expectList.add(new LiteralToken(2));
        expectList.add(new OperatorToken(OperatorType.EXPONENT));
        expectList.add(new LiteralToken(2));
        testTokenizeCase(expectList, "2^2");
    }

    @Test
    void testSignedLiterals(){
        //42+(-3+5) = 44
        ArrayList<Token> expected = new ArrayList<>();
        expected.add(new LiteralToken(42));
        expected.add(new OperatorToken(OperatorType.ADD));
        expected.add(new SeparatorToken(SeparatorType.OPEN));
        expected.add(new LiteralToken(-3));
        expected.add(new OperatorToken(OperatorType.ADD));
        expected.add(new LiteralToken(5));
        expected.add(new SeparatorToken(SeparatorType.CLOSE));
        testTokenizeCase(expected,"42+(-3+5)");
    }
}
