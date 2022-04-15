package de.antonkesy.jcalculator.tokenizer;

import de.antonkesy.jcalculator.number.INumberFactory;
import de.antonkesy.jcalculator.tokenizer.exception.UnknownTokenException;
import de.antonkesy.jcalculator.tokenizer.token.IToken;
import de.antonkesy.jcalculator.tokenizer.token.TypeRepresentation;
import de.antonkesy.jcalculator.tokenizer.token.ValueToken;
import de.antonkesy.jcalculator.tokenizer.token.map.ITokenMap;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class Tokenizer {
    private int tokenIndex = -1;
    private List<IToken> token;
    private final INumberFactory numberFactory;
    private final ITokenMap tokenMap;

    public Tokenizer(String input, INumberFactory numberFactory, ITokenMap tokenMap) throws UnknownTokenException {
        this.numberFactory = numberFactory;
        this.tokenMap = tokenMap;
        tokenize(input);
    }

    public Tokenizer(List<IToken> alreadyTokenized, INumberFactory numberFactory, ITokenMap tokenMap) {
        this.token = alreadyTokenized;
        this.numberFactory = numberFactory;
        this.tokenMap = tokenMap;
    }

    public List<IToken> getToken() {
        return this.token;
    }

    private void tokenize(String input) throws UnknownTokenException {
        if (input.isEmpty()) throw new UnknownTokenException();
        //remove spaces from input
        input = input.replace(" ", "");

        token = new LinkedList<>();
        StringBuilder bufferedToken = new StringBuilder();
        IToken lastAddedToken = null;
        IToken lastPossibleToken = null;
        int inputIndex = 0;
        while (inputIndex < input.length()) {
            bufferedToken.append(input.charAt(inputIndex));
            IToken currentPossibleToken = getTokenFromString(bufferedToken.toString(), lastAddedToken);
            //if current token is not possible then use last possible or continue trying
            if (currentPossibleToken == null && lastPossibleToken != null) {
                lastAddedToken = addToken(lastAddedToken, lastPossibleToken);
                //reset to tokenize buffer
                lastPossibleToken = null;
                bufferedToken.setLength(0);
                --inputIndex;
            } else {
                lastPossibleToken = currentPossibleToken;
            }
            ++inputIndex;
        }
        //add rest when input is completely read
        if (lastPossibleToken != null) {
            addToken(lastAddedToken, lastPossibleToken);
        } else {
            throw new UnknownTokenException();
        }
    }

    private IToken addToken(IToken lastAddedToken, IToken lastPossibleToken) {
        /*
        if (needToAddMultiplyBetweenLiteralAndParentheses(lastAddedToken, lastPossibleToken)) {
            token.add(new Token(OperatorType.MULTIPLY));
        }
        */
        lastAddedToken = lastPossibleToken;
        token.add(lastAddedToken);
        return lastAddedToken;
    }

    private IToken getTokenFromString(String stringToken, IToken lastToken) {
        IToken token;
        //check token of types
        if ((token = getTokenOfType(stringToken)) != null) return token;
        //check signed literal token
        if (nextCouldBeSignedLiteralToken(lastToken) && (token = getSignedLiteralToken(stringToken)) != null)
            return token;
        //check unsigned literal token
        if ((token = getLiteralToken(stringToken)) != null) return token;
        return null;
    }

    private IToken getTokenOfType(String tokenString) {
        for (IToken type : tokenMap.getAll()) {
            if (tokenString.matches(Pattern.quote(type.getTypeRepresentation()))) {
                return type;
            }
        }
        return null;
    }


    private IToken getLiteralToken(String tokenString) {
        if (tokenString.matches("\\d+(\\.\\d*)?")) {
            return new ValueToken(numberFactory.getNumber(tokenString));
        }
        return null;
    }

    private IToken getSignedLiteralToken(String tokenString) {
        if (tokenString.matches("([-+])?\\d+(\\.\\d*)?")) {
            return new ValueToken(numberFactory.getNumber(tokenString));
        }
        return null;
    }

    public IToken peek() {
        if (tokenIndex + 1 < token.size()) {
            return token.get(tokenIndex + 1);
        }
        return null;
    }

    public IToken nextToken() {
        ++tokenIndex;
        return currentToken();
    }

    public IToken currentToken() {
        if (isTokenIndexInBound()) return token.get(tokenIndex);
        else return null;
    }

    private boolean isTokenIndexInBound() {
        return tokenIndex < token.size();
    }

    /*
     * signed literals can't be after ')' or other literals
     */
    private boolean nextCouldBeSignedLiteralToken(IToken lastToken) {
        //return !(lastToken instanceof ValueToken || lastToken instanceof SeparatorToken && ((SeparatorToken) lastToken).separatorType == SeparatorType.CLOSE);
        return !(lastToken instanceof ValueToken);
    }

    /*
    private boolean needToAddMultiplyBetweenLiteralAndParentheses(IToken last, IToken next) {
        return needMultiplyBetweenLiteralParentheses(last, next) || needMultiplyBetweenLiteralAndConstant(last, next);
    }
    */

    /*
    private boolean needMultiplyBetweenLiteralParentheses(IToken last, IToken next) {
        return
                //last token was literal and next is open parentheses
                (last instanceof ValueToken && next instanceof SeparatorToken && ((SeparatorToken) next).separatorType == SeparatorType.OPEN)
                        //last was closing parentheses and next is literal
                        || (last instanceof SeparatorToken && ((SeparatorToken) last).separatorType == SeparatorType.CLOSE && next instanceof ValueToken);
    }

    private boolean needMultiplyBetweenLiteralAndConstant(IToken last, IToken next) {
        return (last instanceof LiteralToken && next instanceof ConstantToken) || (last instanceof ConstantToken && next instanceof LiteralToken);
    }

     */
}
