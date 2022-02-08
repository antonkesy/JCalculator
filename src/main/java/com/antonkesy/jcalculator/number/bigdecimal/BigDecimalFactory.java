package com.antonkesy.jcalculator.number.bigdecimal;

import com.antonkesy.jcalculator.number.INumber;
import com.antonkesy.jcalculator.number.INumberFactory;
import com.antonkesy.jcalculator.tokenizer.token.value.constant.ConstantType;

import java.util.ArrayList;
import java.util.List;

public class BigDecimalFactory implements INumberFactory {

    public INumber getNumber(String tokenString) {
        return new BigDecimalNumber(tokenString);
    }

    public List<ConstantType> getConstants() {
        ArrayList<ConstantType> constants = new ArrayList<>();
        constants.add(new ConstantType("pi", new BigDecimalNumber(Math.PI)));
        constants.add(new ConstantType("e", new BigDecimalNumber(Math.E)));

        return constants;
    }
}
