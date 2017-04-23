package ru.alex55498.utils.loganalyzer;

import java.util.function.Predicate;

public class PredicateWrapper {

    private Operation operation = Operation.AND;
    private String expr;
    private boolean isNot = false;
    private Predicate<String> predicate;
    private StringBuilder description = new StringBuilder();

    private boolean isEmpty = true;

    /**
     * Default ctor
     */
    public PredicateWrapper() {

    }

    /**
     * Ctor with parameters
     * @param operation
     * @param expr
     * @param isNot
     */
    public PredicateWrapper(Operation operation, String expr, boolean isNot) {
        super();
        this.operation = operation;
        this.expr = expr;
        this.isNot = isNot;
    }

    @Override
    public String toString() {

        return description.toString();
    }

    private String makeInitialDescription() {

        return ((isNot) ? "NOT " : "") + expr;
    }

    protected Operation getOperation() {

        return operation;
    }

    protected void setOperation(Operation operation) {

        this.operation = operation;
        isEmpty = false;
    }

    protected String getExpr() {

        return expr;
    }

    protected void setExpr(String expr) {

        this.expr = expr.replaceAll("\\\"", "").toLowerCase().trim();
        description = new StringBuilder(makeInitialDescription());
        isEmpty = false;
    }

    protected boolean isNot() {

        return isNot;
    }

    protected void setNot(boolean isNot) {

        this.isNot = isNot;
        description = new StringBuilder(makeInitialDescription());
        isEmpty = false;
    }

    protected Predicate<String> getPredicate() {

        if (predicate == null) {
            predicate = x -> x.toLowerCase().contains(expr);
            if (isNot()) {
                predicate = predicate.negate();
            }
        }

        return predicate;
    }

    protected void setPredicate(Predicate<String> predicate) {

        this.predicate = predicate;
    }

    public PredicateWrapper and(PredicateWrapper pw) {

        Predicate<String> p = getPredicate();
        predicate = p.and(pw.getPredicate());

        description.append(" AND ").append(pw.toString());

        return this;
    }

    public PredicateWrapper or(PredicateWrapper pw) {

        Predicate<String> p = getPredicate();
        predicate = p.or(pw.getPredicate());

        description.append(" OR (").append(pw.toString()).append(")");

        return this;
    }

    public boolean isEmpty() {

        return isEmpty;
    }
}
