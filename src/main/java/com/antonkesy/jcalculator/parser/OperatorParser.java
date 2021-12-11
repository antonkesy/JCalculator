package com.antonkesy.jcalculator.parser;

import com.antonkesy.jcalculator.tokenizer.Tokenizer;
import com.antonkesy.jcalculator.tokenizer.token.Token;
import com.antonkesy.jcalculator.tokenizer.token.operator.OperatorToken;
import com.antonkesy.jcalculator.tokenizer.token.operator.OperatorType;

public class OperatorParser extends PartialParser {

    private final OperatorType[] typeOfOperators;

    protected OperatorParser(IParser nextHigherPartialParser, Tokenizer tokenizer, OperatorType[] typeOfOperators) {
        super(nextHigherPartialParser, tokenizer);
        this.typeOfOperators = typeOfOperators;
    }

    @Override
    protected boolean nextIsNotExpectedEnd(Token token) {
        return token instanceof OperatorToken && isSameType(token);
    }

    protected boolean isSameType(Token token) {
        OperatorToken opTok = (OperatorToken) token;
        for (OperatorType type : typeOfOperators) {
            if (type == opTok.operator) return true;
        }
        return false;
    }
}
