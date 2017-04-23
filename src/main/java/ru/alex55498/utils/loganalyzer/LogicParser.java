package ru.alex55498.utils.loganalyzer;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Logic expressions parsers
 * @author U_M00Y1
 *
 */
public class LogicParser {

    private Deque<Predicate<String>> stack = new LinkedList<>();
    private final String pattern = "AND|OR|NOT|\\\"[\\w\\s\\.]+\\\"|\\(|\\)"; // "AND|OR|NOT|\\\"[а-яА-Яa-zA-Z0-9_\\s\\.]+\\\"|\\(|\\)"

    /**
     * Parse to tokens logic expression.
     * @param expr
     * @return
     * @throws Exception
     */
    public Queue<String> toTokens(String expr) throws Exception {

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(expr);

        LinkedList<String> tokens = new LinkedList<>();
        while (m.find()) {
            tokens.add(m.group());
        }

        return tokens;
    }

    /**
     * Constructs logic predicate for supplied expression.
     * В стек пушим предикаты, разделенные по OR. 
     * Предикаты, разделенные по AND объединяем на лету.
     * При открытии скобки уходим в рекурсию.
     * @param tokens
     * @return
     * @throws Exception
     */
    public PredicateWrapper makePredicate(Queue<String> tokens) throws Exception {

        Deque<PredicateWrapper> stack = new LinkedList<>();

        PredicateWrapper cur = null;
        PredicateWrapper pw = null;
        while ((pw = constructPredicate(tokens)) != null) {
            if (pw.isEmpty()) {
                continue;
            }
            System.out.println("pw=" + pw.toString());

            if (cur == null) {
                cur = pw;
                continue;
            }

            if (pw.getOperation().isOr()) {
                stack.push(cur);
                cur = pw;
            } else {
                cur.and(pw);
            }
        }

        try {
            while ((pw = stack.pop()) != null) {
                cur.or(pw);
            }
        } catch (Exception e) {
        }

        return cur;
    }

    public PredicateWrapper constructPredicate(Queue<String> tokens) throws Exception {

        PredicateWrapper pw = new PredicateWrapper();
        String token = null;
        while ((token = tokens.poll()) != null) {
            System.out.println("token=" + token);

            if (this.isAnd(token)) {
                pw.setOperation(Operation.AND);
            } else if (this.isOr(token)) {
                pw.setOperation(Operation.OR);
            } else if (this.isNot(token)) {
                pw.setNot(true);
            } else if (this.isElement(token)) {
                pw.setExpr(token);
                return pw;
            } else if (this.isOpenParentheses(token)) {
                boolean isNot = pw.isNot();
                Operation op = pw.getOperation();
                pw = makePredicate(tokens);
                pw.setNot(isNot);
                pw.setOperation(op);
                return pw;
            } else if (this.isCloseParentheses(token)) {
                return new PredicateWrapper();
            }
        }

        return null;
    }

    public Predicate<String> attachAnd(Predicate<String> p, String expr) {

        Predicate<String> newP = x -> x.contains(expr);

        if (p == null) {
            p = newP;
        } else {
            p.and(newP);
        }

        return p;
    }

    public boolean isLogicToken(String token) {

        switch (token) {
        case "AND":
        case "OR":
        case "NOT":
            return true;
        }

        return false;
    }

    public boolean isAnd(String token) {

        return token.equals("AND");
    }

    public boolean isOr(String token) {

        return token.equals("OR");
    }

    public boolean isNot(String token) {

        return token.equals("NOT");
    }

    public boolean isOpenParentheses(String token) {

        return token.equals("(");
    }

    public boolean isCloseParentheses(String token) {

        return token.equals(")");
    }

    public boolean isParentheses(String token) {

        return isOpenParentheses(token) || isCloseParentheses(token);
    }

    public boolean isElement(String token) {

        return !(isLogicToken(token) || isParentheses(token));
    }
}
