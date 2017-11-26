package ru.ag78.utils.loganalyzer.config;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "config")
public class Configuration {

    private Properties props = new Properties();

    private List<Fileset> filesets = new LinkedList<>();

    public Properties getProps() {

        return props;
    }

    public void setProps(Properties props) {

        this.props = props;
    }

    public List<Fileset> getFilesets() {

        return filesets;
    }

    public void setFilesets(List<Fileset> filesets) {

        this.filesets = filesets;
    }
}
