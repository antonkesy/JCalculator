package com.antonkesy.jcalculator.tokenizer.token.value.constant;

import com.antonkesy.jcalculator.tokenizer.token.Token;
import com.antonkesy.jcalculator.tokenizer.token.TypeRepresentation;

public enum ConstantType implements TypeRepresentation {
    //TODO use double for values!
    E("e", (int) Math.E), PI("pi", (int) Math.PI);

    private final String representation;
    public final int value;

    ConstantType(String representation, int value) {
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
