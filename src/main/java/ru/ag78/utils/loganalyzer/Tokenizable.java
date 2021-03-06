package ru.ag78.utils.loganalyzer;

import java.util.Arrays;
import java.util.Queue;

/**
 * An interface for Tokenizer functionality.
 * @author Алексей
 *
 */
public interface Tokenizable {

    public Queue<String> toTokens(String expr) throws Exception;

    public Queue<Token> toTokens2(String expr) throws Exception;

    public static boolean isInType(Token.Type typeToCheck, Token.Type... types) {

        return Arrays.asList(types).contains(typeToCheck);
    }
}
