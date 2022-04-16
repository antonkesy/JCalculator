package de.antonkesy.jcalculator.imp.bigdecimal;

import de.antonkesy.jcalculator.number.INumber;
import de.antonkesy.jcalculator.tokenizer.token.*;
import de.antonkesy.jcalculator.tokenizer.token.map.TokenMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DefaultTokenMap extends TokenMap {

    @Override
    protected HashMap<String, IToken> fillMap() {
        HashMap<String, IToken> token = new HashMap<>();
        token.put("+", new OperatorToken("+", 4, INumber::add));
        token.put("-", new OperatorToken("-", 4, INumber::subtract));
        token.put("/", new OperatorToken("/", 3, INumber::divide));
        token.put("*", new OperatorToken("*", 3, INumber::multiply));
        token.put("^", new OperatorToken("^", 2, INumber::pow));

        token.put("pi", new ValueToken("pi", new BigDecimalNumber(Math.PI)));
        token.put("e", new ValueToken("e", new BigDecimalNumber(Math.E)));

        return token;
    }

    @Override
    protected List<PairToken> fillPairToken() {
        PairElementToken openParentheses = new PairElementToken("(");
        PairElementToken closeParentheses = new PairElementToken(")");
        ArrayList<PairToken> pairs = new ArrayList<>();
        pairs.add(new PairToken(openParentheses, closeParentheses));
        return pairs;
    }

    @Override
    public int getLastPriority() {
        return 4;
    }
}
