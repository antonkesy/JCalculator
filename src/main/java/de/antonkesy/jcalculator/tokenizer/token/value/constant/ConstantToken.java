package de.antonkesy.jcalculator.tokenizer.token.value.constant;

import de.antonkesy.jcalculator.number.INumber;
import de.antonkesy.jcalculator.tokenizer.token.value.ValueToken;

public class ConstantToken implements ValueToken {
    private final ConstantType type;

    public ConstantToken(ConstantType type) {
        this.type = type;
    }

    @Override
    public INumber getValue() {
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
