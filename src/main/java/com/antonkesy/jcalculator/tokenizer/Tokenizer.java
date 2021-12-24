package com.antonkesy.jcalculator.tokenizer;

import com.antonkesy.jcalculator.tokenizer.token.Token;
import com.antonkesy.jcalculator.tokenizer.token.TypeRepresentation;
import com.antonkesy.jcalculator.tokenizer.exception.UnknownTokenException;
import com.antonkesy.jcalculator.tokenizer.token.operator.OperatorToken;
import com.antonkesy.jcalculator.tokenizer.token.operator.OperatorType;
import com.antonkesy.jcalculator.tokenizer.token.separator.SeparatorToken;
import com.antonkesy.jcalculator.tokenizer.token.separator.SeparatorType;
import com.antonkesy.jcalculator.tokenizer.token.value.ValueToken;
import com.antonkesy.jcalculator.tokenizer.token.value.constant.ConstantType;
import com.antonkesy.jcalculator.tokenizer.token.value.literal.LiteralToken;

import java.math.BigDecimal;
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
        //remove spaces from input
        input = input.replace(" ", "");

        token = new LinkedList<>();
        StringBuilder bufferedToken = new StringBuilder();
        Token lastAddedToken = null;
        Token lastPossibleToken = null;
        int inputIndex = 0;
        while (inputIndex < input.length()) {
            bufferedToken.append(input.charAt(inputIndex));
            Token currentPossibleToken = getTokenFromString(bufferedToken.toString(), lastAddedToken);
            //if current token is not possible then use last possible or continue trying
            if (currentPossibleToken == null && lastPossibleToken != null) {
                if (needToAddMultiplyBetweenLiteralAndParentheses(lastAddedToken, lastPossibleToken)) {
                    token.add(new OperatorToken(OperatorType.MULTIPLY));
                }
                lastAddedToken = lastPossibleToken;
                lastPossibleToken = null;
                token.add(lastAddedToken);
                bufferedToken.setLength(0);
                --inputIndex;
            } else {
                lastPossibleToken = currentPossibleToken;
            }
            ++inputIndex;
        }
        //add rest when input is completely read
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
        //check signed literal token
        //TODO check if last was parentheses -> add multiply
        if (nextCouldBeSignedLiteralToken(lastToken) && (token = getSignedLiteralToken(stringToken)) != null)
            return token;
        //check unsigned literal token
        if ((token = getLiteralToken(stringToken)) != null)
            return token;
        return null;
    }

    private Token getTokenOfType(String tokenString) {
        for (TypeRepresentation[] types : getAllTypes()) {
            for (TypeRepresentation option : types) {
                //TODO check if last was literal -> add multiply when next is parentheses
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
        if (tokenString.matches("[0-9]+(\\.[0-9]*)?")) {
            return new LiteralToken(new BigDecimal(tokenString));
        }
        return null;
    }

    private Token getSignedLiteralToken(String tokenString) {
        if (tokenString.matches("([-+])?[0-9]+(\\.[0-9]*)?")) {
            return new LiteralToken(new BigDecimal(tokenString));
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

    /**
     * signed literals can't be after ')' or other literals
     */
    private boolean nextCouldBeSignedLiteralToken(Token lastToken) {
        return !(lastToken instanceof ValueToken || lastToken instanceof SeparatorToken && ((SeparatorToken) lastToken).separatorType == SeparatorType.CLOSE);
    }

    private boolean needToAddMultiplyBetweenLiteralAndParentheses(Token lastToken, Token nextToken) {
        return
                //last token was literal and next is open parentheses
                (lastToken instanceof ValueToken && nextToken instanceof SeparatorToken && ((SeparatorToken) nextToken).separatorType == SeparatorType.OPEN)
                        //last was closing parentheses and next is literal
                        || (lastToken instanceof SeparatorToken && ((SeparatorToken) lastToken).separatorType == SeparatorType.CLOSE && nextToken instanceof ValueToken)
                ;
    }
}
