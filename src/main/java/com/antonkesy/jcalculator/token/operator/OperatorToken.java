package com.antonkesy.jcalculator.token.operator;

import com.antonkesy.jcalculator.token.Token;

public class OperatorToken implements Token {
    public final OperatorType operator;

    public OperatorToken(OperatorType operator) {
        this.operator = operator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperatorToken that = (OperatorToken) o;
        return operator == that.operator;
    }
}