package com.antonkesy.jcalculator.tokenizer.token.term;

import com.antonkesy.jcalculator.tokenizer.token.Token;

public class ExponentToken implements Token {
    @Override
    public String getRepresentation() {
        return "^";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj != null && getClass() == obj.getClass();
    }
}
