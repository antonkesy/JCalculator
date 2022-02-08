package com.antonkesy.jcalculator.tokenizer.token.value.literal;

import com.antonkesy.jcalculator.number.INumber;
import com.antonkesy.jcalculator.tokenizer.token.value.ValueToken;

public class LiteralToken implements ValueToken {
    private final INumber value;

    public LiteralToken(INumber value) {
        this.value = value;
    }

    @Override
    public INumber getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiteralToken that = (LiteralToken) o;
        return value.equals(that.value);
    }

    @Override
    public String getRepresentation() {
        return value.toPlainString();
    }
}
