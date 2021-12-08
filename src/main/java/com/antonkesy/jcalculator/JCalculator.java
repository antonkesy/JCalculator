package com.antonkesy.jcalculator;

import com.antonkesy.jcalculator.parser.Parser;
import com.antonkesy.jcalculator.parser.ast_nodes.ExpressionNode;
import com.antonkesy.jcalculator.parser.ast_nodes.FactorNode;
import com.antonkesy.jcalculator.parser.ast_nodes.Node;
import com.antonkesy.jcalculator.parser.exception.MissingTokenException;
import com.antonkesy.jcalculator.tokenizer.token.Token;
import com.antonkesy.jcalculator.tokenizer.Tokenizer;
import com.antonkesy.jcalculator.tokenizer.exception.UnknownTokenException;
import com.antonkesy.jcalculator.tokenizer.token.operator.OperatorToken;
import com.antonkesy.jcalculator.tokenizer.token.value.ValueToken;

import java.util.List;

public class JCalculator {
    public static int calculate(Tokenizer tokenizer) throws MissingTokenException {
        Parser parser = new Parser(tokenizer);
        Node rootAst = parser.getRootNode();
        return calculateAst(rootAst);
    }

    public static int calculate(String expressionString) throws UnknownTokenException, MissingTokenException {
        return calculate(new Tokenizer(expressionString));
    }

    public static int calculate(List<Token> tokenList) throws MissingTokenException {
        Tokenizer tokenizer = new Tokenizer(tokenList);
        return calculate(tokenizer);
    }

    private static int calculateAst(Node astNode) {
        if (astNode == null) {
            return 0;
        } else if (astNode instanceof FactorNode) {
            return ((ValueToken) astNode.token).getValue();
        } else if (astNode instanceof ExpressionNode) {
            return calculateExpression((ExpressionNode) astNode);
        }
        return 0;
    }

    private static int calculateExpression(ExpressionNode node) {
        int result = 0;
        int leftValue = calculateAst(node.leftChild);
        int rightValue = calculateAst(node.rightChild);
        switch (((OperatorToken) node.token).operator) {
            case ADD:
                result = leftValue + rightValue;
                break;
            case SUB:
                result = leftValue - rightValue;
                break;
            case MULTIPLY:
                result = leftValue * rightValue;
                break;
            case DIVIDE:
                result = leftValue / rightValue;
                break;
        }
        return result;
    }
}

