package de.antonkesy.jcalculator.parser.ast_nodes;

import de.antonkesy.jcalculator.tokenizer.token.IToken;

import java.util.Objects;

public abstract class Node {
    public final IToken token;

    public Node(IToken token) {
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
