package com.antonkesy.jcalculator.parser.ast_nodes;

import com.antonkesy.jcalculator.tokenizer.token.Token;

public class TermNode extends Node {

    public TermNode(Token token, Node parent) {
        super(token, parent);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    String getRepresentation() {
        return token.toString();
    }
}
