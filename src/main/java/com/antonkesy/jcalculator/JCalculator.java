package com.antonkesy.jcalculator;

import com.antonkesy.jcalculator.parser.token.Token;
import com.antonkesy.jcalculator.parser.Parser;

import java.io.IOException;
import java.util.List;

public class JCalculator {
    public static Token calculate(List<Token> expressions) throws IOException {
        return null;
    }

    public static Token calculate(String expressionString) throws IOException {
        List<Token> expressions = Parser.convertToExpressions(expressionString);
        return calculate(expressions);
    }


}

