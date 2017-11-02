package ru.ag78.utils.loganalyzer.logic;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Predicate;

import ru.ag78.utils.loganalyzer.Token;
import ru.ag78.utils.loganalyzer.logic.sm.State;
import ru.ag78.utils.loganalyzer.logic.sm.StateBegin;

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
        Predicate<String> p = s -> true;

        State state = new StateBegin(this);
        while ((state = state.next()) != null) {

        }

        return p;
    }
}
