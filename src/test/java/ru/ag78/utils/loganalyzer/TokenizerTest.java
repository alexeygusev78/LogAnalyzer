package ru.ag78.utils.loganalyzer;

import org.junit.Assert;
import org.junit.Test;

public class TokenizerTest {

    @Test
    public void toTokensTest() {

        LogicParser lp = new LogicParser();

        try {
            Assert.assertEquals(1, lp.toTokens("\"wolf\"").size());
            // Assert.assertEquals(1, lp.toTokens("wolf").size());
            Assert.assertEquals(3, lp.toTokens("\"wolf\" AND \"fox\"").size());
            Assert.assertEquals(4, lp.toTokens("\"wolf\" AND NOT \"fox\"").size());
            Assert.assertEquals(3, lp.toTokens("\"grey wolf\" AND \"red fox\"").size());

        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}
