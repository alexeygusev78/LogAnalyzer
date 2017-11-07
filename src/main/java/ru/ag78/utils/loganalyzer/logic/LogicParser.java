package ru.ag78.utils.loganalyzer.logic;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Predicate;

import org.apache.log4j.Logger;

import ru.ag78.utils.loganalyzer.Operation;
import ru.ag78.utils.loganalyzer.Token;
import ru.ag78.utils.loganalyzer.logic.sm.Element;

/**
 * Parser for logical expression.
 * @author u_m00y1
 *
 */
public class LogicParser {

    private static final Logger log = Logger.getLogger(LogicParser.class);

    private Queue<Token> tokens;
    private int level;

    /**
     * Default ctor
     */
    public LogicParser() {

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

        Deque<Predicate<String>> stack = new LinkedList<>();

        int level = 0;
        Element prev = null;
        Element cur = null;
        while ((cur = getNextElement(tokens, level)) != null) {
            log.debug(cur.toString());

            if (prev == null) {
                prev = cur;
                continue;
            } else if (cur.getOp() == Operation.OR) {
                stack.push(prev.getP());
                prev = cur;
            } else if (cur.getOp() == Operation.AND) {
                prev.and(cur.getP());
            }
        }

        // Объединить все элементы из стека по OR
        Predicate<String> p = applyByOr(stack);
        if (p != null) {
            p = prev.getP().or(p);
        } else {
            p = prev.getP();
        }

        return p;
    }

    private Predicate<String> applyByOr(Deque<Predicate<String>> stack) {

        Predicate<String> cur = null;
        Predicate<String> prev = null;

        while ((cur = stack.pollLast()) != null) {

            if (prev == null) {
                prev = cur;
                continue;
            }

            prev = prev.or(cur);
        }

        return prev;
    }

    public Element getNextElement(Queue<Token> tokens, int level) throws Exception {

        Element e = null;

        Token t = null;
        boolean negative = false;
        Operation op = Operation.AND;
        while ((t = tokens.poll()) != null) {
            log.debug(t.toString());

            if (t.getType() == Token.Type.NOT) {
                negative = !negative;
            }

            if (t.isOr()) {
                op = Operation.OR;
            }

            if (t.isValue()) {
                e = new Element(t.getValue(), negative);
                e.setOp(op);
                return e;
            }
        }

        return e;
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
