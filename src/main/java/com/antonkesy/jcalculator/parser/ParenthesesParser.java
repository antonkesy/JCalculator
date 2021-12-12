package com.antonkesy.jcalculator.parser;

import com.antonkesy.jcalculator.parser.ast_nodes.Node;
import com.antonkesy.jcalculator.tokenizer.token.Token;
import com.antonkesy.jcalculator.tokenizer.token.separator.SeparatorToken;
import com.antonkesy.jcalculator.tokenizer.token.separator.SeparatorType;

public class ParenthesesParser implements IParser {

    private final IParser rootParser;
    private final IParser nextHigher;

    protected ParenthesesParser(IParser rootParser, IParser nextHigher) {
        this.rootParser = rootParser;
        this.nextHigher = nextHigher;
    }

    @Override
    public Node parse() {
        Node left = nextHigher.parse();
        if (left != null && isSeparatorType(left.token, SeparatorType.OPEN)) {
            Node inside;
            Node right = null;
            while (true) {
                inside = right;
                right = rootParser.parse();
                if (right != null && isSeparatorType(right.token, SeparatorType.CLOSE)) {
                    return inside;
                }
            }
        }
        return left;
    }

    private boolean isSeparatorType(Token token, SeparatorType type) {
        return token instanceof SeparatorToken && ((SeparatorToken) token).separatorType == type;
    }
}
