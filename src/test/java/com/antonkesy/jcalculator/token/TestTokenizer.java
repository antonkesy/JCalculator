package com.antonkesy.jcalculator.token;

import com.antonkesy.jcalculator.token.exception.UnknownTokenException;
import com.antonkesy.jcalculator.token.operator.OperatorToken;
import com.antonkesy.jcalculator.token.operator.OperatorType;
import com.antonkesy.jcalculator.token.value.literal.LiteralToken;
import com.antonkesy.jcalculator.tokenizer.Tokenizer;
import org.junit.jupiter.api.Test;

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
        assertEquals("(a)", Tokenizer.getOptionRegex("a"));
        assertEquals("(a|b)", Tokenizer.getOptionRegex("a b"));
        assertEquals("(a|ab)", Tokenizer.getOptionRegex("a ab"));
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
}
