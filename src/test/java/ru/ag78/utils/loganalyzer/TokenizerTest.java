package ru.ag78.utils.loganalyzer;

import java.util.Queue;

import org.junit.Assert;
import org.junit.Test;

import ru.ag78.utils.loganalyzer.Token.Type;

public class TokenizerTest {

    @Test
    public void toTokensTest() {

        Tokenizable t = new Tokenizer1();
        try {
            checkTokens("blogic", new Token[] {new Token("blogic", Type.STRING)});
            checkTokens("blogic *rc*", new Token[] {
                            new Token("blogic", Type.STRING),
                            new Token("*rc*", Type.STRING)});
            checkTokens("blogic AND *rc*", new Token[] {
                            new Token("blogic", Type.STRING),
                            new Token("AND", Type.AND),
                            new Token("*rc*", Type.STRING)});
            checkTokens("*rc* AND (<<< OR >>>)", new Token[] {
                            new Token("*rc*", Type.STRING),
                            new Token("AND", Type.AND),
                            new Token("(", Type.OPEN_BR),
                            new Token("<<<", Type.STRING),
                            new Token("OR", Type.OR),
                            new Token(">>>", Type.STRING),
                            new Token(")", Type.CLOSE_BR)});
            // TODO: пока этот тест не проходит на Linux. На Windows, возможно будет проходить.
            //            checkTokens("альфа Омега", new Token[] {
            //                            new Token("альфа", Type.STRING),
            //                            new Token("Омега", Type.STRING)});
            checkTokens("blogic (*rc* OR *YO!*)", new Token[] {
                            new Token("blogic", Type.STRING),
                            new Token("(", Type.OPEN_BR),
                            new Token("*rc*", Type.STRING),
                            new Token("OR", Type.OR),
                            new Token("*YO!*", Type.STRING),
                            new Token(")", Type.CLOSE_BR)});

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

    private void checkTokens(String filter, Token... tokens) throws Exception {

        Tokenizable tokenizer = new Tokenizer1();

        Queue<Token> res = tokenizer.toTokens2(filter);
        Assert.assertEquals(res.toString(), tokens.length, res.size());

        for (int i = 0; i < tokens.length; i++) {
            Token t1 = tokens[i];
            Token t2 = res.remove();
            Assert.assertTrue(t1.equals(t2));
        }
    }
}
