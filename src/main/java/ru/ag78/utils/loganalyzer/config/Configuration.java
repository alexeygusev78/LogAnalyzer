package ru.ag78.utils.loganalyzer.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.annotation.XmlRootElement;

import ru.ag78.utils.loganalyzer.ui.fileset.Fileset;

@XmlRootElement
public class Configuration {

    private Properties props = new Properties();
    private Map<String, Fileset> filesets = new HashMap<>();

    public Properties getProps() {

        return props;
    }

    public void setProps(Properties props) {

        this.props = props;
    }

    public Map<String, Fileset> getFilesets() {

        return filesets;
    }

    public void setFilesets(Map<String, Fileset> filesets) {

        this.filesets = filesets;
    }
}
