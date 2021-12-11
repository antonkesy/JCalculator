package com.antonkesy.jcalculator.parser;

import com.antonkesy.jcalculator.parser.ast_nodes.ExpressionNode;
import com.antonkesy.jcalculator.parser.ast_nodes.Node;
import com.antonkesy.jcalculator.tokenizer.Tokenizer;
import com.antonkesy.jcalculator.tokenizer.token.Token;

public abstract class PartialParser implements IParser {

    //TODO merge with operator parser?
    protected final Tokenizer tokenizer;
    protected final IParser nextHigherPartialParser;

    protected PartialParser(IParser nextHigherPartialParser, Tokenizer tokenizer) {
        this.nextHigherPartialParser = nextHigherPartialParser;
        this.tokenizer = tokenizer;
    }

    public abstract Node parse();

    protected abstract boolean nextIsNotExpectedEnd(Token token);

}
