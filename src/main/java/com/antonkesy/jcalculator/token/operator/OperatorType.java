package com.antonkesy.jcalculator.token.operator;

public enum OperatorType {
    ADD('+'), SUB('-'), MULTIPLY('*'), DIVIDE('/');

    public final char representation;

    OperatorType(char representation) {
        this.representation = representation;
    }
}
