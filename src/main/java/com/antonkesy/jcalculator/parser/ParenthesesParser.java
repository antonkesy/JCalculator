package com.antonkesy.jcalculator.parser;

import com.antonkesy.jcalculator.parser.ast_nodes.Node;
import com.antonkesy.jcalculator.tokenizer.Tokenizer;
import com.antonkesy.jcalculator.tokenizer.token.Token;
import com.antonkesy.jcalculator.tokenizer.token.separator.SeparatorToken;
import com.antonkesy.jcalculator.tokenizer.token.separator.SeparatorType;

public class ParenthesesParser implements IParser {

    Tokenizer tokenizer;
    private final IParser rootParser;
    private final IParser nextHigher;

    protected ParenthesesParser(IParser rootParser, IParser nextHigher, Tokenizer tokenizer) {
        this.rootParser = rootParser;
        this.nextHigher = nextHigher;
        this.tokenizer = tokenizer;
    }

    @Override
    public Node parse() {
        Node left = nextHigher.parse();
        if (left != null && isSeparatorType(left.token, SeparatorType.OPEN)) {
            Node inside = null;
            while (!isSeparatorType(tokenizer.peek(), SeparatorType.CLOSE)) {
                inside = rootParser.parse();
            }
            tokenizer.nextToken();
            return inside;
        }
        return left;
    }

    private boolean isSeparatorType(Token token, SeparatorType type) {
        return token instanceof SeparatorToken && ((SeparatorToken) token).separatorType == type;
    }
}
