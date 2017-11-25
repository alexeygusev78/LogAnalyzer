package ru.ag78.utils.loganalyzer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.Assert;
import org.junit.Test;

import ru.ag78.utils.loganalyzer.config.Configuration;
import ru.ag78.utils.loganalyzer.ui.fileset.FilesetModel;

public class JAXBTest {

    @Test
    public void marshalTest() {

        Configuration config = new Configuration();
        config.getProps().setProperty("ru.ag78.encoding", "UTF-8");

        FilesetModel fs = new FilesetModel();

        try {
            JAXBContext context = JAXBContext.newInstance(Configuration.class);
            Assert.assertNotNull(context);

            Marshaller m = context.createMarshaller();
            Assert.assertNotNull(m);

            m.marshal(config, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void settingsTest() {

    }
}
