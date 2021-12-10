package com.antonkesy.jcalculator.parser;

import com.antonkesy.jcalculator.LiteralParser;
import com.antonkesy.jcalculator.parser.ast_nodes.ExpressionNode;
import com.antonkesy.jcalculator.parser.ast_nodes.FactorNode;
import com.antonkesy.jcalculator.parser.ast_nodes.Node;
import com.antonkesy.jcalculator.tokenizer.Tokenizer;
import com.antonkesy.jcalculator.tokenizer.token.Token;
import com.antonkesy.jcalculator.tokenizer.token.operator.OperatorToken;
import com.antonkesy.jcalculator.tokenizer.token.operator.OperatorType;

public final class Parser {
    private final Tokenizer tokenizer;
    private final Node root;

    public Parser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
        root = parse();
    }

    public Node getRootNode() {
        return root;
    }

    private Node parse() {
        MultiParser multiParser = new MultiParser(new LiteralParser(), tokenizer);
        AddParser addParser = new AddParser(multiParser, tokenizer);
        return addParser.parse();
    }
}
