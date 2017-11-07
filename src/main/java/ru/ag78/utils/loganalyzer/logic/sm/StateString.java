package ru.ag78.utils.loganalyzer.logic.sm;

import java.text.ParseException;
import java.util.function.Predicate;

import ru.ag78.utils.loganalyzer.Token;
import ru.ag78.utils.loganalyzer.logic.LogicParser;
import ru.ag78.utils.loganalyzer.logic.MyPredicate;

public class StateString extends State {

    private String value;
    private Predicate p;

    public StateString(LogicParser context, String value) {

        super(context);
        this.value = value;
        p = new MyPredicate(value);
    }

    public StateString(LogicParser context, String value, boolean negative) {

        this(context, value);
        if (negative) {
            p = p.negate();
        }
    }

    @Override
    public State next(Token t) throws ParseException {

        if (t == null) {
            return null;
        }

        return null;
    }

    @Override
    protected boolean check(Token t) {

        // TODO Auto-generated method stub
        return false;
    }
}
