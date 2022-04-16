package de.antonkesy.jcalculator.tokenizer.token;

import de.antonkesy.jcalculator.number.INumber;

public class OperatorToken extends Token {
    public final Operation operation;

    public interface Operation {
        INumber operation(INumber left, INumber right) throws OperationException;

        class OperationException extends Exception {
        }
    }

    public OperatorToken(String representation, int priority, Operation operation) {
        super(representation, priority);
        this.operation = operation;
    }

}
