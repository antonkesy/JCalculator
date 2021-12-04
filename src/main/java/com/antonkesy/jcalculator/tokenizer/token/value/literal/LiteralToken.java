package com.antonkesy.jcalculator.tokenizer.token.value.literal;

import com.antonkesy.jcalculator.tokenizer.token.value.ValueToken;

public class LiteralToken implements ValueToken {
    private final int value;

    public LiteralToken(int value) {
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
        LiteralToken that = (LiteralToken) o;
        return value == that.value;
    }

    @Override
    public String getRepresentation() {
        return Integer.toString(value);
    }
}
