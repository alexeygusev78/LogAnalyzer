package ru.ag78.utils.loganalyzer;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserTest {

    public static void main(String[] args) {

        System.out.println("ParserTest is here...");
        try {
            ParserTest t = new ParserTest();
            t.localDateTimeParseTest();
            t.localTimeParseTest();
            t.tokenizerTest1();
            t.tokenizerTest2();
            t.logicParserTest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void localDateTimeParseTest() throws Exception {

        System.out.println("\r\n.localDateTimeParseTest ----------------------------------------");
        String src = "2017-04-07 00:00:00,312";
        String pattern = "yyyy-MM-dd HH:mm:ss,SSS";

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        System.out.println("   src=" + src);
        System.out.println("format=" + LocalDateTime.now().format(dtf));

        LocalDateTime ldt = LocalDateTime.parse(src, dtf);
        System.out.println("ldt=" + ldt);
    }

    private void localTimeParseTest() throws Exception {

        System.out.println("\r\n.localTimeParseTest ----------------------------------------");
        String src = "12:25:34,312";
        String pattern = "HH:mm:ss,SSS";

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        System.out.println("   src=" + src);
        System.out.println("format=" + LocalTime.now().format(dtf));

        LocalTime lt = LocalTime.parse(src, dtf);
        System.out.println("lt=" + lt);
    }

    /**
     * Trying to parse query
     * @throws Exception
     */
    private void tokenizerTest1() throws Exception {

        System.out.println("\r\n.tokenizerTest1 ----------------------------------------");
        // String pattern = "\\{[a-zA-Z\\d_\\.]+\\}";
        String pattern = "AND|OR|NOT|\\\"[а-яА-Яa-zA-Z0-9_\\s\\.]+\\\"|\\(|\\)"; // AND|OR|NOT|\"[а-яА-Яa-zA-Z0-9_\.]+\"|\(|\)
        String expr = "\"привет\" AND (\"expr2\" OR NOT\"expr3 expr4\")";

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(expr);

        System.out.println("regexp=" + pattern + " expr=" + expr);
        List<String> tokens = new LinkedList<>();
        while (m.find()) {
            tokens.add(m.group());
        }

        tokens.forEach(System.out::println);
    }

    /**
     * Tokenizing query into Predicate
     * @throws Exception
     */
    private void tokenizerTest2() throws Exception {

        System.out.println("\r\n.tokenizerTest2 ----------------------------------------");

        Predicate<String> main = x -> true;

        String pattern = "AND|OR|NOT|\\\"[а-яА-Яa-zA-Z0-9_\\s\\.]+\\\"|\\(|\\)"; // AND|OR|NOT|\"[а-яА-Яa-zA-Z0-9_\.]+\"|\(|\)
        String expr = "\"привет\" AND (\"expr2\" OR NOT\"expr3 expr4\")";

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(expr);

        System.out.println("regexp=" + pattern + " expr=" + expr);
        LinkedList<String> tokens = new LinkedList<>();
        while (m.find()) {
            tokens.add(m.group());
        }

        parseLogic(tokens);
    }

    private void logicParserTest() throws Exception {

        System.out.println("\r\n.logicParserTest ----------------------------------------");

        String expr = "\"волк\" AND \"лиса\" OR \"тюлень\" AND \"олень\" AND \"Орел\" OR \"Щука\"";

        LogicParser lp = new LogicParser();
        Queue<String> tokens = lp.toTokens(expr);
        PredicateWrapper pw = lp.makePredicate(tokens);
        System.out.println("pw=" + pw.toString());
    }

    private Predicate parseLogic(Queue<String> tokens) throws Exception {

        return null;
    }

    private Predicate<String> makePredicate(String token, Queue<String> tokens) {

        return null;
    }
}
