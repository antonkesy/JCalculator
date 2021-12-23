package com.antonkesy.jcalculator.tokenizer.token.value.literal;

import com.antonkesy.jcalculator.tokenizer.token.value.ValueToken;

import java.math.BigDecimal;

public class LiteralToken implements ValueToken {
    private final BigDecimal value;

    public LiteralToken(BigDecimal value) {
        this.value = value;
    }

    @Override
    public BigDecimal getValue() {
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
