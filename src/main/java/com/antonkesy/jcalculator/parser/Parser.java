package com.antonkesy.jcalculator.parser;

import com.antonkesy.jcalculator.parser.ast_nodes.ExpressionNode;
import com.antonkesy.jcalculator.parser.ast_nodes.FactorNode;
import com.antonkesy.jcalculator.parser.ast_nodes.Node;
import com.antonkesy.jcalculator.parser.ast_nodes.TermNode;
import com.antonkesy.jcalculator.tokenizer.Tokenizer;
import com.antonkesy.jcalculator.tokenizer.token.Token;
import com.antonkesy.jcalculator.tokenizer.token.operator.OperatorToken;
import com.antonkesy.jcalculator.tokenizer.token.value.ValueToken;

import java.util.ArrayList;
import java.util.List;

public final class Parser {
    private final Tokenizer tokenizer;
    private Node lastNode;
    private Node root;

    public Parser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
        buildAST();
    }

    public Node getRootNode() {
        return root;
    }

    private void buildAST() {
        //TODO not working if last is double term token
        Node result;
        do {
            result = parseNextToken();
            if (result != null) {
                if (lastNode == null) {
                    lastNode = result;
                }
                root = result;
            }
        } while (result != null);
    }

    private Node parseNextToken() {
        Token currentToken = tokenizer.nextToken();
        if (currentToken instanceof ValueToken) {
            return new FactorNode(currentToken);
        } else if (currentToken instanceof OperatorToken && lastNode != null) {
            Node copyLastNode = lastNode;
            Node exp = new ExpressionNode(currentToken, copyLastNode, parseNextToken());
            lastNode = exp;
            return exp;
        }
        //TODO add all token
        return null;
    }


}
