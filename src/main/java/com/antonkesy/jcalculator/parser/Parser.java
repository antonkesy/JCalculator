package com.antonkesy.jcalculator.parser;

import com.antonkesy.jcalculator.parser.ast_nodes.ExpressionNode;
import com.antonkesy.jcalculator.parser.ast_nodes.FactorNode;
import com.antonkesy.jcalculator.parser.ast_nodes.Node;
import com.antonkesy.jcalculator.parser.ast_nodes.TermNode;
import com.antonkesy.jcalculator.parser.exception.MissingTokenException;
import com.antonkesy.jcalculator.tokenizer.Tokenizer;
import com.antonkesy.jcalculator.tokenizer.token.Token;
import com.antonkesy.jcalculator.tokenizer.token.operator.OperatorToken;
import com.antonkesy.jcalculator.tokenizer.token.value.ValueToken;

public final class Parser {
    private final Tokenizer tokenizer;
    private Node lastNode;
    private final Node root;

    public Parser(Tokenizer tokenizer) throws MissingTokenException {
        this.tokenizer = tokenizer;
        root = parse();
    }

    public Node getRootNode() {
        return root;
    }

    private Node parse() throws MissingTokenException {
        Token next = tokenizer.peek();
        if (next == null) return null;
        Node parsed;
        if (next instanceof OperatorToken) {
            parsed = parseExpressionNode();
        } else if (next instanceof ValueToken) {
            parsed = parseFactorNode();
            //check if there is something behind and use this as left
            if (tokenizer.peek() != null) {
                lastNode = parsed;
                parsed = parse();
            }
        } else {
            throw new MissingTokenException();
        }
        return parsed;
    }

    private TermNode parseTermNode() {
        return null;
    }

    private FactorNode parseFactorNode() {
        return new FactorNode(tokenizer.nextToken());
    }

    private ExpressionNode parseExpressionNode() throws MissingTokenException {
        Token currentToken = tokenizer.nextToken();
        Node left = lastNode;
        Node right = parse();
        if (expressionNodeExpect(left) && expressionNodeExpect(right))
            return new ExpressionNode(currentToken, left, right);
        //TODO actually wrong token
        throw new MissingTokenException();
    }

    private boolean expressionNodeExpect(Node toCheck) {
        return (toCheck instanceof ExpressionNode || toCheck instanceof FactorNode);
    }
}
