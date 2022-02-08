package de.antonkesy.jcalculator.parser;

import de.antonkesy.jcalculator.parser.ast_nodes.ExpressionNode;
import de.antonkesy.jcalculator.parser.ast_nodes.Node;
import de.antonkesy.jcalculator.parser.exception.MissingTokenException;
import de.antonkesy.jcalculator.tokenizer.Tokenizer;
import de.antonkesy.jcalculator.tokenizer.token.Token;
import de.antonkesy.jcalculator.tokenizer.token.operator.OperatorToken;
import de.antonkesy.jcalculator.tokenizer.token.operator.OperatorType;

public class OperatorParser implements IParser {
    private final Tokenizer tokenizer;
    private final IParser nextHigher;
    private final OperatorType[] typeOfOperators;

    protected OperatorParser(IParser nextHigherPartialParser, Tokenizer tokenizer, OperatorType[] typeOfOperators) {
        this.nextHigher = nextHigherPartialParser;
        this.tokenizer = tokenizer;
        this.typeOfOperators = typeOfOperators;
    }

    @Override
    public Node parse() throws MissingTokenException {
        Node left = nextHigher.parse();
        while (isSameTokenType(tokenizer.peek())) {
            left = new ExpressionNode(tokenizer.nextToken(), left, nextHigher.parse());
        }
        return left;
    }

    protected boolean isSameTokenType(Token token) {
        return token instanceof OperatorToken && isSameType(token);
    }

    protected boolean isSameType(Token token) {
        OperatorToken opTok = (OperatorToken) token;
        for (OperatorType type : typeOfOperators) {
            if (type == opTok.operator) return true;
        }
        return false;
    }
}
