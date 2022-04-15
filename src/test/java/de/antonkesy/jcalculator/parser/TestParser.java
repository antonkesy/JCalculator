package de.antonkesy.jcalculator.parser;

import de.antonkesy.jcalculator.imp.bigdecimal.BigDecimalFactory;
import de.antonkesy.jcalculator.imp.bigdecimal.BigDecimalNumber;
import de.antonkesy.jcalculator.imp.bigdecimal.DefaultTokenMap;
import de.antonkesy.jcalculator.parser.ast_nodes.ExpressionNode;
import de.antonkesy.jcalculator.parser.ast_nodes.FactorNode;
import de.antonkesy.jcalculator.parser.ast_nodes.Node;
import de.antonkesy.jcalculator.tokenizer.Tokenizer;
import de.antonkesy.jcalculator.tokenizer.token.IToken;
import de.antonkesy.jcalculator.tokenizer.token.OperatorToken;
import de.antonkesy.jcalculator.tokenizer.token.ValueToken;
import de.antonkesy.jcalculator.tokenizer.token.map.ITokenMap;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestParser {
    private final ITokenMap tokenMap = new DefaultTokenMap();
    private final OperatorToken add = new OperatorToken("+", 2, null);
    private final OperatorToken multiply = new OperatorToken("*", 1, null);

    @Test
    void testParserSimple1() {
        //42 + 73
        ArrayList<IToken> testToken = new ArrayList<>();
        testToken.add(new ValueToken(new BigDecimalNumber(42)));
        testToken.add(add);
        testToken.add(new ValueToken(new BigDecimalNumber(73)));
        Tokenizer tokenizer = new Tokenizer(testToken, new BigDecimalFactory(), tokenMap);

        try {
            Parser parser = new Parser(tokenizer, tokenMap);
            Node result = parser.parse();
            if (!(result instanceof ExpressionNode)) fail();
            ExpressionNode root = (ExpressionNode) result;
            FactorNode left = (FactorNode) root.leftChild;
            FactorNode right = (FactorNode) root.rightChild;

            assertEquals(new ExpressionNode(add, left, right), root);
            assertEquals(new FactorNode(new ValueToken(new BigDecimalNumber(42))), left);
            assertEquals(new FactorNode(new ValueToken(new BigDecimalNumber(73))), right);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void testParserSimple2() {
        //42 + 73 * 101
        ArrayList<IToken> testToken = new ArrayList<>();
        testToken.add(new ValueToken(new BigDecimalNumber(42)));
        testToken.add(add);
        testToken.add(new ValueToken(new BigDecimalNumber(73)));
        testToken.add(multiply);
        testToken.add(new ValueToken(new BigDecimalNumber(101)));
        Tokenizer tokenizer = new Tokenizer(testToken, new BigDecimalFactory(), tokenMap);

        try {
            Parser parser = new Parser(tokenizer, tokenMap);
            Node result = parser.parse();
            if (!(result instanceof ExpressionNode)) fail();
            ExpressionNode root = (ExpressionNode) result;
            FactorNode left = (FactorNode) root.leftChild; //42
            ExpressionNode right = (ExpressionNode) root.rightChild; // (73 * 101)

            FactorNode node101 = new FactorNode(new ValueToken(new BigDecimalNumber(101)));
            FactorNode node73 = new FactorNode(new ValueToken(new BigDecimalNumber(73)));
            assertEquals(new ExpressionNode(add, left, right), root);
            assertEquals(new ExpressionNode(multiply, node73, node101), right);
            assertEquals(new FactorNode(new ValueToken(new BigDecimalNumber(42))), left);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
