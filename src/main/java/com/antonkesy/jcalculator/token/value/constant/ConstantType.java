package com.antonkesy.jcalculator.token.value.constant;

import com.antonkesy.jcalculator.token.TypeRepresentation;

public enum ConstantType implements TypeRepresentation {
    E('e'), PI('π');

    private final char representation;

    ConstantType(char representation) {
        this.representation = representation;
    }

    @Override
    public char getTypeRepresentation() {
        return representation;
    }
}
