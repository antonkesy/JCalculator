package com.antonkesy.jcalculator.parser;

import com.antonkesy.jcalculator.parser.ast_nodes.ExpressionNode;
import com.antonkesy.jcalculator.parser.ast_nodes.FactorNode;
import com.antonkesy.jcalculator.parser.ast_nodes.Node;
import com.antonkesy.jcalculator.parser.double_linked_token.DoubleLinkedToken;
import com.antonkesy.jcalculator.tokenizer.token.Token;
import com.antonkesy.jcalculator.tokenizer.token.operator.OperatorToken;
import com.antonkesy.jcalculator.tokenizer.token.operator.OperatorType;
import com.antonkesy.jcalculator.tokenizer.token.value.literal.LiteralToken;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestParser {
    @Test
    void testBuildDoubleLinkedTokenList() {
        ArrayList<Token> testToken = new ArrayList<>();
        testToken.add(new LiteralToken(42));
        testToken.add(new OperatorToken(OperatorType.ADD));
        testToken.add(new LiteralToken(73));

        DoubleLinkedToken start = Parser.buildDoubleLinkedTokenList(testToken);

        for (Token token : testToken) {
            assertEquals(token, start.value);
            start = start.after;
        }
    }

    @Test
    void testParserSimple1() {
        ArrayList<Token> testToken = new ArrayList<>();
        testToken.add(new LiteralToken(42));
        testToken.add(new OperatorToken(OperatorType.ADD));
        testToken.add(new LiteralToken(73));

        Node result = Parser.buildAST(testToken);
        if (!(result instanceof ExpressionNode)) fail();
        ExpressionNode root = (ExpressionNode) result;
        FactorNode left = (FactorNode) root.leftChild;
        FactorNode right = (FactorNode) root.rightChild;

        assertEquals(new ExpressionNode(new OperatorToken(OperatorType.ADD), null, left, right), result);
        assertEquals(new FactorNode(new LiteralToken(42), root), left);
        assertEquals(new FactorNode(new LiteralToken(73), root), right);
    }

    @Test
    void testParserSimple2() {
        ArrayList<Token> testToken = new ArrayList<>();
        testToken.add(new LiteralToken(42));
        testToken.add(new OperatorToken(OperatorType.ADD));
        testToken.add(new LiteralToken(73));
        testToken.add(new OperatorToken(OperatorType.MULTIPLY));
        testToken.add(new LiteralToken(101));

        Node result = Parser.buildAST(testToken);
        if (!(result instanceof ExpressionNode)) fail();
        ExpressionNode root = (ExpressionNode) result;
        FactorNode left = (FactorNode) root.leftChild;
        FactorNode right = (FactorNode) root.rightChild;

        assertEquals(new ExpressionNode(new OperatorToken(OperatorType.MULTIPLY), null, left, right), result);
        assertEquals(new FactorNode(new OperatorToken(OperatorType.ADD), root), left);
        assertEquals(new FactorNode(new LiteralToken(101), root), right);
    }
}
