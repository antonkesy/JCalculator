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
        while (nextIsNotExpectedEnd(tokenizer.peek())) {
            left = new ExpressionNode(tokenizer.nextToken(), left, nextHigherPartialParser.parse());
        }
        return left;
    }

    protected abstract boolean nextIsNotExpectedEnd(Token token);

}
