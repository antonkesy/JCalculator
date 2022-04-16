package de.antonkesy.jcalculator;

import de.antonkesy.jcalculator.number.INumber;
import de.antonkesy.jcalculator.number.INumberFactory;
import de.antonkesy.jcalculator.parser.Parser;
import de.antonkesy.jcalculator.parser.ast_nodes.ExpressionNode;
import de.antonkesy.jcalculator.parser.ast_nodes.Node;
import de.antonkesy.jcalculator.parser.exception.MissingTokenException;
import de.antonkesy.jcalculator.tokenizer.Tokenizer;
import de.antonkesy.jcalculator.tokenizer.exception.UnknownTokenException;
import de.antonkesy.jcalculator.tokenizer.token.*;
import de.antonkesy.jcalculator.tokenizer.token.map.ITokenMap;

import java.util.List;

public class JCalculator {
    private final INumberFactory numberFactory;
    private final ITokenMap tokenMap;

    public JCalculator(INumberFactory numberFactory, ITokenMap tokenMap) {
        this.numberFactory = numberFactory;
        this.tokenMap = tokenMap;
    }

    /**
     * @param expressionString e.g "1+1","(3+pi*3)-1"
     * @return result of expressionString
     * @throws UnknownTokenException if expression string can't be tokenized
     * @throws MissingTokenException if expression is not complete
     */
    public String calculate(String expressionString) throws UnknownTokenException, MissingTokenException, OperatorToken.Operation.OperationException {
        return calculate(new Tokenizer(expressionString, tokenMap));
    }

    /**
     * @param tokenizer if tokenizer already exists
     */
    public String calculate(Tokenizer tokenizer) throws MissingTokenException, OperatorToken.Operation.OperationException {
        Parser parser = new Parser(tokenizer, tokenMap);
        Node rootAst;
        rootAst = parser.parse();
        return calculateAst(rootAst).toPlainString();
    }


    /**
     * @param tokenList need's to be correctly build or wrong result gets calculated with no error
     */
    public String calculate(List<IToken> tokenList) throws MissingTokenException, OperatorToken.Operation.OperationException {
        Tokenizer tokenizer = new Tokenizer(tokenList, tokenMap);
        return calculate(tokenizer);
    }

    private INumber calculateAst(Node astNode) throws OperatorToken.Operation.OperationException {
        //TODO remove separator from ast
        if (astNode == null) {
            return numberFactory.getNumber("0");
        } else if (astNode.token instanceof ValueToken) {
            return ((ValueToken) astNode.token).value;
        } else if (astNode.token instanceof OperatorToken) {
            return calculateExpression((ExpressionNode) astNode);
        }
        return numberFactory.getNumber("0");
    }

    private INumber calculateExpression(ExpressionNode node) throws OperatorToken.Operation.OperationException {
        return (((OperatorToken) node.token).operation.operation(calculateAst(node.leftChild), calculateAst(node.rightChild)));
    }
}

