package de.antonkesy.jcalculator.tokenizer.token.map;

import de.antonkesy.jcalculator.tokenizer.token.IToken;
import de.antonkesy.jcalculator.tokenizer.token.PairToken;

import java.util.Collection;

public interface ITokenMap {
    Collection<IToken> getToken(int level);

    Collection<IToken> getAll();

    Collection<PairToken> getPairs(int level);

    int getLastPriority();
}
