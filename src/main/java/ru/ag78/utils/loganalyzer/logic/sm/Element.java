package ru.ag78.utils.loganalyzer.logic.sm;

import java.util.function.Predicate;

import ru.ag78.utils.loganalyzer.Operation;
import ru.ag78.utils.loganalyzer.logic.MyPredicate;

public class Element {

    private Predicate<String> p;
    private Operation op = Operation.AND;
    private String value = "";
    private boolean negative = false;

    public Element() {

    }

    public Element(String value) {

        p = new MyPredicate(value);
        this.value = value;
    }

    public Element(String value, boolean negative) {

        this(value);
        this.negative = negative;
        if (negative) {
            p = p.negate();
        }
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("Element ");
        sb.append(op.toString()).append(" ");
        if (negative) {
            sb.append("NOT ");
        }
        sb.append("'" + value + "'");

        return sb.toString();
    }

    public Predicate<String> getP() {

        return p;
    }

    public Predicate<String> and(Predicate<String> nextP) {

        p = p.and(nextP);
        return p;
    }

    public Predicate<String> or(Predicate<String> nextP) {

        p = p.or(nextP);
        return p;
    }

    public Operation getOp() {

        return op;
    }

    public void setOp(Operation op) {

        this.op = op;
    }
}
