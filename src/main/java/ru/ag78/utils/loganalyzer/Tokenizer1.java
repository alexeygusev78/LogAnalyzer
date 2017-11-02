package ru.ag78.utils.loganalyzer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.ag78.api.utils.SafeTypes;

/**
 * Standard tokenizer.
 * AND, OR, NOT, (, ), word, "hello world"
 * @author Алексей
 *
 */
public class Tokenizer1 implements Tokenizable {

    private String pattern = "\\\".+?\\\"|\\(|\\)|[\\w\\[\\]\\\\\\/\\^\\$\\.\\|\\?\\*\\+\\{\\}<>!@#%_-]+"; // regexp: \".+?\"|\(|\)|[\w\[\]\\\/\^\$\.\|\?\*\+\{\}<>!@#%_-=]+

    /**
     * Default ctor with default RegExp to tokenize query string.
     */
    public Tokenizer1() {

    }

    /**
     * Ctor with supplied pattern.
     * @param pattern
     */
    public Tokenizer1(String pattern) {

        super();
        this.pattern = pattern;
    }

    @Override
    public Queue<String> toTokens(String expr) throws Exception {

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(expr);

        LinkedList<String> tokens = new LinkedList<>();
        while (m.find()) {
            tokens.add(m.group());
        }

        return tokens;
    }

    @Override
    public Queue<Token> toTokens2(String expr) throws Exception {

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(expr);

        // split input string to a tokens
        LinkedList<Token> tokens = new LinkedList<>();
        while (m.find()) {
            String value = m.group();
            Token t = new Token(value);
            if (t.getType() == Token.Type.STRING) {
                t.setValue(removeBeginEndQuotes(t.getValue()));
            }
            tokens.add(t);
        }

        // add AND-operator between two STRING tokens
        tokens = insertAndOperator(tokens);

        // renum
        for (int i = 0; i < tokens.size(); i++) {
            tokens.get(i).setIndex(i);
        }

        return tokens;
    }

    private LinkedList<Token> insertAndOperator(LinkedList<Token> tokens) {

        LinkedList<Token> retlist = new LinkedList<>();

        Token t1 = null;
        while ((t1 = tokens.poll()) != null) {
            retlist.add(t1);

            if (Tokenizable.isInType(t1.getType(), Token.Type.STRING, Token.Type.CLOSE_BR)) {
                // for current token is STRING or CLOSE_BR -> look forward
                Token t2 = tokens.peek();
                if (t2 != null && Tokenizable.isInType(t2.getType(), Token.Type.STRING, Token.Type.OPEN_BR)) {
                    retlist.add(new Token("AND", Token.Type.AND));
                }
            }
        }

        return retlist;
    }

    private String removeBeginEndQuotes(String value) {

        value = value.startsWith("\"") ? value.substring(1) : value;
        value = value.endsWith("\"") ? value.substring(0, value.length() - 1) : value;
        value = SafeTypes.getDBString(value);

        return value;
    }
}
