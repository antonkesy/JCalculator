package de.antonkesy.jcalculator.number;

public interface INumber {

    INumber add(INumber rightValue);

    INumber subtract(INumber rightValue);

    INumber multiply(INumber rightValue);

    INumber divide(INumber rightValue);

    INumber pow(INumber exponent);

    String toPlainString();

    boolean equals(Object obj);

}
