package com.antonkesy.jcalculator.parser;

import com.antonkesy.jcalculator.parser.ast_nodes.ExpressionNode;
import com.antonkesy.jcalculator.parser.ast_nodes.Node;
import com.antonkesy.jcalculator.tokenizer.Tokenizer;
import com.antonkesy.jcalculator.tokenizer.token.Token;

public abstract class PartialParser implements IParser {

    private final Tokenizer tokenizer;
    private final IParser nextHigherPartialParser;

    protected PartialParser(IParser nextHigherPartialParser, Tokenizer tokenizer) {
        this.nextHigherPartialParser = nextHigherPartialParser;
        this.tokenizer = tokenizer;
    }

    public Node parse() {
        Node left = nextHigherPartialParser.parse();
        while (isSamePrecedenceAvailable(tokenizer.peek())) {
            left = new ExpressionNode(tokenizer.nextToken(), left, nextHigherPartialParser.parse());
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
