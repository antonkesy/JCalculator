package com.antonkesy.jcalculator.tokenizer;

import com.antonkesy.jcalculator.tokenizer.token.Token;
import com.antonkesy.jcalculator.tokenizer.token.TypeRepresentation;
import com.antonkesy.jcalculator.tokenizer.exception.UnknownTokenException;
import com.antonkesy.jcalculator.tokenizer.token.operator.OperatorToken;
import com.antonkesy.jcalculator.tokenizer.token.operator.OperatorType;
import com.antonkesy.jcalculator.tokenizer.token.separator.SeparatorType;
import com.antonkesy.jcalculator.tokenizer.token.value.ValueToken;
import com.antonkesy.jcalculator.tokenizer.token.value.constant.ConstantType;
import com.antonkesy.jcalculator.tokenizer.token.value.literal.LiteralToken;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class Tokenizer {
    private int tokenIndex = -1;
    private List<Token> token;

    public Tokenizer(String input) throws UnknownTokenException {
        tokenize(input);
    }

    public Tokenizer(List<Token> alreadyTokenized) {
        this.token = alreadyTokenized;
    }

    public List<Token> getToken() {
        return this.token;
    }

    private void tokenize(String input) throws UnknownTokenException {
        if (input.isEmpty()) throw new UnknownTokenException();
        token = new LinkedList<>();
        StringBuilder bufferedToken = new StringBuilder();
        Token lastAddedToken = null;
        Token lastPossibleToken = null;
        //TODO with next
        for (int i = 0; i < input.length(); ++i) {
            //skip spaces
            if (input.charAt(i) == ' ') continue;
            bufferedToken.append(input.charAt(i));
            Token currentPossibleToken = getTokenFromString(bufferedToken.toString(), lastAddedToken);
            //if current token is not possible then use last possible
            if (currentPossibleToken == null && lastPossibleToken != null) {
                lastAddedToken = lastPossibleToken;
                token.add(lastAddedToken);
                lastPossibleToken = null;
                bufferedToken.setLength(0);
                --i;
            } else {
                lastPossibleToken = currentPossibleToken;
            }
        }
        //add rest
        if (lastPossibleToken != null) {
            token.add(lastPossibleToken);
        } else {
            throw new UnknownTokenException();
        }
    }

    private Token getTokenFromString(String stringToken, Token lastToken) {
        Token token;
        //check token of types
        if ((token = getTokenOfType(stringToken)) != null)
            return token;
        //check literal token
        if (!(lastToken instanceof ValueToken) && (token = getLiteralToken(stringToken)) != null)
            return token;
        return null;
    }

    private Token getTokenOfType(String tokenString) {
        for (TypeRepresentation[] types : getAllTypes()) {
            for (TypeRepresentation option : types) {
                if (tokenString.matches(Pattern.quote(option.getTypeRepresentation()))) {
                    return option.createToken();
                }
            }
        }
        return null;
    }


    private TypeRepresentation[][] getAllTypes() {
        return new TypeRepresentation[][]{OperatorType.values(), ConstantType.values(), SeparatorType.values()};
    }

    private Token getLiteralToken(String tokenString) {
        if (tokenString.matches("([-+])*[0-9]+")) {
            return new LiteralToken(Integer.parseInt(tokenString));
        }
        return null;
    }

    public Token peek() {
        if (tokenIndex + 1 < token.size()) {
            return token.get(tokenIndex + 1);
        }
        return null;
    }

    public Token nextToken() {
        ++tokenIndex;
        return currentToken();
    }

    public Token currentToken() {
        if (isTokenIndexInBound()) return token.get(tokenIndex);
        else return null;
    }

    private boolean isTokenIndexInBound() {
        return tokenIndex < token.size();
    }
}
