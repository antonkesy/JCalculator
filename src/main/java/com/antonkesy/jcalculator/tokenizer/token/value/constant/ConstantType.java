package com.antonkesy.jcalculator.tokenizer.token.value.constant;

import com.antonkesy.jcalculator.tokenizer.token.Token;
import com.antonkesy.jcalculator.tokenizer.token.TypeRepresentation;

import java.math.BigDecimal;

public enum ConstantType implements TypeRepresentation {
    //TODO use double for values!
    E("e", new BigDecimal(Math.E)), PI("pi", new BigDecimal(Math.PI));

    private final String representation;
    public final BigDecimal value;

    ConstantType(String representation, BigDecimal value) {
        this.representation = representation;
        this.value = value;
    }

    @Override
    public String getTypeRepresentation() {
        return representation;
    }

    @Override
    public Token createToken() {
        return new ConstantToken(this);
    }
}
