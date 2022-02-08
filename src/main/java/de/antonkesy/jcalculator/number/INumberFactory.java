package de.antonkesy.jcalculator.number;

import de.antonkesy.jcalculator.tokenizer.token.value.constant.ConstantType;

import java.util.List;

public interface INumberFactory {
    INumber getNumber(String tokenString);

    List<ConstantType> getConstants();
}
