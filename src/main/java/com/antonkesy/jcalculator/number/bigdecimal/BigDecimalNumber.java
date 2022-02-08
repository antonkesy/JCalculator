package com.antonkesy.jcalculator.number.bigdecimal;

import com.antonkesy.jcalculator.number.INumber;

import java.math.BigDecimal;
import java.util.Objects;

public class BigDecimalNumber implements INumber {
    public BigDecimal value;

    public BigDecimalNumber(String tokenString) {
        value = new BigDecimal(tokenString);
    }

    public BigDecimalNumber(double value) {
        this.value = new BigDecimal(value);
    }

    @Override
    public INumber add(INumber rightValue) {
        value = value.add(((BigDecimalNumber) rightValue).value);
        return this;
    }

    @Override
    public INumber subtract(INumber rightValue) {
        value = value.subtract(((BigDecimalNumber) rightValue).value);
        return this;
    }

    @Override
    public INumber multiply(INumber rightValue) {
        value = value.multiply(((BigDecimalNumber) rightValue).value);
        return this;
    }

    @Override
    public INumber divide(INumber rightValue) {
        value = value.divide(((BigDecimalNumber) rightValue).value);
        return this;
    }

    @Override
    public INumber pow(INumber exponent) {
        value = value.pow(((BigDecimalNumber) exponent).value.intValue());
        return this;
    }

    @Override
    public String toPlainString() {
        return value.toPlainString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BigDecimalNumber that = (BigDecimalNumber) o;
        return Objects.equals(value, that.value);
    }

}
