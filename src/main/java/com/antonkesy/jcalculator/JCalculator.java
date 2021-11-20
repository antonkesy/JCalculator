package com.antonkesy.jcalculator;

import com.antonkesy.jcalculator.token.Token;
import com.antonkesy.jcalculator.parser.Parser;
import com.antonkesy.jcalculator.tokenizer.Tokenizer;

import java.io.IOException;
import java.util.List;

public class JCalculator {
    public static Token calculate(List<Token> tokenList) throws IOException {
        return null;
    }

    public static Token calculate(String expressionString) throws IOException {
        List<Token> expressions = Parser.convertToExpressions(Tokenizer.tokenize(expressionString));
        return calculate(expressions);
    }


}

