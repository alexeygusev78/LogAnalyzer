package ru.ag78.utils.loganalyzer;

import java.util.Queue;

import org.junit.Assert;
import org.junit.Test;

public class TokenizerTest {

    @Test
    public void toTokensTest() {

        LogicParser lp = new LogicParser();

        try {
            Queue<String> tokens = lp.toTokens("\"wolf\"");
            Assert.assertEquals(1, tokens.size());

        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}
