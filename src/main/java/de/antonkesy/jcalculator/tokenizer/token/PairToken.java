package de.antonkesy.jcalculator.tokenizer.token;

public class PairToken extends Token {
    public final PairElementToken front, end;

    public PairToken(PairElementToken front, PairElementToken end) {
        super(front.getTypeRepresentation() + end.getTypeRepresentation(), 1);
        this.front = front;
        this.end = end;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PairToken && ((PairToken) obj).front.equals(front) && ((PairToken) obj).end.equals(end);
    }
}
