package de.antonkesy.jcalculator.tokenizer.token.value;

import de.antonkesy.jcalculator.number.INumber;
import de.antonkesy.jcalculator.tokenizer.token.Token;

public interface ValueToken extends Token {
    INumber getValue();
}
