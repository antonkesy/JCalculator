package com.antonkesy.jcalculator.tokenizer;

import com.antonkesy.jcalculator.tokenizer.token.Token;
import com.antonkesy.jcalculator.tokenizer.token.TypeRepresentation;
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

    void testGetTokenOfTypeCase(TypeRepresentation expected) {
        assertEquals(expected.createToken(), Tokenizer.getTokenOfType(expected.getTypeRepresentation()));
    }

    @Test
    void testGetTokenOfType() {
        for (TypeRepresentation[] types : Tokenizer.getAllTypes()) {
            for (TypeRepresentation type : types) {
                testGetTokenOfTypeCase(type);
            }
        }
    }

    @Test
    void testGetOptionsRegex() {
        // \Q*\E -> * not read as regex
        assertEquals("(\\Qa\\E)", Tokenizer.getOptionRegex("a"));
        assertEquals("(\\Qa\\E|\\Qb\\E)", Tokenizer.getOptionRegex("a b"));
        assertEquals("(\\Qa\\E|\\Qab\\E)", Tokenizer.getOptionRegex("a ab"));
    }

    void testGetTokenFromStringCase(TypeRepresentation type) {
        try {
            assertEquals(type.createToken(), Tokenizer.getTokenFromString(type.getTypeRepresentation()));
        } catch (UnknownTokenException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void testGetTokenFromStringTypes() {
        for (TypeRepresentation[] types : Tokenizer.getAllTypes()) {
            for (TypeRepresentation type : types) {
                testGetTokenFromStringCase(type);
            }
        }
    }

    @Test
    void testGetTokenFromStringLiteral() {
        try {
            assertEquals(new LiteralToken(42), Tokenizer.getTokenFromString("42"));
            assertEquals(new LiteralToken(-42), Tokenizer.getTokenFromString("-42"));
            assertEquals(new LiteralToken(123), Tokenizer.getTokenFromString("0123"));

            assertNull(Tokenizer.getLiteralToken("+"));
            assertNull(Tokenizer.getLiteralToken("a"));
        } catch (UnknownTokenException e) {
            e.printStackTrace();
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
            assertEquals(expected, Tokenizer.tokenize(input));
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
        assertThrows(UnknownTokenException.class, () -> Tokenizer.tokenize("un"));
        assertThrows(UnknownTokenException.class, () -> Tokenizer.tokenize(""));
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
}
