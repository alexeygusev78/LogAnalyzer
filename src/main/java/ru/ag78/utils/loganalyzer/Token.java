package ru.ag78.utils.loganalyzer;

import ru.ag78.api.utils.SafeTypes;

/**
 * Token of the filter expression.
 * @author alexey
 *
 */
public class Token {

    private Type type;
    private String value;
    private int index;

    public static enum Type {

        EMPTY, STRING, AND, OR, NOT, OPEN_BR, CLOSE_BR
    }

    /**
     * Ctor with self-detect type of token.
     * @param value
     */
    public Token(String value) {

        value = SafeTypes.getDBString(value);
        this.value = value;
        type = parseTypeFromValue(value);
    }

    /**
     * Ctor with supplied token type.
     * @param type
     * @param value
     */
    public Token(String value, Type type) {

        super();
        this.value = SafeTypes.getDBString(value);
        this.type = type;
    }

    private Type parseTypeFromValue(String value) {

        if (value.isEmpty()) {
            return Type.EMPTY;
        }
        if (value.equals("AND")) {
            return Type.AND;
        }
        if (value.equals("OR")) {
            return Type.OR;
        }
        if (value.equals("NOT")) {
            return Type.NOT;
        }
        if (value.equals("(")) {
            return Type.OPEN_BR;
        }
        if (value.equals(")")) {
            return Type.CLOSE_BR;
        }

        return Type.STRING;
    }

    @Override
    public String toString() {

        return String.format("%03d [%8s] %s", index, type, value);
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Token other = (Token) obj;
        if (type != other.type)
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }

    public Type getType() {

        return type;
    }

    public void setType(Type type) {

        this.type = type;
    }

    public String getValue() {

        return value;
    }

    public void setValue(String value) {

        this.value = value;
    }

    public boolean isLogical() {

        return type == Type.AND || type == Type.OR;
    }

    public boolean isNot() {

        return type == Type.NOT;
    }

    public boolean isBracket() {

        return type == Type.OPEN_BR || type == Type.CLOSE_BR;
    }

    public boolean isValue() {

        return type == Type.STRING;
    }

    public int getIndex() {

        return index;
    }

    public void setIndex(int index) {

        this.index = index;
    }
}
