package com.antonkesy.jcalculator.tokenizer;

import com.antonkesy.jcalculator.token.Token;
import com.antonkesy.jcalculator.token.TypeRepresentation;
import com.antonkesy.jcalculator.token.exception.UnknownTokenException;
import com.antonkesy.jcalculator.token.operator.OperatorType;
import com.antonkesy.jcalculator.token.separator.SeparatorToken;
import com.antonkesy.jcalculator.token.separator.SeparatorType;
import com.antonkesy.jcalculator.token.value.constant.ConstantToken;
import com.antonkesy.jcalculator.token.value.constant.ConstantType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    public static List<Token> tokenize(String list) throws IOException, UnknownTokenException {
        ArrayList<Token> tokenList = new ArrayList<>();
        for (String stringToken : list.split(getAllSeparatorRegex())) {
            tokenList.add(getTokenFromString(stringToken));
        }
        return tokenList;
    }

    private static Token getTokenFromString(String stringToken) throws UnknownTokenException {
        return new SeparatorToken(SeparatorType.LEFT);
    }

    private static String enumRepresentations(TypeRepresentation[] enumValues) {
        StringBuilder allReps = new StringBuilder(enumValues.length);
        for (TypeRepresentation value : enumValues) {
            allReps.append(value.getTypeRepresentation());
        }
        return allReps.toString();
    }

    private static String getAllSeparateByChars() {
        return enumRepresentations(SeparatorType.values()) +
                enumRepresentations(ConstantType.values()) +
                enumRepresentations(OperatorType.values());
    }

    private static String getAllSeparatorRegex() {
        String allSeparator = getAllSeparateByChars();
        StringBuilder sepRegex = new StringBuilder(allSeparator.length() * 2 + 2);
        //start of regex option
        sepRegex.append('(');
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
}
