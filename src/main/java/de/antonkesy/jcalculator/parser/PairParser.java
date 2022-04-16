package de.antonkesy.jcalculator.parser;

import de.antonkesy.jcalculator.parser.ast_nodes.Node;
import de.antonkesy.jcalculator.parser.exception.MissingTokenException;
import de.antonkesy.jcalculator.tokenizer.Tokenizer;
import de.antonkesy.jcalculator.tokenizer.token.IToken;
import de.antonkesy.jcalculator.tokenizer.token.PairToken;
import de.antonkesy.jcalculator.tokenizer.token.ValueToken;

import java.util.Collection;

public class PairParser implements IParser {

    private final Tokenizer tokenizer;
    private final IParser rootParser;
    private final IParser nextHigher;

    private final Collection<PairToken> pairs;

    protected PairParser(IParser rootParser, IParser nextHigher, Tokenizer tokenizer, Collection<PairToken> pairs) {
        this.rootParser = rootParser;
        this.nextHigher = nextHigher;
        this.tokenizer = tokenizer;
        this.pairs = pairs;
    }

    @Override
    public Node parse() throws MissingTokenException {
        Node left = nextHigher.parse();
        if (left == null) return null;
        if (isStartPairToken(left.token)) {
            Node inside = null;
            while (!isEndPairToken(tokenizer.peek())) {
                inside = rootParser.parse();
                //if parsing failed -> close separator token probably not available
                if (inside == null || inside.token == null)
                    throw new MissingTokenException(getStartPairToken(left.token).end);
            }
            IToken toDelete = tokenizer.nextToken();
            //if inside of brackets is empty
            if (inside == null) {
                throw new MissingTokenException(ValueToken.class.getName());
            }
            return inside;
        } else if (isEndPairToken(left.token)) {
            throw new MissingTokenException(getStartPairToken(left.token).front);
        }

        return left;
    }

    private boolean isStartPairToken(IToken token) {
        return pairs.stream().anyMatch(p -> p.front.equals(token));
    }

    private boolean isEndPairToken(IToken token) {
        return pairs.stream().anyMatch(p -> p.end.equals(token));
    }

    private PairToken getStartPairToken(IToken token) {
        return pairs.stream().filter(p -> p.front.equals(token)).findAny().orElse(null);
    }
}