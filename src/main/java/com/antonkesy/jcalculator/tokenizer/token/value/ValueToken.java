package com.antonkesy.jcalculator.tokenizer.token.value;

import com.antonkesy.jcalculator.tokenizer.token.Token;

import java.math.BigDecimal;

public interface ValueToken extends Token {
    BigDecimal getValue();
}
