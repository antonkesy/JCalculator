package com.antonkesy.jcalculator.token;

import com.antonkesy.jcalculator.tokenizer.Tokenizer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
