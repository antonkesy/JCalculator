package de.antonkesy.jcalculator.parser;

import de.antonkesy.jcalculator.parser.ast_nodes.ExpressionNode;
import de.antonkesy.jcalculator.parser.ast_nodes.FactorNode;
import de.antonkesy.jcalculator.parser.ast_nodes.Node;
import de.antonkesy.jcalculator.parser.exception.MissingTokenException;
import de.antonkesy.jcalculator.tokenizer.Tokenizer;
import de.antonkesy.jcalculator.tokenizer.token.IToken;
import de.antonkesy.jcalculator.tokenizer.token.PairToken;
import de.antonkesy.jcalculator.tokenizer.token.Token;

import java.util.Collection;

public class PriorityParser implements IParser {
    private final Tokenizer tokenizer;
    private final IParser root, nextHigher;
    private final Collection<IToken> tokensToParse;
    private final Collection<PairToken> pairs;

    protected PriorityParser(IParser root, IParser nextHigherPartialParser, Tokenizer tokenizer, Collection<IToken> tokensToParse, Collection<PairToken> pairs) {
        this.root = root;
        this.nextHigher = nextHigherPartialParser;
        this.tokenizer = tokenizer;
        this.tokensToParse = tokensToParse;
        this.pairs = pairs;
    }

    @Override
    public Node parse() throws MissingTokenException {
        if (nextHigher == null) {
            return new FactorNode(tokenizer.nextToken());
        }
        Node left = nextHigher.parse();
        left = parsePair(left);

        while (isSameTokenType(tokenizer.peek())) {
            left = new ExpressionNode(tokenizer.nextToken(), left, nextHigher.parse());
        }
        return left;
    }

    private Node parsePair(Node left) throws MissingTokenException {
        //TODO dont push closing on ast
        PairToken pair = getStartPairToken(left.token);

        if (pair == null) {
            return left;
        }

        Node inside = null;
        while (!isEndToken(pair, tokenizer.peek())) {
            inside = root.parse();
            if (inside == null || inside.token == null) break;
        }
        tokenizer.nextToken();
        if (inside == null) throw new MissingTokenException(pair.end);
        return inside;
    }

    private boolean isEndToken(PairToken pair, IToken peek) {
        return pair != null && pair.end.equals(peek);
    }


    protected boolean isSameTokenType(IToken token) {
        return token != null && tokensToParse.stream().anyMatch(t -> t.getTypeRepresentation().equals(token.getTypeRepresentation()));
    }

    private PairToken getStartPairToken(IToken token) {
        return pairs.stream().filter(p -> p.front.equals(token)).findAny().orElse(null);
    }
}
