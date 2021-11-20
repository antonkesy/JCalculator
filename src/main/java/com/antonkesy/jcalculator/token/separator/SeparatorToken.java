package com.antonkesy.jcalculator.token.separator;

import com.antonkesy.jcalculator.token.Token;

public class SeparatorToken implements Token {
    public final SeparatorType separatorType;

    public SeparatorToken(SeparatorType type) {
        this.separatorType = type;
    }
}
