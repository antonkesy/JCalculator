package com.antonkesy.jcalculator.token.value.constant;

public enum ConstantType {
    E('e'), PI('π');

    private final char representation;

    ConstantType(char representation) {
        this.representation = representation;
    }
}
