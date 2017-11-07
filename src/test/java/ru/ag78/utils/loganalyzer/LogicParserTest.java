package ru.ag78.utils.loganalyzer;

import java.util.Queue;
import java.util.function.Predicate;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import ru.ag78.utils.loganalyzer.logic.LogicParser;

public class LogicParserTest {

    private static final Logger log = Logger.getLogger(LogicParserTest.class);

    /**
     * 3.1. val1
     */
    @Test
    public void test3_1() {

        log.info("test3_1: fox");

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

        log.info("test3_2: NOT fox");

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
    @Test
    public void test3_3() {

        log.info("test3_3: fox AND bear");

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

    /**
     * 3.4.1. val1 AND NOT val2
     */
    @Test
    public void test3_4_1() {

        log.info("test3_4_1: fox AND NOT bear");

        try {
            String query = "fox AND NOT bear";
            Tokenizable t = new Tokenizer1();
            LogicParser parser = new LogicParser();

            Queue<Token> tokens = t.toTokens2(query);
            Assert.assertNotNull("Empty tokens", tokens);
            Assert.assertEquals(4, tokens.size());

            Predicate<String> p = parser.parse(tokens);
            Assert.assertNotNull("Empty predicate", p);

            Assert.assertFalse(p.test("wolf fox and bear"));
            Assert.assertFalse(p.test("wolf and bear"));
            Assert.assertTrue(p.test("wolf and fox"));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    /**
     * 3.4.2. NOT val1 AND val2
     */
    @Test
    public void test3_4_2() {

        log.info("test3_4_2: NOT fox AND bear");

        try {
            String query = "NOT fox AND bear";
            Tokenizable t = new Tokenizer1();
            LogicParser parser = new LogicParser();

            Queue<Token> tokens = t.toTokens2(query);
            Assert.assertNotNull("Empty tokens", tokens);
            Assert.assertEquals(4, tokens.size());

            Predicate<String> p = parser.parse(tokens);
            Assert.assertNotNull("Empty predicate", p);

            Assert.assertFalse(p.test("wolf fox and bear"));
            Assert.assertTrue(p.test("wolf and bear"));
            Assert.assertFalse(p.test("wolf and fox"));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    /**
     * 3.5. val1 OR val2
     */
    @Test
    public void test3_5() {

        log.info("test3_5: fox OR bear");

        try {
            String query = "fox OR bear";
            Tokenizable t = new Tokenizer1();
            LogicParser parser = new LogicParser();

            Queue<Token> tokens = t.toTokens2(query);
            Assert.assertNotNull("Empty tokens", tokens);
            Assert.assertEquals(3, tokens.size());

            Predicate<String> p = parser.parse(tokens);
            Assert.assertNotNull("Empty predicate", p);

            Assert.assertTrue(p.test("wolf fox and bear"));
            Assert.assertTrue(p.test("wolf and bear"));
            Assert.assertTrue(p.test("wolf and fox"));
            Assert.assertFalse(p.test("wolf and deer"));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    /**
     * 3.6. val1 OR val2 OR val3
     */
    @Test
    public void test3_6() {

        log.info("test3_6: fox OR bear OR wolf");

        try {
            String query = "fox OR bear OR wolf";
            Tokenizable t = new Tokenizer1();
            LogicParser parser = new LogicParser();

            Queue<Token> tokens = t.toTokens2(query);
            Assert.assertNotNull("Empty tokens", tokens);
            Assert.assertEquals(5, tokens.size());

            Predicate<String> p = parser.parse(tokens);
            Assert.assertNotNull("Empty predicate", p);

            Assert.assertFalse(p.test("Hello world"));
            Assert.assertTrue(p.test("Hello world fox"));
            Assert.assertTrue(p.test("Hello world bear"));
            Assert.assertTrue(p.test("Hello world wolf"));
            Assert.assertTrue(p.test("Hello world fox bear"));
            Assert.assertTrue(p.test("Hello world bear wolf"));
            Assert.assertTrue(p.test("Hello world fox wolf"));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    /**
     * 3.7. val1 OR val2 AND val3
     */
    @Test
    public void test3_7() {

        log.info("test3_7: fox OR bear AND wolf");

        try {
            String query = "fox OR bear AND wolf";
            Tokenizable t = new Tokenizer1();
            LogicParser parser = new LogicParser();

            Queue<Token> tokens = t.toTokens2(query);
            Assert.assertNotNull("Empty tokens", tokens);
            Assert.assertEquals(5, tokens.size());

            Predicate<String> p = parser.parse(tokens);
            Assert.assertNotNull("Empty predicate", p);

            Assert.assertFalse(p.test("Hello world"));
            Assert.assertTrue(p.test("Hello world fox"));
            Assert.assertFalse(p.test("Hello world bear"));
            Assert.assertFalse(p.test("Hello world wolf"));
            Assert.assertTrue(p.test("Hello world fox bear"));
            Assert.assertTrue(p.test("Hello world bear wolf"));
            Assert.assertTrue(p.test("Hello world fox wolf"));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    /**
     * 3.8. val1 AND val2 OR val3
     */
    @Test
    public void test3_8() {

        log.info("test3_8: fox AND bear OR wolf");

        try {
            String query = "fox OR bear AND wolf";
            Tokenizable t = new Tokenizer1();
            LogicParser parser = new LogicParser();

            Queue<Token> tokens = t.toTokens2(query);
            Assert.assertNotNull("Empty tokens", tokens);
            Assert.assertEquals(5, tokens.size());

            Predicate<String> p = parser.parse(tokens);
            Assert.assertNotNull("Empty predicate", p);

            Assert.assertFalse(p.test("Hello world"));
            Assert.assertTrue(p.test("Hello world fox"));
            Assert.assertFalse(p.test("Hello world bear"));
            Assert.assertFalse(p.test("Hello world wolf"));
            Assert.assertTrue(p.test("Hello world fox bear"));
            Assert.assertTrue(p.test("Hello world bear wolf"));
            Assert.assertTrue(p.test("Hello world fox wolf"));
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }
}
