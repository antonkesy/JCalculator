package de.antonkesy.jcalculator.tokenizer.token;

import de.antonkesy.jcalculator.number.INumber;

public class ValueToken extends Token {

    public final INumber value;

    /**
     * For literals
     *
     * @param value
     */
    public ValueToken(INumber value) {
        super(value.toPlainString(), 0);
        this.value = value;
    }

    /**
     * For constants
     *
     * @param name
     * @param value
     */
    public ValueToken(String name, INumber value) {
        super(name, 0);
        this.value = value;
    }

}
