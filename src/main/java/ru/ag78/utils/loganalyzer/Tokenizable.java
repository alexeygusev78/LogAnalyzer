package ru.ag78.utils.loganalyzer;

import java.util.Queue;

/**
 * An interface for Tokenizer functionality.
 * @author Алексей
 *
 */
public interface Tokenizable {

    public Queue<String> toTokens(String expr) throws Exception;
}
