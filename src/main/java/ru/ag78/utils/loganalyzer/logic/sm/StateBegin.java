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
    public State next() throws ParseException {

        boolean negate = false;
        while (true) {
            Token t = getNextSymbol();
            if (t == null) {
                return null;
            }

            if (Token.Type.NOT == t.getType()) {
                negate = !negate;
                continue;
            }

            if (Token.Type.STRING == t.getType()) {
                return new StateString(getContext(), t.getValue());
            }

            throw new ParseException("Unsupported token: " + t.toString(), t.getIndex());
        }
    }

    @Override
    protected boolean check(Token t) {

        return Tokenizable.isInType(t.getType(), Type.STRING, Type.OPEN_BR, Type.NOT);
    }
}
