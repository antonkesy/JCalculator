package com.antonkesy.jcalculator.tokenizer.token.value;

import com.antonkesy.jcalculator.number.INumber;
import com.antonkesy.jcalculator.tokenizer.token.Token;

public interface ValueToken extends Token {
    INumber getValue();
}
