package de.antonkesy.jcalculator.tokenizer.token.map;

import de.antonkesy.jcalculator.tokenizer.token.IToken;
import de.antonkesy.jcalculator.tokenizer.token.PairToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public abstract class TokenMap implements ITokenMap {
    public final HashMap<String, IToken> token = new HashMap<>();
    public final List<PairToken> tokenPairs = new ArrayList<>();

    public TokenMap() {
        this.token.putAll(fillMap());
        this.tokenPairs.addAll(fillPairToken());
    }

    protected abstract HashMap<String, IToken> fillMap();

    protected abstract List<PairToken> fillPairToken();

    @Override
    public Collection<IToken> getToken(int level) {
        return token.values().stream().filter(t -> t.getPriority() == level).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Collection<IToken> getAll() {
        return token.values();
    }

    @Override
    public List<PairToken> getPairs(int level) {
        return tokenPairs.stream().filter(t -> t.getPriority() == level).collect(Collectors.toCollection(ArrayList::new));
    }
}
