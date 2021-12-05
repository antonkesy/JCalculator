package com.antonkesy.jcalculator;

import com.antonkesy.jcalculator.parser.Parser;
import com.antonkesy.jcalculator.parser.ast_nodes.Node;
import com.antonkesy.jcalculator.parser.exception.MissingTokenException;
import com.antonkesy.jcalculator.tokenizer.token.Token;
import com.antonkesy.jcalculator.tokenizer.Tokenizer;
import com.antonkesy.jcalculator.tokenizer.token.exception.UnknownTokenException;

import java.util.List;

public class JCalculator {
    //TODO create calculator object, non static
    //TODO override list of constants
    public static Token calculate(Tokenizer tokenizer) throws MissingTokenException {
        Parser parser = new Parser(tokenizer);
        Node rootAst = parser.getRootNode();
        return null;
    }

    public static Token calculate(String expressionString) throws UnknownTokenException, MissingTokenException {
        return calculate(new Tokenizer(expressionString));
    }

    public static Token calculate(List<Token> tokenList) throws MissingTokenException {
        Tokenizer tokenizer = new Tokenizer(tokenList);
        return calculate(tokenizer);
    }

}

