package com.antonkesy.jcalculator.number;

import com.antonkesy.jcalculator.tokenizer.token.value.constant.ConstantType;

import java.util.List;

public interface INumberFactory {
    INumber getNumber(String tokenString);

    List<ConstantType> getConstants();
}
