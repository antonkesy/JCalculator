package com.antonkesy.jcalculator.token.operator;

import com.antonkesy.jcalculator.token.Token;

public class OperatorToken implements Token {
    public final OperatorType operator;

    public OperatorToken(OperatorType operator) {
        this.operator = operator;
    }
}