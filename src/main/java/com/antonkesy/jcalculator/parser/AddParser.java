package com.antonkesy.jcalculator.parser;

import com.antonkesy.jcalculator.tokenizer.token.Token;
import com.antonkesy.jcalculator.tokenizer.token.operator.OperatorToken;
import com.antonkesy.jcalculator.tokenizer.token.operator.OperatorType;

public class AddParser extends PartialParser {

    protected AddParser(IParser nextHigherPartialParser) {
        super(nextHigherPartialParser);
    }

    @Override
    protected boolean isSameType(Token token) {
        OperatorToken opTok = (OperatorToken) token;
        for (OperatorType type : new OperatorType[]{OperatorType.ADD, OperatorType.SUB}) {
            if (type == opTok.operator) return true;
        }
        return false;
    }

    @Override
    protected boolean isInstanceOf(Token token) {
        return token instanceof OperatorToken;
    }
}
