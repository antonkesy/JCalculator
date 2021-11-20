package com.antonkesy.jcalculator.token.separator;

public enum SeparatorType {
    LEFT('('), RIGHT(')');

    public final char representation;

    SeparatorType(char representation) {
        this.representation = representation;
    }
}
