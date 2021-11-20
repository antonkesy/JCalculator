package com.antonkesy.jcalculator.token.operator;

import com.antonkesy.jcalculator.token.Token;
import com.antonkesy.jcalculator.token.TypeRepresentation;

public enum OperatorType implements TypeRepresentation {
    ADD("+"), SUB("-"), MULTIPLY("*"), DIVIDE("/");

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
