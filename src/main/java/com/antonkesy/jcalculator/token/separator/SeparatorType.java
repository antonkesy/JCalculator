package com.antonkesy.jcalculator.token.separator;

import com.antonkesy.jcalculator.token.TypeRepresentation;

public enum SeparatorType implements TypeRepresentation {
    LEFT('('), RIGHT(')');

    public final char representation;

    SeparatorType(char representation) {
        this.representation = representation;
    }

    @Override
    public char getTypeRepresentation() {
        return representation;
    }
}
