package de.antonkesy.jcalculator.tokenizer.token.map;

import de.antonkesy.jcalculator.tokenizer.token.IToken;
import de.antonkesy.jcalculator.tokenizer.token.PairToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public abstract class TokenMap implements ITokenMap {
    private final HashMap<String, IToken> token = new HashMap<>();
    private final List<PairToken> tokenPairs = new ArrayList<>();

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
        ArrayList<IToken> all = new ArrayList<>(token.values());
        all.addAll(tokenPairs.stream().map(p -> p.front).collect(Collectors.toCollection(ArrayList::new)));
        all.addAll(tokenPairs.stream().map(p -> p.end).collect(Collectors.toCollection(ArrayList::new)));
        return all;
    }

    @Override
    public Collection<PairToken> getPairs() {
        return tokenPairs;
    }

    @Override
    public int getFirstModifiablePriority() {
        return 2;
    }
}
