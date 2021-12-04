package com.antonkesy.jcalculator;

import com.antonkesy.jcalculator.parser.Parser;
import com.antonkesy.jcalculator.parser.ast_nodes.Node;
import com.antonkesy.jcalculator.tokenizer.token.Token;
import com.antonkesy.jcalculator.tokenizer.Tokenizer;
import com.antonkesy.jcalculator.tokenizer.token.exception.UnknownTokenException;

import java.util.List;

public class JCalculator {
    //TODO create calculator object, non static
    //TODO override list of constants
    public static Token calculate(List<Token> tokenList) {
        Parser parser = new Parser(tokenList);
        Node rootAst = parser.getRootNode();
        return null;
    }

    public static Token calculate(String expressionString) throws UnknownTokenException {
        return calculate(Tokenizer.tokenize(expressionString));
    }


}

