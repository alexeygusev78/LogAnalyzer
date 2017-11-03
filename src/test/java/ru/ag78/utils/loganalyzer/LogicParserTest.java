package ru.ag78.utils.loganalyzer;

import java.util.Queue;
import java.util.function.Predicate;

import org.junit.Assert;
import org.junit.Test;

import ru.ag78.utils.loganalyzer.logic.LogicParser;

public class LogicParserTest {

    /**
     * 3.1. val1
     */
    @Test
    public void test3_1() {

        try {
            String query = "fox";
            Tokenizable t = new Tokenizer1();
            LogicParser parser = new LogicParser();

            Queue<Token> tokens = t.toTokens2(query);
            Assert.assertNotNull("Empty tokens", tokens);
            Assert.assertEquals(1, tokens.size());

            Predicate<String> p = parser.parse(tokens);
            Assert.assertNotNull("Empty predicate", p);

            Assert.assertTrue(p.test("wolf fox and bear"));
            Assert.assertFalse(p.test("wolf and bear"));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    /**
     * 3.2. NOT val1
     */
    @Test
    public void test3_2() {

        try {
            String query = "NOT fox";
            Tokenizable t = new Tokenizer1();
            LogicParser parser = new LogicParser();

            Queue<Token> tokens = t.toTokens2(query);
            Assert.assertNotNull("Empty tokens", tokens);
            Assert.assertEquals(2, tokens.size());

            Predicate<String> p = parser.parse(tokens);
            Assert.assertNotNull("Empty predicate", p);

            Assert.assertFalse(p.test("wolf fox and bear"));
            Assert.assertTrue(p.test("wolf and bear"));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    /**
     * 3.3. val1 AND val2, val1 AND (val2)
     */
    // @Test
    public void test3_3() {

        try {
            String query = "fox AND bear";
            Tokenizable t = new Tokenizer1();
            LogicParser parser = new LogicParser();

            Queue<Token> tokens = t.toTokens2(query);
            Assert.assertNotNull("Empty tokens", tokens);
            Assert.assertEquals(3, tokens.size());

            Predicate<String> p = parser.parse(tokens);
            Assert.assertNotNull("Empty predicate", p);

            Assert.assertTrue(p.test("wolf fox and bear"));
            Assert.assertFalse(p.test("wolf and bear"));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }
}
