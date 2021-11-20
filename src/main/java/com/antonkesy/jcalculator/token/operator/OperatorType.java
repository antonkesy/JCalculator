package com.antonkesy.jcalculator.token.operator;

import com.antonkesy.jcalculator.token.TypeRepresentation;

public enum OperatorType implements TypeRepresentation {
    ADD('+'), SUB('-'), MULTIPLY('*'), DIVIDE('/');

    public final char representation;

    OperatorType(char representation) {
        this.representation = representation;
    }

    @Override
    public char getTypeRepresentation() {
        return representation;
    }
}
