package ru.ag78.utils.loganalyzer.logic;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Predicate;

import ru.ag78.utils.loganalyzer.Token;

/**
 * Parser for logical expression.
 * @author u_m00y1
 *
 */
public class LogicParser {

    private Queue<Token> tokens;
    private Deque<MyPredicate> stack = new LinkedList<>();
    private int level;

    /**
     * Default ctor
     */
    public LogicParser() {

    }

    public MyPredicate push(MyPredicate p) {

        stack.push(p);

        return p;
    }

    /**
     * Safely pop element from stack.
     * @return null if stack is empty.
     */
    public MyPredicate pop() {

        return stack.pollFirst(); // silent (safe) analog of pop or removeFirst.
    }

    /**
     * Safely returns next token from the token list
     * @return
     */
    public Token getNextToken() {

        return tokens.poll();
    }

    public Predicate<String> parse(Queue<Token> tokens) throws Exception {

        this.tokens = tokens;
        Predicate<String> prev = null;
        Predicate<String> p = null;

        while ((p = getNextPredicate(tokens, 0)) != null) {
            if (prev != null) {
                p = prev.or(p);
            }

            prev = p;
        }

        return prev;
    }

    public Predicate<String> getNextPredicate(Queue<Token> tokens, int level) throws Exception {

        MyPredicate mp = null;

        Token t = null;
        boolean negate = false;
        while ((t = tokens.poll()) != null) {
            if (mp == null) {
                mp = new MyPredicate();
            }

            if (t.isNot()) {
                negate = !negate;
                continue;
            }
            if (t.isValue()) {
                mp.setValue(t.getValue());
            }
        }

        if (mp == null) {
            return null;
        }

        Predicate<String> p = mp;
        if (negate) {
            p = p.negate();
        }

        return p;
    }
}
