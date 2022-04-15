package de.antonkesy.jcalculator.tokenizer.token;

public class PairToken extends Token {
    public final IToken front, end;

    public PairToken(IToken front, IToken end) {
        super(front.getTypeRepresentation() + end.getTypeRepresentation(), front.getPriority());
        this.front = front;
        this.end = end;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PairToken && ((PairToken) obj).front.equals(front) && ((PairToken) obj).end.equals(end);
    }
}
