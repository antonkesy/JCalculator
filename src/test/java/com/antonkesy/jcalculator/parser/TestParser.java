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
    void testParserSimple1() {
        //42 + 73
        ArrayList<Token> testToken = new ArrayList<>();
        testToken.add(new LiteralToken(42));
        testToken.add(new OperatorToken(OperatorType.ADD));
        testToken.add(new LiteralToken(73));

        Parser parser = new Parser(testToken);
        Node result = parser.getRootNode();
        if (!(result instanceof ExpressionNode)) fail();
        ExpressionNode root = (ExpressionNode) result;
        FactorNode left = (FactorNode) root.leftChild;
        FactorNode right = (FactorNode) root.rightChild;

        assertEquals(new ExpressionNode(new OperatorToken(OperatorType.ADD), left, right), root);
        assertEquals(new FactorNode(new LiteralToken(42)), left);
        assertEquals(new FactorNode(new LiteralToken(73)), right);
    }

    @Test
    void testParserSimple2() {
        //42 + 73 * 101
        ArrayList<Token> testToken = new ArrayList<>();
        testToken.add(new LiteralToken(42));
        testToken.add(new OperatorToken(OperatorType.ADD));
        testToken.add(new LiteralToken(73));
        testToken.add(new OperatorToken(OperatorType.MULTIPLY));
        testToken.add(new LiteralToken(101));

        Parser parser = new Parser(testToken);
        Node result = parser.getRootNode();
        if (!(result instanceof ExpressionNode)) fail();
        ExpressionNode root = (ExpressionNode) result;
        ExpressionNode left = (ExpressionNode) root.leftChild; //exp 42 + 73
        FactorNode right = (FactorNode) root.rightChild; // 101

        FactorNode node42 = new FactorNode(new LiteralToken(42));
        FactorNode node73 = new FactorNode(new LiteralToken(73));
        assertEquals(new ExpressionNode(new OperatorToken(OperatorType.MULTIPLY), left, right), root);
        assertEquals(new ExpressionNode(new OperatorToken(OperatorType.ADD), node42, node73), left);
        assertEquals(new FactorNode(new LiteralToken(101)), right);
    }
}
