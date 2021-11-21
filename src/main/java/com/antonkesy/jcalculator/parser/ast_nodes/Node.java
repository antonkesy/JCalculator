package com.antonkesy.jcalculator.parser.ast_nodes;

import com.antonkesy.jcalculator.tokenizer.token.Token;

import java.util.Objects;

public class Node {
    public final Node parent; //TODO get parent from correctly using recursion
    public final Token token;

    public Node(Token token, Node parent) {
        this.token = token;
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(token, node.token);
    }
}
