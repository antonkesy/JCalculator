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

public class Tokenizer {
    public static List<Token> tokenize(String list) throws IOException, UnknownTokenException {
        String[] splitList = list.split("[" + getAllSeparateByChars() + "]");
        return new ArrayList<>();
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
}
