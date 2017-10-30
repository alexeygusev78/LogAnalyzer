package ru.ag78.utils.loganalyzer;

import org.junit.Assert;
import org.junit.Test;

public class TokenizerTest {

    @Test
    public void toTokensTest() {

        Tokenizable t = new Tokenizer1();

        try {
            Assert.assertEquals(1, t.toTokens("\"wolf\"").size());
            Assert.assertEquals(1, t.toTokens("wolf").size());
            Assert.assertEquals(3, t.toTokens("\"wolf\" AND \"fox\"").size());
            Assert.assertEquals(4, t.toTokens("\"wolf\" AND NOT \"fox\"").size());
            Assert.assertEquals(3, t.toTokens("\"grey wolf\" AND \"red fox\"").size());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}
