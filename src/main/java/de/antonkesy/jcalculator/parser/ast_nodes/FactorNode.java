package de.antonkesy.jcalculator.parser.ast_nodes;

import de.antonkesy.jcalculator.tokenizer.token.Token;

public class FactorNode extends Node {

    public FactorNode(Token token) {
        super(token);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    String getRepresentation() {
        return token.getRepresentation();
    }
}
