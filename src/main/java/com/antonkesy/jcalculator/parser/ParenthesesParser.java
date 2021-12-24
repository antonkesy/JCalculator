package com.antonkesy.jcalculator.parser;

import com.antonkesy.jcalculator.parser.ast_nodes.Node;
import com.antonkesy.jcalculator.parser.exception.MissingTokenException;
import com.antonkesy.jcalculator.tokenizer.Tokenizer;
import com.antonkesy.jcalculator.tokenizer.token.Token;
import com.antonkesy.jcalculator.tokenizer.token.separator.SeparatorToken;
import com.antonkesy.jcalculator.tokenizer.token.separator.SeparatorType;

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
