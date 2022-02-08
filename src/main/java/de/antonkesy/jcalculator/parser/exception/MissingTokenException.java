package de.antonkesy.jcalculator.parser.exception;

import de.antonkesy.jcalculator.tokenizer.token.Token;

public class MissingTokenException extends Exception {
    public MissingTokenException(Token tokenExpected) {
        super(tokenExpected.getRepresentation() + " not found");
    }

    public MissingTokenException(String tokenName) {
        super(tokenName + " not found");
    }

}
