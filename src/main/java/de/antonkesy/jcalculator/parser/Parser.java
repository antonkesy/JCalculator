package de.antonkesy.jcalculator.parser;

import de.antonkesy.jcalculator.parser.ast_nodes.Node;
import de.antonkesy.jcalculator.parser.exception.MissingTokenException;
import de.antonkesy.jcalculator.tokenizer.Tokenizer;
import de.antonkesy.jcalculator.tokenizer.token.map.ITokenMap;

public final class Parser implements IParser {
    private final Tokenizer tokenizer;
    private final ITokenMap tokenMap;

    private IParser parser;

    public Parser(Tokenizer tokenizer, ITokenMap tokenMap) {
        this.tokenizer = tokenizer;
        this.tokenMap = tokenMap;
        setupParser();
    }

    private void setupParser() {
        parser = new LiteralParser(tokenizer);
        parser = new PairParser(this, parser, tokenizer, tokenMap.getPairs());
        for (int i = tokenMap.getFirstModifiablePriority(); i <= tokenMap.getLastPriority(); ++i) {
            parser = new PriorityParser(parser, tokenizer, tokenMap.getToken(i));
        }
    }

    public Node parse() throws MissingTokenException {
        return parser.parse();
    }
}
