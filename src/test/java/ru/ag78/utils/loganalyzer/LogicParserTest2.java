package ru.ag78.utils.loganalyzer;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

public class LogicParserTest2 {

    @Test
    public void test1() {

        final String filter = "*rc* AND CacheSpread";

        Stream<String> s = null;
        try {
            Path path = Paths.get("stuff\\test1.log");
            Assert.assertTrue(path.toFile().exists());
            s = Files.lines(path, Charset.forName("UTF-8"));
            Assert.assertNotNull(s);

            Tokenizable t = new Tokenizer1();
            Queue<String> tokens = t.toTokens(filter);
            tokens.stream().forEach(System.out::println);

            LogicParser lp = new LogicParser();
            PredicateWrapper p = lp.constructPredicate(tokens);

            s = s.filter(p.getPredicate()).limit(3);
            List<String> results = s.collect(Collectors.toList());

            Assert.assertNotNull(results);
            Assert.assertTrue(results.size() > 0);

            for (String res: results) {
                System.out.println(res);
                Assert.assertTrue(res.contains("*rc*"));
                Assert.assertTrue(res.contains("CacheSpread"));
            }

        } catch (Exception e) {
            Assert.fail(e.toString());
        } finally {
            s.close();
        }
        //        s = s.filter(p);
        //
        //        if (limit > 0) {
        //            s = s.limit(limit);
        //        }
        //
        //        s.forEach(l -> sb.append(l).append("\r\n"));
    }
}
