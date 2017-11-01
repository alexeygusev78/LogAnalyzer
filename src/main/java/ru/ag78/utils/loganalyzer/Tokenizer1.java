package ru.ag78.utils.loganalyzer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Standard tokenizer.
 * AND, OR, NOT, (, ), word, "hello world"
 * @author Алексей
 *
 */
public class Tokenizer1 implements Tokenizable {

    private static final String pattern = "\\\".+?\\\"|\\(|\\)|[\\w\\[\\]\\\\\\/\\^\\$\\.\\|\\?\\*\\+\\{\\}<>!@#%_-]+"; // regexp: \".+?\"|\(|\)|[\w\[\]\\\/\^\$\.\|\?\*\+\{\}<>!@#%_-=]+

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

        LinkedList<Token> tokens = new LinkedList<>();
        while (m.find()) {
            String value = m.group();
            Token t = new Token(value);
            tokens.add(t);
        }

        return tokens;
    }
}
