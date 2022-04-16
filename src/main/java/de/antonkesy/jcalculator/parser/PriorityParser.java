package de.antonkesy.jcalculator.parser;

import de.antonkesy.jcalculator.parser.ast_nodes.ExpressionNode;
import de.antonkesy.jcalculator.parser.ast_nodes.Node;
import de.antonkesy.jcalculator.parser.exception.MissingTokenException;
import de.antonkesy.jcalculator.tokenizer.Tokenizer;
import de.antonkesy.jcalculator.tokenizer.token.IToken;

import java.util.Collection;

public class PriorityParser implements IParser {
    private final Tokenizer tokenizer;
    private final IParser nextHigher;
    private final Collection<IToken> tokensToParse;

    protected PriorityParser(IParser nextHigherPartialParser, Tokenizer tokenizer, Collection<IToken> tokensToParse) {
        this.nextHigher = nextHigherPartialParser;
        this.tokenizer = tokenizer;
        this.tokensToParse = tokensToParse;
    }

    @Override
    public Node parse() throws MissingTokenException {
        Node left = nextHigher.parse();

        while (isSameTokenType(tokenizer.peek())) {
            left = new ExpressionNode(tokenizer.nextToken(), left, nextHigher.parse());
        }
        return left;
    }

    protected boolean isSameTokenType(IToken token) {
        return token != null && tokensToParse.stream().anyMatch(t -> t.getTypeRepresentation().equals(token.getTypeRepresentation()));
    }

}
