package com.antonkesy.jcalculator.parser;

import com.antonkesy.jcalculator.parser.ast_nodes.ExpressionNode;
import com.antonkesy.jcalculator.parser.ast_nodes.Node;
import com.antonkesy.jcalculator.tokenizer.Tokenizer;
import com.antonkesy.jcalculator.tokenizer.token.Token;

public abstract class PartialParser implements IParser {

    private final IParser nextHigherPartialParser;

    protected PartialParser(IParser nextHigherPartialParser) {
        this.nextHigherPartialParser = nextHigherPartialParser;
    }

    public Node parse(Tokenizer tokenizer) {
        Node left = nextHigherPartialParser.parse(tokenizer);
        while (isSamePrecedenceAvailable(tokenizer.peek())) {
            left = new ExpressionNode(tokenizer.nextToken(), left, nextHigherPartialParser.parse(tokenizer));
        }
        return left;
    }

    private boolean isSamePrecedenceAvailable(Token token) {
        if (token == null) return false;
        return isInstanceOf(token) && isSameType(token);
    }

    protected abstract boolean isSameType(Token token);

    protected abstract boolean isInstanceOf(Token token);
}
