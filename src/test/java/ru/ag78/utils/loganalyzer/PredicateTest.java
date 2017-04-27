package ru.ag78.utils.loganalyzer;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

import org.junit.Assert;
import org.junit.Test;

public class PredicateTest {

    @Test
    public void splitTest() {

        String[] tokens = "2017-04-07 00:00:00,305 I [ru.afxng.server.cache.CacheSession] .delete session id=G6Fr-qnyd86ZxvrfLDo-FtbNXCSdSQVIlfDMj5u5".split("\\s");
        Assert.assertTrue(tokens.length > 0);
        Assert.assertEquals("2017-04-07", tokens[0]);
        Assert.assertEquals("00:00:00,305", tokens[1]);
    }

    @Test
    public void testTimeFrom() {

        final String pattern = "HH:mm:ss,SSS";
        final String strFrom = "10:00:00,000";
        final LocalTime ltFrom = LocalTime.parse(strFrom, DateTimeFormatter.ofPattern(pattern));

        Assert.assertNotNull(ltFrom);

        Predicate<String> p = x -> {

            String[] tokens = x.split("\\s");
            String time = tokens[1];

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
            LocalTime curTime = LocalTime.parse(time, dtf);

            return curTime.isAfter(ltFrom);
        };

        Assert.assertTrue(p.test("2017-04-07 12:23:30,001 I [ru.afxng.server.service.TimerSessionListener] onTimer: Timer.Session"));
        Assert.assertFalse(p.test("2017-04-07 02:23:30,001 I [ru.afxng.server.service.TimerSessionListener] onTimer: Timer.Session"));
    }
}
