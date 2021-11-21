package com.antonkesy.jcalculator;

import com.antonkesy.jcalculator.tokenizer.token.Token;
import com.antonkesy.jcalculator.parser.Parser;
import com.antonkesy.jcalculator.tokenizer.Tokenizer;
import com.antonkesy.jcalculator.tokenizer.token.exception.UnknownTokenException;

import java.util.List;

public class JCalculator {
    public static Token calculate(List<Token> tokenList) throws UnknownTokenException {
        return null;
    }

    public static Token calculate(String expressionString) throws UnknownTokenException {
        List<Token> expressions = Parser.convertToExpressions(Tokenizer.tokenize(expressionString));
        return calculate(expressions);
    }


}

