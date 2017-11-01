package ru.ag78.utils.loganalyzer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tokenizer for custom RegEx pattern.
 * @author Алексей
 *
 */
public class TokenizerCustom implements Tokenizable {

    private String pattern;

    /**
     * Ctor with parameters
     * @param pattern
     */
    public TokenizerCustom(String pattern) {

        super();
        this.pattern = pattern;
    }

    @Override
    public Queue<String> toTokens(String expr) throws Exception {

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(expr);

        LinkedList<String> tokens = new LinkedList<>();
        while (m.find()) {
            tokens.add(m.group());
        }

        return tokens;
    }

    @Override
    public Queue<Token> toTokens2(String expr) throws Exception {

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(expr);

        LinkedList<Token> tokens = new LinkedList<>();
        while (m.find()) {
            String value = m.group();
            Token t = new Token(value);
            tokens.add(t);
        }

        return tokens;
    }
}
