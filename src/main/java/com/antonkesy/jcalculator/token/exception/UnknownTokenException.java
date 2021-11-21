package com.antonkesy.jcalculator.token.exception;

import java.io.IOException;

public class UnknownTokenException extends IOException {

    public UnknownTokenException() {
        super();
    }

    public UnknownTokenException(String stringToken) {
        super(stringToken);
    }
}
