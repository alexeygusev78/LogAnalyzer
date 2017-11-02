package ru.ag78.utils.loganalyzer.logic.sm;

import java.text.ParseException;

import ru.ag78.utils.loganalyzer.Token;
import ru.ag78.utils.loganalyzer.logic.LogicParser;

public class StateString extends State {

    private String value;

    public StateString(LogicParser context, String value) {

        super(context);
        this.value = value;
    }

    @Override
    public State next() throws ParseException {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected boolean check(Token t) {

        // TODO Auto-generated method stub
        return false;
    }
}
