package com.antonkesy.jcalculator.tokenizer.token.value.constant;

import com.antonkesy.jcalculator.number.INumber;
import com.antonkesy.jcalculator.tokenizer.token.Token;
import com.antonkesy.jcalculator.tokenizer.token.TypeRepresentation;

import java.util.Objects;

public class ConstantType implements TypeRepresentation {
    private final String representation;
    public final INumber value;

    public ConstantType(String representation, INumber value) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConstantType that = (ConstantType) o;
        return Objects.equals(representation, that.representation) && Objects.equals(value, that.value);
    }

}
