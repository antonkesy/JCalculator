package de.antonkesy.jcalculator.parser;

import de.antonkesy.jcalculator.parser.ast_nodes.Node;
import de.antonkesy.jcalculator.parser.exception.MissingTokenException;
import de.antonkesy.jcalculator.tokenizer.Tokenizer;
import de.antonkesy.jcalculator.tokenizer.token.Token;
import de.antonkesy.jcalculator.tokenizer.token.separator.SeparatorToken;
import de.antonkesy.jcalculator.tokenizer.token.separator.SeparatorType;
import de.antonkesy.jcalculator.tokenizer.token.value.ValueToken;

public class ParenthesesParser implements IParser {

    private final Tokenizer tokenizer;
    private final IParser rootParser;
    private final IParser nextHigher;

    protected ParenthesesParser(IParser rootParser, IParser nextHigher, Tokenizer tokenizer) {
        this.rootParser = rootParser;
        this.nextHigher = nextHigher;
        this.tokenizer = tokenizer;
    }

    @Override
    public Node parse() throws MissingTokenException {
        Node left = nextHigher.parse();
        if (left == null) return null;
        if (isSeparatorType(left.token, SeparatorType.OPEN)) {
            Node inside = null;
            while (!isSeparatorType(tokenizer.peek(), SeparatorType.CLOSE)) {
                inside = rootParser.parse();
                //if parsing failed -> close separator token probably not available
                if (inside == null || inside.token == null)
                    throw new MissingTokenException(new SeparatorToken(SeparatorType.CLOSE));
            }
            tokenizer.nextToken();
            //if inside of brackets is empty
            if (inside == null) {
                throw new MissingTokenException(ValueToken.class.getName());
            }
            return inside;
        } else if (isSeparatorType(left.token, SeparatorType.CLOSE)) {
            throw new MissingTokenException(new SeparatorToken(SeparatorType.OPEN));
        }

        return left;
    }

    private boolean isSeparatorType(Token token, SeparatorType type) {
        return token instanceof SeparatorToken && ((SeparatorToken) token).separatorType == type;
    }
}
