package ru.alex55498.utils.loganalyzer;

public enum Operation {

    AND, OR, NOT;

    public boolean isAnd() {

        return this.toString().equalsIgnoreCase("AND");
    }

    public boolean isOr() {

        return this.toString().equalsIgnoreCase("OR");
    }

    public boolean isNot() {

        return this.toString().equalsIgnoreCase("NOT");
    }
}
