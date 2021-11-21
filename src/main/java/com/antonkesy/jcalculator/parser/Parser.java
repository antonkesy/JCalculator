package com.antonkesy.jcalculator.parser;

import com.antonkesy.jcalculator.parser.ast_nodes.ExpressionNode;
import com.antonkesy.jcalculator.parser.ast_nodes.FactorNode;
import com.antonkesy.jcalculator.parser.ast_nodes.Node;
import com.antonkesy.jcalculator.parser.double_linked_token.DoubleLinkedToken;
import com.antonkesy.jcalculator.tokenizer.token.Token;
import com.antonkesy.jcalculator.tokenizer.token.value.ValueToken;

import java.util.List;

public final class Parser {
    public static Node buildAST(List<Token> tokens) {
        if (tokens.isEmpty()) return null;
        Node lastNode = buildExpression(null, buildDoubleLinkedTokenList(tokens));
        return getRootNode(lastNode);
    }


    public static Node buildExpression(Node lastNode, DoubleLinkedToken tokenToAdd) {
        //TODO return root not last node but root!
        if (tokenToAdd == null) return lastNode;
        //terminal
        if (tokenToAdd.value instanceof ValueToken) {
            return buildExpression(new FactorNode(tokenToAdd.value, lastNode), tokenToAdd.after);
        }
        /*
        if(tokenToAdd instanceof SeparatorToken){
           //TODO add separator
        }*/
        //non-terminal
        return new ExpressionNode(tokenToAdd.value, null, lastNode, buildExpression(lastNode, tokenToAdd.after));
    }

    public static DoubleLinkedToken buildDoubleLinkedTokenList(List<Token> tokenList) {
        DoubleLinkedToken start = null;
        DoubleLinkedToken last = null;
        for (Token token : tokenList) {
            if (start == null) {
                start = last;
            }
            DoubleLinkedToken newLinkedToken = new DoubleLinkedToken(token, last);
            if (last != null) {
                last.after = newLinkedToken;
            }
            last = newLinkedToken;
        }
        return start;
    }

    private static Node getRootNode(Node lastNode) {
        if (lastNode.parent != null) {
            return getRootNode(lastNode.parent);
        }
        return lastNode;
    }
}
