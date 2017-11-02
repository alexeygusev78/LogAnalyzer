package ru.ag78.utils.loganalyzer.logic.sm;

import java.text.ParseException;

import ru.ag78.utils.loganalyzer.Token;
import ru.ag78.utils.loganalyzer.logic.LogicParser;

public abstract class State {

    private LogicParser context;

    public State(LogicParser context) {

        super();
        this.context = context;
    }

    public LogicParser getContext() {

        return context;
    }

    public void setContext(LogicParser context) {

        this.context = context;
    }

    protected Token getNextSymbol() throws ParseException {

        Token t = context.getNextToken();
        if (t == null) {
            return null;
        }

        if (!check(t)) {
            throw new ParseException("Unexpected token: " + t.toString(), t.getIndex());
        }

        return t;
    }

    abstract public State next() throws ParseException;

    abstract protected boolean check(Token t);
}
