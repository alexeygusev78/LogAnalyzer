package ru.ag78.utils.loganalyzer;

import java.util.Queue;

import org.junit.Assert;
import org.junit.Test;

import ru.ag78.utils.loganalyzer.Token.Type;

public class TokenizerTest {

    /**
     * Test toTokens method with non-cyrillic symobols.
     */
    @Test
    public void toTokensTest() {

        try {
            checkTokens("blogic", new Token[] {new Token("blogic", Type.STRING)});

            Assert.assertTrue(checkTokens("blogic AND *rc*", new Token[] {
                            new Token("blogic", Type.STRING),
                            new Token("AND", Type.AND),
                            new Token("*rc*", Type.STRING)}));

            Assert.assertTrue(checkTokens("*rc* AND (<<< OR >>>)", new Token[] {
                            new Token("*rc*", Type.STRING),
                            new Token("AND", Type.AND),
                            new Token("(", Type.OPEN_BR),
                            new Token("<<<", Type.STRING),
                            new Token("OR", Type.OR),
                            new Token(">>>", Type.STRING),
                            new Token(")", Type.CLOSE_BR)}));

            Assert.assertTrue(checkTokens("blogic (*rc* OR *YO!*)", new Token[] {
                            new Token("blogic", Type.STRING),
                            new Token("AND", Type.AND),
                            new Token("(", Type.OPEN_BR),
                            new Token("*rc*", Type.STRING),
                            new Token("OR", Type.OR),
                            new Token("*YO!*", Type.STRING),
                            new Token(")", Type.CLOSE_BR)}));

        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * 4 cases:
     * - if STRING STRING => STRING AND STRING
     * - if STRING OPEN_BR => STRING AND OPEN_BR
     * - if CLOSE_BR STRING => CLOSE_BR AND STRING
     * - if CLOSE_BR OPEN_BR => CLOSE_BR AND OPEN_BR
     */
    @Test
    public void toTokensAddAndOperator() {

        try {
            // - if STRING STRING => STRING AND STRING
            Assert.assertTrue(checkTokens("   blogic *rc*   ", new Token[] {
                            new Token("blogic", Type.STRING),
                            new Token("AND", Type.AND),
                            new Token("*rc*", Type.STRING)}));

            // - if STRING OPEN_BR => STRING AND OPEN_BR
            Assert.assertTrue(checkTokens("wolf (fox)", new Token[] {
                            new Token("wolf", Type.STRING),
                            new Token("AND", Type.AND),
                            new Token("(", Type.OPEN_BR),
                            new Token("fox", Type.STRING),
                            new Token(")", Type.CLOSE_BR)}));

            // - if CLOSE_BR STRING => CLOSE_BR AND STRING
            Assert.assertTrue(checkTokens("(wolf) fox", new Token[] {
                            new Token("(", Type.OPEN_BR),
                            new Token("wolf", Type.STRING),
                            new Token(")", Type.CLOSE_BR),
                            new Token("AND", Type.AND),
                            new Token("fox", Type.STRING)}));

            // - if CLOSE_BR OPEN_BR => CLOSE_BR AND OPEN_BR
            Assert.assertTrue(checkTokens("(wolf) (fox)", new Token[] {
                            new Token("(", Type.OPEN_BR),
                            new Token("wolf", Type.STRING),
                            new Token(")", Type.CLOSE_BR),
                            new Token("AND", Type.AND),
                            new Token("(", Type.OPEN_BR),
                            new Token("fox", Type.STRING),
                            new Token(")", Type.CLOSE_BR)}));

        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * check remove begin-end quotes for STRING token and trim.
     */
    @Test
    public void toTokensTestQuotes() {

        try {
            Assert.assertTrue(checkTokens("\"wolf\"", new Token[] {new Token("wolf", Type.STRING)}));
            Assert.assertTrue(checkTokens("\"     wolf     \"", new Token[] {new Token("wolf", Type.STRING)}));
            Assert.assertTrue(checkTokens("   \"wolf\"   ", new Token[] {new Token("wolf", Type.STRING)}));
            Assert.assertTrue(checkTokens("\"red fox\"", new Token[] {new Token("red fox", Type.STRING)}));
            Assert.assertTrue(checkTokens("   \"   red   fox   \"   ", new Token[] {new Token("red   fox", Type.STRING)}));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test toTokens with cyrillic symbols.
     * FIXME: does not work: fix and enable test again.
     */
    // @Test
    public void toTokensTestCyryllic() {

        try {
            Assert.assertTrue(checkTokens("яблоко", new Token[] {new Token("blogic", Type.STRING)}));
            Assert.assertTrue(checkTokens("альфа Омега", new Token[] {
                            new Token("альфа", Type.STRING),
                            new Token("Омега", Type.STRING)}));

        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void tokenParseTest() {

        try {
            Token.Type t = Type.valueOf("blogic");
            Assert.fail("You cannot be here...");
        } catch (Exception e) {
        }

        try {
            Assert.assertTrue(Token.Type.EMPTY == new Token(null).getType());
            Assert.assertTrue(Token.Type.EMPTY == new Token("   ").getType());
            Assert.assertTrue(Token.Type.STRING == new Token("blogic").getType());
            Assert.assertEquals("blogic", new Token("   blogic   ").getValue());
            Assert.assertTrue(Token.Type.STRING == new Token("and").getType());
            Assert.assertTrue(Token.Type.AND == new Token("AND").getType());
            Assert.assertTrue(Token.Type.AND == new Token("   AND   ").getType());
            Assert.assertTrue(Token.Type.OR == new Token("OR").getType());
            Assert.assertTrue(Token.Type.NOT == new Token("NOT").getType());
            Assert.assertTrue(Token.Type.OPEN_BR == new Token("(").getType());
            Assert.assertTrue(Token.Type.CLOSE_BR == new Token(")").getType());

        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    private boolean checkTokens(String filter, Token... tokens) throws Exception {

        Tokenizable tokenizer = new Tokenizer1();

        Queue<Token> res = tokenizer.toTokens2(filter);
        // Assert.assertEquals(res.toString(), tokens.length, res.size());

        for (int i = 0; i < tokens.length; i++) {
            Token t1 = tokens[i];
            Token t2 = res.remove();
            if (!t1.equals(t2)) {
                return false;
            }
        }

        return true;
    }
}
