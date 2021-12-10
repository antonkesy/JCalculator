package com.antonkesy.jcalculator.parser;

import com.antonkesy.jcalculator.LiteralParser;
import com.antonkesy.jcalculator.parser.ast_nodes.ExpressionNode;
import com.antonkesy.jcalculator.parser.ast_nodes.FactorNode;
import com.antonkesy.jcalculator.parser.ast_nodes.Node;
import com.antonkesy.jcalculator.tokenizer.Tokenizer;
import com.antonkesy.jcalculator.tokenizer.token.Token;
import com.antonkesy.jcalculator.tokenizer.token.operator.OperatorToken;
import com.antonkesy.jcalculator.tokenizer.token.operator.OperatorType;

public final class Parser implements IParser {
    private final Tokenizer tokenizer;

    public Parser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public Node parse() {
        MultiParser multiParser = new MultiParser(new LiteralParser(tokenizer), tokenizer);
        AddParser addParser = new AddParser(multiParser, tokenizer);
        return addParser.parse();
    }
}
