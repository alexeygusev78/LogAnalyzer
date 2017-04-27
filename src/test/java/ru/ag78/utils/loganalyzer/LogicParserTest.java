package ru.ag78.utils.loganalyzer;

import java.util.Queue;
import java.util.function.Predicate;

import org.junit.Assert;
import org.junit.Test;

import ru.ag78.utils.loganalyzer.LogicParser;
import ru.ag78.utils.loganalyzer.PredicateWrapper;

public class LogicParserTest {

    /**
     * Тестируем работу выражения: wolf
     */
    @Test
    public void testWolf() {

        System.out.println("testWolf ----------------------------------------");
        try {
            LogicParser lp = new LogicParser();
            String expr = "\"wolf\"";
            System.out.println("expr=" + expr);

            Queue<String> tokens = lp.toTokens(expr);
            Assert.assertNotNull(tokens);
            Assert.assertEquals(1, tokens.size());

            Predicate<String> p = lp.makePredicate(tokens).getPredicate();
            Assert.assertNotNull(p);

            Assert.assertTrue(p.test("The wolf - the nurse of the forest"));
            Assert.assertFalse(p.test("The fox is beautiful"));

        } catch (Exception e) {
            Assert.assertTrue(e.toString(), false);
        }
    }

    /**
     * Тестируем работу выражения: wolf AND fox
     */
    @Test
    public void testWolfAndFox() {

        System.out.println("testWolfAndFox ----------------------------------------");
        try {
            LogicParser lp = new LogicParser();
            String expr = "\"wolf\" AND \"fox\"";
            System.out.println("expr=" + expr);

            Queue<String> tokens = lp.toTokens(expr);
            Assert.assertNotNull(tokens);
            Assert.assertEquals(3, tokens.size());

            Predicate<String> p = lp.makePredicate(tokens).getPredicate();
            Assert.assertNotNull(p);

            Assert.assertFalse(p.test("Wolf - санитар леса"));
            Assert.assertFalse(p.test("fox - краса"));
            Assert.assertTrue(p.test("wolf и fox - друзья"));
        } catch (Exception e) {
            Assert.assertTrue(e.toString(), false);
        }
    }

    /**
     * Тестируем работу выражения: wolf AND NOT fox
     */
    @Test
    public void testWolfAndNotFox() {

        System.out.println("testWolfAndNotFox ----------------------------------------");
        try {
            LogicParser lp = new LogicParser();
            String expr = "\"wolf\" AND NOT \"fox\"";
            System.out.println("expr=" + expr);

            Queue<String> tokens = lp.toTokens(expr);
            Assert.assertNotNull(tokens);
            Assert.assertEquals(4, tokens.size());

            Predicate<String> p = lp.makePredicate(tokens).getPredicate();
            Assert.assertNotNull(p);

            Assert.assertTrue(p.test("Wolf - санитар леса"));
            Assert.assertFalse(p.test("fox - краса"));
            Assert.assertFalse(p.test("wolf и fox - друзья"));
        } catch (Exception e) {
            Assert.assertTrue(e.toString(), false);
        }
    }

    /**
     * Тестируем работу выражения: NOT wolf AND NOT fox
     */
    @Test
    public void testNotWolfAndNotFox() {

        System.out.println("testNotWolfAndNotFox ----------------------------------------");
        try {
            LogicParser lp = new LogicParser();
            String expr = "NOT \"wolf\" AND NOT \"fox\"";
            System.out.println("expr=" + expr);

            Queue<String> tokens = lp.toTokens(expr);
            Assert.assertNotNull(tokens);
            Assert.assertEquals(5, tokens.size());

            Predicate<String> p = lp.makePredicate(tokens).getPredicate();
            Assert.assertNotNull(p);

            Assert.assertFalse(p.test("Wolf - санитар леса"));
            Assert.assertFalse(p.test("fox - краса"));
            Assert.assertFalse(p.test("wolf и fox - друзья"));
            Assert.assertTrue(p.test("лебедь рак и щука"));
        } catch (Exception e) {
            Assert.assertTrue(e.toString(), false);
        }
    }

    /**
     * Тестируем работу выражения: wolf OR fox
     */
    @Test
    public void testWolfOrFox() {

        System.out.println("testWolfOrFox ----------------------------------------");
        try {
            LogicParser lp = new LogicParser();
            String expr = "\"wolf\" OR \"fox\"";
            System.out.println("expr=" + expr);

            Queue<String> tokens = lp.toTokens(expr);
            Assert.assertNotNull(tokens);
            Assert.assertEquals(3, tokens.size());

            Predicate<String> p = lp.makePredicate(tokens).getPredicate();
            Assert.assertNotNull(p);

            Assert.assertTrue(p.test("Wolf - санитар леса"));
            Assert.assertTrue(p.test("fox - краса"));
            Assert.assertTrue(p.test("wolf и fox - друзья"));
            Assert.assertFalse(p.test("лебедь рак и щука"));
        } catch (Exception e) {
            Assert.assertTrue(e.toString(), false);
        }
    }

    /**
     * Тестируем работу выражения: wolf и fox или сокол и голубь
     */
    @Test
    public void testWolfAndFoxOrHawkAndPigeon() {

        System.out.println("testWolfAndFoxOrHawkAndPigeon ----------------------------------------");
        try {
            LogicParser lp = new LogicParser();
            String expr = "\"wolf\" AND \"fox\" OR \"hawk\" AND \"pigeon\"";
            System.out.println("expr=" + expr);

            Queue<String> tokens = lp.toTokens(expr);
            Assert.assertNotNull(tokens);
            Assert.assertEquals(7, tokens.size());

            Predicate<String> p = lp.makePredicate(tokens).getPredicate();
            Assert.assertNotNull(p);

            Assert.assertFalse(p.test("Wolf - forest's nurse"));
            Assert.assertFalse(p.test("fox - beautiful"));
            Assert.assertTrue(p.test("wolf and fox are friends"));
            Assert.assertTrue(p.test("pigeon and hawk are birds"));
            Assert.assertFalse(p.test("Pigeon and wolf are enemies"));
        } catch (Exception e) {
            Assert.assertTrue(e.toString(), false);
        }
    }

    /**
     * Тестируем работу выражения: wolf AND (fox OR rabbit)
     */
    @Test
    public void testWolfAND_FoxOrRabbit_() {

        System.out.println("testWolfAND_FoxOrRabbit_ ----------------------------------------");
        try {
            LogicParser lp = new LogicParser();
            String expr = "\"wolf\" AND (\"fox\" OR \"rabbit\")";
            System.out.println("expr=" + expr);

            Queue<String> tokens = lp.toTokens(expr);
            Assert.assertNotNull(tokens);
            Assert.assertEquals(7, tokens.size());

            PredicateWrapper pw = lp.makePredicate(tokens);
            Assert.assertNotNull(pw);
            System.out.println("pw=" + pw.toString());

            Predicate<String> p = pw.getPredicate();
            Assert.assertNotNull(p);

            Assert.assertFalse(p.test("wolf a nurse of the forest"));
            Assert.assertFalse(p.test("fox - beautiful"));
            Assert.assertFalse(p.test("rabbit - beautiful"));
            Assert.assertTrue(p.test("wolf and fox are friends"));
            Assert.assertTrue(p.test("wolf and rabbit are enemies"));
            Assert.assertTrue(p.test("wolf and fox are friends, but rabbit is an enemy"));
            Assert.assertFalse(p.test("swan, crawfish and pike"));
        } catch (Exception e) {
            Assert.assertTrue(e.toString(), false);
        }
    }
}
