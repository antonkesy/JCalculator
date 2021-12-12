package com.antonkesy.jcalculator.parser;

import com.antonkesy.jcalculator.parser.ast_nodes.Node;
import com.antonkesy.jcalculator.tokenizer.Tokenizer;
import com.antonkesy.jcalculator.tokenizer.token.operator.OperatorType;

public final class Parser implements IParser {
    private final Tokenizer tokenizer;

    public Parser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public Node parse() {
        IParser parenthesesParser = new ParenthesesParser(this, new LiteralParser(tokenizer), tokenizer);
        IParser exponentParser = new OperatorParser(parenthesesParser, tokenizer, new OperatorType[]{OperatorType.EXPONENT});
        OperatorParser multiParser =
                new OperatorParser(
                        exponentParser,
                        tokenizer,
                        new OperatorType[]{OperatorType.MULTIPLY, OperatorType.DIVIDE}
                );
        OperatorParser addParser =
                new OperatorParser(
                        multiParser,
                        tokenizer,
                        new OperatorType[]{OperatorType.ADD, OperatorType.SUB}
                );
        return addParser.parse();
    }
}
