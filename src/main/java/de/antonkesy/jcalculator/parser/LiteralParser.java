package de.antonkesy.jcalculator.parser;

import de.antonkesy.jcalculator.parser.ast_nodes.FactorNode;
import de.antonkesy.jcalculator.parser.ast_nodes.Node;
import de.antonkesy.jcalculator.tokenizer.Tokenizer;

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
