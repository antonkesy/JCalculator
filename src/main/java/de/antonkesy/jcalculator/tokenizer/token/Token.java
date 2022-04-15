package de.antonkesy.jcalculator.tokenizer.token;

public class Token implements IToken {
    public final String representation;
    public final int priority;

    public Token(String representation, int priorityLevel) {
        this.representation = representation;
        this.priority = priorityLevel;
    }

    @Override
    public String getTypeRepresentation() {
        return representation;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Token && ((Token) obj).representation.equals(representation);
    }
}
