package com.antonkesy.jcalculator.tokenizer.token.term;

import com.antonkesy.jcalculator.tokenizer.token.Token;
import com.antonkesy.jcalculator.tokenizer.token.TypeRepresentation;

public enum ExponentType implements TypeRepresentation {
    EXPONENT("^");

    public final String representation;

    ExponentType(String representation) {
        this.representation = representation;
    }

    @Override
    public String getTypeRepresentation() {
        return representation;
    }

    @Override
    public Token createToken() {
        return new ExponentToken();
    }
}
