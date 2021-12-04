package com.antonkesy.jcalculator.parser.ast_nodes;

import com.antonkesy.jcalculator.tokenizer.token.Token;

import java.util.Objects;

public abstract class Node {
    public final Token token;

    public Node(Token token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(token, node.token);
    }

    abstract String getRepresentation();
}
