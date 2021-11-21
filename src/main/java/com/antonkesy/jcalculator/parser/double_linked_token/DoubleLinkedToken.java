package com.antonkesy.jcalculator.parser.double_linked_token;

import com.antonkesy.jcalculator.tokenizer.token.Token;

public class DoubleLinkedToken {
    public final Token value;
    public DoubleLinkedToken before, after;

    public DoubleLinkedToken(Token value, DoubleLinkedToken before) {
        this.value = value;
        this.before = before;
    }
}
