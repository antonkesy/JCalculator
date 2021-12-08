package com.antonkesy.jcalculator.tokenizer.exception;

import java.io.IOException;

public class UnknownTokenException extends IOException {

    public UnknownTokenException() {
        super();
    }

    public UnknownTokenException(String stringToken) {
        super(stringToken);
    }
}
