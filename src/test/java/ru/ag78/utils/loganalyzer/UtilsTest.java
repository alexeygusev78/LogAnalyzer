package ru.ag78.utils.loganalyzer;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import ru.ag78.useful.helpers.Utils;

public class UtilsTest {

    @Test
    public void loadManifestTest() {

        System.out.println(".loadManifestTest");
        try {
            Properties props = Utils.getManifest();
            Assert.assertNotNull(props);
            Assert.assertTrue(props.keySet().size() > 0);

            props.keySet().stream().forEach(p -> {
                System.out.println(p);
            });

            Assert.assertNotNull(props.get("Main-Class"));
            Assert.assertEquals("ru.ag78.utils.loganalyzer.LogAnalyzer", props.get("Main-Class").toString());

        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }
}
