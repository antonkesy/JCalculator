package de.antonkesy.jcalculator.imp.bigdecimal;

import de.antonkesy.jcalculator.number.INumber;
import de.antonkesy.jcalculator.number.INumberFactory;
import de.antonkesy.jcalculator.tokenizer.token.ValueToken;

import java.util.ArrayList;
import java.util.List;

public class BigDecimalFactory implements INumberFactory {

    public INumber getNumber(String tokenString) {
        return new BigDecimalNumber(tokenString);
    }

}
