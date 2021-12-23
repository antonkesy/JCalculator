package com.antonkesy.jcalculator.tokenizer.token.value.constant;

import com.antonkesy.jcalculator.tokenizer.token.value.ValueToken;

import java.math.BigDecimal;

public class ConstantToken implements ValueToken {
    private final ConstantType type;

    public ConstantToken(ConstantType type) {
        this.type = type;
    }

    @Override
    public BigDecimal getValue() {
        return type.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConstantToken that = (ConstantToken) o;
        return type.equals(that.type);
    }

    @Override
    public String getRepresentation() {
        return type.getTypeRepresentation();
    }
}
