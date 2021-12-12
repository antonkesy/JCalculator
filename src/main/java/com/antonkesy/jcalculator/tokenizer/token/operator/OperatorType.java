package com.antonkesy.jcalculator.tokenizer.token.operator;

import com.antonkesy.jcalculator.tokenizer.token.Token;
import com.antonkesy.jcalculator.tokenizer.token.TypeRepresentation;

public enum OperatorType implements TypeRepresentation {
    ADD("+"), SUB("-"), MULTIPLY("*"), DIVIDE("/"), EXPONENT("^");

    public final String representation;

    OperatorType(String representation) {
        this.representation = representation;
    }

    @Override
    public String getTypeRepresentation() {
        return representation;
    }

    @Override
    public Token createToken() {
        return new OperatorToken(this);
    }

}
