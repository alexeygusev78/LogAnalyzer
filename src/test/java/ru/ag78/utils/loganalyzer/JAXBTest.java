package ru.ag78.utils.loganalyzer;

import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.junit.Assert;
import org.junit.Test;

import ru.ag78.useful.helpers.Utils;
import ru.ag78.utils.loganalyzer.config.Configuration;
import ru.ag78.utils.loganalyzer.config.Fileset;
import ru.ag78.utils.loganalyzer.config.LogFile;

public class JAXBTest {

    @Test
    public void marshalTest() {

        Configuration config = new Configuration();
        config.getProps().setProperty("ru.ag78.encoding", "UTF-8");

        Fileset fs = new Fileset();
        fs.setName("rest_rabbit");
        fs.setDescription("rest service on rabbit");
        fs.getFiles().add(new LogFile("/home/alexey/log/rest1.log", "UTF-8"));
        fs.getFiles().add(new LogFile("/home/alexey/log/rest2.log", "UTF-8"));
        fs.getFiles().add(new LogFile("/home/alexey/log/rest3.log", "UTF-8"));
        config.getFilesets().add(fs);

        fs = new Fileset();
        fs.setName("soap_rabbit");
        fs.setDescription("soap service on rabbit");
        fs.getFiles().add(new LogFile("/home/alexey/log/soap1.log", "UTF-8"));
        fs.getFiles().add(new LogFile("/home/alexey/log/soap2.log", "UTF-8"));
        config.getFilesets().add(fs);

        try (FileWriter fw = new FileWriter(Utils.getConfigFile().toFile(), false)) {
            JAXBContext context = JAXBContext.newInstance(Configuration.class);
            Assert.assertNotNull(context);

            Marshaller m = context.createMarshaller();
            Assert.assertNotNull(m);

            m.marshal(config, fw);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void settingsTest() {

        System.getProperties().keySet().stream().filter(k -> k.toString().contains("user")).forEach(k -> {
            System.out.println(k.toString() + "=" + System.getProperty(k.toString(), ""));
        });

        Path p = Paths.get(System.getProperty("user.home"), "loganalyzer.dat");
        System.out.println("p=" + p.toString());
    }
}
