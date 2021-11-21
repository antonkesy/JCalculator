package com.antonkesy.jcalculator.tokenizer.token.value.literal;

import com.antonkesy.jcalculator.tokenizer.token.value.ValueToken;

public class LiteralToken implements ValueToken {
    private int value;

    public LiteralToken() {
    }

    public LiteralToken(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiteralToken that = (LiteralToken) o;
        return value == that.value;
    }
}
