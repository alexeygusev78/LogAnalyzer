package ru.ag78.utils.loganalyzer.logic;

import java.util.function.Predicate;

/**
 * Extended implementation of Predicate interface.
 * @author u_m00y1
 *
 */
public class MyPredicate implements Predicate<String> {

    private String value = "";
    private Predicate<String> predicate = new Predicate<String>() {

        @Override
        public boolean test(String t) {

            return t.contains(value);
        }
    };

    /**
     * Default ctor
     */
    public MyPredicate() {

    }

    /**
     * Ctor with parameters
     * @param value
     */
    public MyPredicate(String value) {

        setValue(value);
    }

    public void setValue(String value) {

        this.value = value;
    }

    @Override
    public boolean test(String t) {

        return predicate.test(t);
    }
}
