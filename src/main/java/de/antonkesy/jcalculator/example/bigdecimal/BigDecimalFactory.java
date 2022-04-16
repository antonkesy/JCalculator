package de.antonkesy.jcalculator.example.bigdecimal;

import de.antonkesy.jcalculator.number.INumber;
import de.antonkesy.jcalculator.number.INumberFactory;

public class BigDecimalFactory implements INumberFactory {

    public INumber getNumber(String tokenString) {
        return new BigDecimalNumber(tokenString);
    }

    @Override
    public INumber getEmpty() {
        return new BigDecimalNumber("0");
    }

}
