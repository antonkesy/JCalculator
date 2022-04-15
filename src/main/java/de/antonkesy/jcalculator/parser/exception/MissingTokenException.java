package de.antonkesy.jcalculator.parser.exception;

import de.antonkesy.jcalculator.tokenizer.token.IToken;

public class MissingTokenException extends Exception {
    public MissingTokenException(IToken tokenExpected) {
        super(tokenExpected.getTypeRepresentation() + " not found");
    }

    public MissingTokenException(String tokenName) {
        super(tokenName + " not found");
    }

}
