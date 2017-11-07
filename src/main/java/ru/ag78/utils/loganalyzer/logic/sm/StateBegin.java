package ru.ag78.utils.loganalyzer.logic.sm;

import java.text.ParseException;

import ru.ag78.utils.loganalyzer.Token;
import ru.ag78.utils.loganalyzer.Token.Type;
import ru.ag78.utils.loganalyzer.Tokenizable;
import ru.ag78.utils.loganalyzer.logic.LogicParser;

public class StateBegin extends State {

    public StateBegin(LogicParser context) {

        super(context);
    }

    @Override
    public State next(Token t) throws ParseException {

        boolean negative = false;
        while (true) {
            if (t == null) {
                return null;
            }

            if (Token.Type.NOT == t.getType()) {
                negative = !negative;
                continue;
            }

            if (Token.Type.STRING == t.getType()) {
                return new StateString(getContext(), t.getValue(), negative);
            }

            throw new ParseException("Unsupported token: " + t.toString(), t.getIndex());
        }
    }

    @Override
    protected boolean check(Token t) {

        return Tokenizable.isInType(t.getType(), Type.STRING, Type.OPEN_BR, Type.NOT);
    }
}
