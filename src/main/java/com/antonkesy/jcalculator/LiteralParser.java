package com.antonkesy.jcalculator;

import com.antonkesy.jcalculator.parser.IParser;
import com.antonkesy.jcalculator.parser.ast_nodes.FactorNode;
import com.antonkesy.jcalculator.parser.ast_nodes.Node;
import com.antonkesy.jcalculator.tokenizer.Tokenizer;

public class LiteralParser implements IParser {
    @Override
    public Node parse(Tokenizer tokenizer) {
        return new FactorNode(tokenizer.nextToken());
    }
}
