package com.antonkesy.jcalculator.tokenizer;

import com.antonkesy.jcalculator.token.Token;
import com.antonkesy.jcalculator.token.TypeRepresentation;
import com.antonkesy.jcalculator.token.exception.UnknownTokenException;
import com.antonkesy.jcalculator.token.operator.OperatorType;
import com.antonkesy.jcalculator.token.separator.SeparatorType;
import com.antonkesy.jcalculator.token.value.constant.ConstantType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Tokenizer {
    public static List<Token> tokenize(String list) throws IOException {
        ArrayList<Token> tokenList = new ArrayList<>();
        for (String stringToken : list.split(getAllSeparatorRegex())) {
            tokenList.add(getTokenFromString(stringToken));
        }
        return tokenList;
    }

    public static Token getTokenFromString(String stringToken) throws UnknownTokenException {
        Token token;
        token = getTokenOfType(stringToken);
        if (token != null) return token;
        //TODO check for literal numeric token
        throw new UnknownTokenException();
    }

    public static String enumRepresentations(TypeRepresentation[] enumValues) {
        StringBuilder allReps = new StringBuilder(enumValues.length);
        for (TypeRepresentation value : enumValues) {
            allReps.append(value.getTypeRepresentation()).append(' ');
        }
        //remove last space
        allReps.deleteCharAt(allReps.length() - 1);
        return allReps.toString();
    }

    private static String getAllSeparateByChars() {
        return enumRepresentations(SeparatorType.values()) +
                enumRepresentations(ConstantType.values()) +
                enumRepresentations(OperatorType.values());
    }

    private static String getAllSeparatorRegex() {
        String allSeparator = getAllSeparateByChars();
        StringBuilder sepRegex = new StringBuilder();
        //start of regex option
        sepRegex.append('(');
        sepRegex.append("\\s|");
        //add every char as regex option
        for (char c : allSeparator.toCharArray()) {
            sepRegex.append(c).append('|');
        }
        //remove last appended '|'
        sepRegex.deleteCharAt(sepRegex.length() - 1);
        //end of regex option
        sepRegex.append(')');
        return sepRegex.toString();
    }

    /**
     * @param optionString split by space all options regex should use
     * @return "(optionA|optionB|optionN)"
     */
    public static String getOptionRegex(String optionString) {
        StringBuilder regex = new StringBuilder();
        //add every char as regex option
        regex.append('(');
        for (String s : optionString.split("\\s")) {
            regex.append(s).append('|');
        }
        //remove last appended '|'
        regex.deleteCharAt(regex.length() - 1);
        //end of regex option
        regex.append(')');
        return regex.toString();
    }

    public static Token getTokenOfType(String tokenString) {
        for (TypeRepresentation[] types : getAllTypes()) {
            for (TypeRepresentation option : types) {
                if (tokenString.matches(Pattern.quote(option.getTypeRepresentation()))) {
                    return option.createToken();
                }
            }
        }
        return null;
    }

    public static TypeRepresentation[][] getAllTypes() {
        return new TypeRepresentation[][]{OperatorType.values(), ConstantType.values(), SeparatorType.values()};
    }
}
