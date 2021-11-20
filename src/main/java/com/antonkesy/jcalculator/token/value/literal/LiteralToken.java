package com.antonkesy.jcalculator.token.value.literal;

import com.antonkesy.jcalculator.token.value.ValueToken;

public class LiteralToken implements ValueToken {
    private int value;

    @Override
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
