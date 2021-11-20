package com.antonkesy.jcalculator.token.separator;

import com.antonkesy.jcalculator.token.Token;

public class SeparatorToken implements Token {
    public final SeparatorType separatorType;

    public SeparatorToken(SeparatorType type) {
        this.separatorType = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeparatorToken that = (SeparatorToken) o;
        return separatorType == that.separatorType;
    }

}
