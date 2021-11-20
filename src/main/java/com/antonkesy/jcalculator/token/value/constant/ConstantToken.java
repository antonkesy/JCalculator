package com.antonkesy.jcalculator.token.value.constant;

import com.antonkesy.jcalculator.token.value.ValueToken;

public class ConstantToken implements ValueToken {
    private final int value;

    public ConstantToken(ConstantType type) {
        value = type.value;
    }

    public ConstantToken(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
