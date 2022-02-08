package de.antonkesy.jcalculator.parser;

import de.antonkesy.jcalculator.number.bigdecimal.BigDecimalFactory;
import de.antonkesy.jcalculator.number.bigdecimal.BigDecimalNumber;
import de.antonkesy.jcalculator.parser.ast_nodes.ExpressionNode;
import de.antonkesy.jcalculator.parser.ast_nodes.FactorNode;
import de.antonkesy.jcalculator.parser.ast_nodes.Node;
import de.antonkesy.jcalculator.tokenizer.Tokenizer;
import de.antonkesy.jcalculator.tokenizer.token.Token;
import de.antonkesy.jcalculator.tokenizer.token.operator.OperatorToken;
import de.antonkesy.jcalculator.tokenizer.token.operator.OperatorType;
import de.antonkesy.jcalculator.tokenizer.token.value.literal.LiteralToken;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestParser {
    @Test
    void testParserSimple1() {
        //42 + 73
        ArrayList<Token> testToken = new ArrayList<>();
        testToken.add(new LiteralToken(new BigDecimalNumber(42)));
        testToken.add(new OperatorToken(OperatorType.ADD));
        testToken.add(new LiteralToken(new BigDecimalNumber(73)));
        Tokenizer tokenizer = new Tokenizer(testToken, new BigDecimalFactory());

        try {
            Parser parser = new Parser(tokenizer);
            Node result = parser.parse();
            if (!(result instanceof ExpressionNode)) fail();
            ExpressionNode root = (ExpressionNode) result;
            FactorNode left = (FactorNode) root.leftChild;
            FactorNode right = (FactorNode) root.rightChild;

            assertEquals(new ExpressionNode(new OperatorToken(OperatorType.ADD), left, right), root);
            assertEquals(new FactorNode(new LiteralToken(new BigDecimalNumber(42))), left);
            assertEquals(new FactorNode(new LiteralToken(new BigDecimalNumber(73))), right);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testParserSimple2() {
        //42 + 73 * 101
        ArrayList<Token> testToken = new ArrayList<>();
        testToken.add(new LiteralToken(new BigDecimalNumber(42)));
        testToken.add(new OperatorToken(OperatorType.ADD));
        testToken.add(new LiteralToken(new BigDecimalNumber(73)));
        testToken.add(new OperatorToken(OperatorType.MULTIPLY));
        testToken.add(new LiteralToken(new BigDecimalNumber(101)));
        Tokenizer tokenizer = new Tokenizer(testToken, new BigDecimalFactory());

        try {
            Parser parser = new Parser(tokenizer);
            Node result = parser.parse();
            if (!(result instanceof ExpressionNode)) fail();
            ExpressionNode root = (ExpressionNode) result;
            FactorNode left = (FactorNode) root.leftChild; //42
            ExpressionNode right = (ExpressionNode) root.rightChild; // (73 * 101)

            FactorNode node101 = new FactorNode(new LiteralToken(new BigDecimalNumber(101)));
            FactorNode node73 = new FactorNode(new LiteralToken(new BigDecimalNumber(73)));
            assertEquals(new ExpressionNode(new OperatorToken(OperatorType.ADD), left, right), root);
            assertEquals(new ExpressionNode(new OperatorToken(OperatorType.MULTIPLY), node73, node101), right);
            assertEquals(new FactorNode(new LiteralToken(new BigDecimalNumber(42))), left);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
