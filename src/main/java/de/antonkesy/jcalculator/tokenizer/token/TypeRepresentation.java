package de.antonkesy.jcalculator.tokenizer.token;

public interface TypeRepresentation {
    String getTypeRepresentation();

    Token createToken();
}
