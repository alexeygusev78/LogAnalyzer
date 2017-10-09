package ru.ag78.utils.loganalyzer;

public class StateMachine {

    public static class State {

        public final int EXPR = 1;
        public final int OPEN_P = 2;
        public final int CLOESE_P = 3;
        public final int AND = 4;
        public final int OR = 5;
        public final int END = 6;
    }
}
