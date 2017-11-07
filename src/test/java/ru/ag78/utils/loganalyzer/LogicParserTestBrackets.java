package ru.ag78.utils.loganalyzer;

import java.util.Queue;
import java.util.function.Predicate;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import ru.ag78.utils.loganalyzer.logic.LogicParser;

/**
 * Test parse expressions with brackets.
 * @author alexey
 *
 */
public class LogicParserTestBrackets {

    private static final Logger log = Logger.getLogger(LogicParserTestBrackets.class);

    /**
     * 4.1. val1 AND (val2 OR val3)
     */
    @Test
    public void test4_1() {

        log.info("4.1. wolf AND (grey OR white)");

        try {
            String query = "wolf AND (grey OR white)";
            Tokenizable t = new Tokenizer1();
            LogicParser parser = new LogicParser();

            Queue<Token> tokens = t.toTokens2(query);
            Assert.assertNotNull("Empty tokens", tokens);
            Assert.assertEquals(7, tokens.size());

            Predicate<String> p = parser.parse(tokens);
            Assert.assertNotNull("Empty predicate", p);

            Assert.assertFalse(p.test("Hello world"));

            Assert.assertTrue(p.test("grey wolf"));
            Assert.assertTrue(p.test("white wolf"));
            Assert.assertFalse(p.test("red wolf"));

            Assert.assertFalse(p.test("grey fox"));
            Assert.assertFalse(p.test("white fox"));
            Assert.assertFalse(p.test("red fox"));

            Assert.assertFalse(p.test("grey bear"));
            Assert.assertFalse(p.test("white bear"));
            Assert.assertFalse(p.test("red bear"));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    /**
     * 4.2. val1 AND NOT (val2 OR val3)
     */
    // @Test
    public void test4_2() {

        log.info("4.2. wolf AND NOT (grey OR white)");

        try {
            String query = "wolf AND NOT (grey OR white)";
            Tokenizable t = new Tokenizer1();
            LogicParser parser = new LogicParser();

            Queue<Token> tokens = t.toTokens2(query);
            Assert.assertNotNull("Empty tokens", tokens);
            Assert.assertEquals(8, tokens.size());

            Predicate<String> p = parser.parse(tokens);
            Assert.assertNotNull("Empty predicate", p);

            Assert.assertFalse(p.test("Hello world"));

            Assert.assertFalse(p.test("grey wolf"));
            Assert.assertFalse(p.test("white wolf"));
            Assert.assertTrue(p.test("red wolf"));

            Assert.assertFalse(p.test("grey fox"));
            Assert.assertFalse(p.test("white fox"));
            Assert.assertFalse(p.test("red fox"));

            Assert.assertFalse(p.test("grey bear"));
            Assert.assertFalse(p.test("white bear"));
            Assert.assertFalse(p.test("red bear"));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }
}
