package com.antonkesy.jcalculator.parser;

import com.antonkesy.jcalculator.tokenizer.Tokenizer;
import com.antonkesy.jcalculator.tokenizer.token.Token;
import com.antonkesy.jcalculator.tokenizer.token.operator.OperatorToken;
import com.antonkesy.jcalculator.tokenizer.token.operator.OperatorType;

public class MultiParser extends PartialParser {

    protected MultiParser(IParser nextHigherPartialParser, Tokenizer tokenizer) {
        super(nextHigherPartialParser, tokenizer);
    }

    @Override
    protected boolean isSameType(Token token) {
        OperatorToken opTok = (OperatorToken) token;
        for (OperatorType type : new OperatorType[]{OperatorType.MULTIPLY, OperatorType.DIVIDE}) {
            if (type == opTok.operator) return true;
        }
        return false;
    }

    @Override
    protected boolean isInstanceOf(Token token) {
        return token instanceof OperatorToken;
    }
}
