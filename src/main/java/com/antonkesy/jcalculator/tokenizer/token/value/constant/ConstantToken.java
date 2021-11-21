package com.antonkesy.jcalculator.tokenizer.token.value.constant;

import com.antonkesy.jcalculator.tokenizer.token.value.ValueToken;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConstantToken that = (ConstantToken) o;
        return value == that.value;
    }

}
