package de.antonkesy.jcalculator;

import de.antonkesy.jcalculator.number.INumber;
import de.antonkesy.jcalculator.number.INumberFactory;
import de.antonkesy.jcalculator.parser.Parser;
import de.antonkesy.jcalculator.parser.ast_nodes.ExpressionNode;
import de.antonkesy.jcalculator.parser.ast_nodes.FactorNode;
import de.antonkesy.jcalculator.parser.ast_nodes.Node;
import de.antonkesy.jcalculator.parser.exception.MissingTokenException;
import de.antonkesy.jcalculator.tokenizer.Tokenizer;
import de.antonkesy.jcalculator.tokenizer.exception.UnknownTokenException;
import de.antonkesy.jcalculator.tokenizer.token.Token;
import de.antonkesy.jcalculator.tokenizer.token.operator.OperatorToken;
import de.antonkesy.jcalculator.tokenizer.token.value.ValueToken;

import java.util.List;

public class JCalculator {
    private final INumberFactory numberFactory;

    public JCalculator(INumberFactory numberFactory) {
        this.numberFactory = numberFactory;
    }

    /**
     * @param expressionString e.g "1+1","(3+pi*3)-1"
     * @return result of expressionString
     * @throws UnknownTokenException if expression string can't be tokenized
     * @throws MissingTokenException if expression is not complete
     */
    public String calculate(String expressionString) throws UnknownTokenException, MissingTokenException {
        return calculate(new Tokenizer(expressionString, numberFactory));
    }

    /**
     * @param tokenizer if tokenizer already exists
     */
    public String calculate(Tokenizer tokenizer) throws MissingTokenException {
        Parser parser = new Parser(tokenizer);
        Node rootAst;
        rootAst = parser.parse();
        return calculateAst(rootAst).toPlainString();
    }


    /**
     * @param tokenList need's to be correctly build or wrong result gets calculated with no error
     */
    public String calculate(List<Token> tokenList) throws MissingTokenException {
        Tokenizer tokenizer = new Tokenizer(tokenList, numberFactory);
        return calculate(tokenizer);
    }

    private INumber calculateAst(Node astNode) {
        if (astNode == null) {
            return numberFactory.getNumber("0");
        } else if (astNode instanceof FactorNode) {
            return ((ValueToken) astNode.token).getValue();
        } else if (astNode instanceof ExpressionNode) {
            return calculateExpression((ExpressionNode) astNode);
        }
        return numberFactory.getNumber("0");
    }

    private INumber calculateExpression(ExpressionNode node) {
        INumber result = numberFactory.getNumber("0");
        INumber leftValue = calculateAst(node.leftChild);
        INumber rightValue = calculateAst(node.rightChild);
        switch (((OperatorToken) node.token).operator) {
            case ADD:
                result = leftValue.add(rightValue);
                break;
            case SUB:
                result = leftValue.subtract(rightValue);
                break;
            case MULTIPLY:
                result = leftValue.multiply(rightValue);
                break;
            case DIVIDE:
                result = leftValue.divide(rightValue);
                break;
            case EXPONENT:
                result = leftValue.pow(rightValue);
                break;
        }
        return result;
    }
}

