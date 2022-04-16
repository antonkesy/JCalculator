package de.antonkesy.jcalculator.tokenizer;

import de.antonkesy.jcalculator.number.INumberFactory;
import de.antonkesy.jcalculator.tokenizer.exception.UnknownTokenException;
import de.antonkesy.jcalculator.tokenizer.token.IToken;
import de.antonkesy.jcalculator.tokenizer.token.map.ITokenMap;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class Tokenizer {
    private int tokenIndex = -1;
    private List<IToken> token;
    private final ITokenMap tokenMap;

    public Tokenizer(String input, ITokenMap tokenMap) throws UnknownTokenException {
        this.tokenMap = tokenMap;
        tokenize(input);
    }

    public Tokenizer(List<IToken> alreadyTokenized, ITokenMap tokenMap) {
        this.token = alreadyTokenized;
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
        lastAddedToken = lastPossibleToken;
        token.add(lastAddedToken);
        return lastAddedToken;
    }

    private IToken getTokenFromString(String stringToken, IToken lastToken) {
        IToken token;
        if ((token = getTokenOfType(stringToken)) != null) return token;
        if ((token = tokenMap.parseLiteralToken(stringToken, lastToken)) != null) return token;
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
        return null;
    }

    private boolean isTokenIndexInBound() {
        return tokenIndex < token.size();
    }
}
