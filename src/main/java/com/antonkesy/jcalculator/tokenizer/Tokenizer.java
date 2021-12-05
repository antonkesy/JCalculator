package com.antonkesy.jcalculator.tokenizer;

import com.antonkesy.jcalculator.tokenizer.token.Token;
import com.antonkesy.jcalculator.tokenizer.token.TypeRepresentation;
import com.antonkesy.jcalculator.tokenizer.token.exception.UnknownTokenException;
import com.antonkesy.jcalculator.tokenizer.token.operator.OperatorType;
import com.antonkesy.jcalculator.tokenizer.token.separator.SeparatorType;
import com.antonkesy.jcalculator.tokenizer.token.value.constant.ConstantType;
import com.antonkesy.jcalculator.tokenizer.token.value.literal.LiteralToken;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Tokenizer {
    private int tokenIndex = -1;
    private final List<Token> token;

    public Tokenizer(String input) throws UnknownTokenException {
        token = tokenize(input);
    }

    public Tokenizer(List<Token> alreadyTokenized) {
        this.token = alreadyTokenized;
    }

    protected static List<Token> tokenize(String input) throws UnknownTokenException {
        if (input.isEmpty()) throw new UnknownTokenException();
        ArrayList<Token> tokenList = new ArrayList<>();
        //split input token input by delimiters
        for (String stringToken : addSpacesDelimiter(input).split("\\s")) {
            //skip empty or only space token
            if (stringToken.isEmpty() || stringToken.matches("\\s*")) continue;
            //add token if exists
            tokenList.add(getTokenFromString(stringToken));
        }
        if (tokenList.isEmpty()) throw new UnknownTokenException();
        return tokenList;
    }

    protected static Token getTokenFromString(String stringToken) throws UnknownTokenException {
        Token token;
        //check token of types
        token = getTokenOfType(stringToken);
        if (token != null) return token;
        //check literal token
        token = getLiteralToken(stringToken);
        if (token != null) return token;
        throw new UnknownTokenException(stringToken);
    }

    private static String enumRepresentations(TypeRepresentation[] enumValues) {
        StringBuilder allReps = new StringBuilder(enumValues.length);
        for (TypeRepresentation value : enumValues) {
            allReps.append(value.getTypeRepresentation()).append(' ');
        }
        //remove last space
        allReps.deleteCharAt(allReps.length() - 1);
        return allReps.toString();
    }

    private static String getAllSeparateByChars() {
        return enumRepresentations(SeparatorType.values()) + ' ' +
                enumRepresentations(ConstantType.values()) + ' ' +
                enumRepresentations(OperatorType.values()) + "  ";
    }

    /**
     * @param optionString split by space all options regex should use
     * @return "(optionA|optionB|optionN)"
     */
    protected static String getOptionRegex(String optionString) {
        StringBuilder regex = new StringBuilder();
        //add every char as regex option
        regex.append('(');
        for (String s : optionString.split("\\s")) {
            regex.append(Pattern.quote(s)).append('|');
        }
        //remove last appended '|'
        regex.deleteCharAt(regex.length() - 1);
        //end of regex option
        regex.append(')');
        return regex.toString();
    }

    protected static Token getTokenOfType(String tokenString) {
        for (TypeRepresentation[] types : getAllTypes()) {
            for (TypeRepresentation option : types) {
                if (tokenString.matches(Pattern.quote(option.getTypeRepresentation()))) {
                    return option.createToken();
                }
            }
        }
        return null;
    }

    protected static TypeRepresentation[][] getAllTypes() {
        return new TypeRepresentation[][]{OperatorType.values(), ConstantType.values(), SeparatorType.values()};
    }

    protected static Token getLiteralToken(String tokenString) {
        if (tokenString.matches("([-+])*[0-9]+")) {
            return new LiteralToken(Integer.parseInt(tokenString));
        }

        return null;
    }

    protected static String addSpacesDelimiter(String input) {
        String[] delimiters = getAllSeparateByChars().split("\\s");
        for (String delimiter : delimiters) {
            input = input.replace(delimiter, " " + delimiter + " ");
        }
        return input;
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
        if (isTokenIndexInBound())
            return token.get(tokenIndex);
        else return null;
    }

    private boolean isTokenIndexInBound() {
        return tokenIndex < token.size();
    }
}
