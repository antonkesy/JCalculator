package com.antonkesy.jcalculator.parser;

import com.antonkesy.jcalculator.parser.IParser;
import com.antonkesy.jcalculator.parser.ast_nodes.FactorNode;
import com.antonkesy.jcalculator.parser.ast_nodes.Node;
import com.antonkesy.jcalculator.tokenizer.Tokenizer;

public class LiteralParser implements IParser {
    private final Tokenizer tokenizer;

    public LiteralParser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    @Override
    public Node parse() {
        return new FactorNode(tokenizer.nextToken());
    }
}
