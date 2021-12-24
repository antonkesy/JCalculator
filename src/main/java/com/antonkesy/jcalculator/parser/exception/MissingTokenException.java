package com.antonkesy.jcalculator.parser.exception;

import com.antonkesy.jcalculator.tokenizer.token.Token;

public class MissingTokenException extends Exception {
    public MissingTokenException(Token tokenExpected) {
        super(tokenExpected.getRepresentation() + " not found");
    }

    public MissingTokenException(String tokenName) {
        super(tokenName + " not found");
    }

}
