package ru.ag78.utils.loganalyzer.config;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LogFile {

    private String path;
    private String encoding;

    public LogFile() {

    }

    public LogFile(String path, String encoding) {

        super();
        this.path = path;
        this.encoding = encoding;
    }

    public String getPath() {

        return path;
    }

    public void setPath(String path) {

        this.path = path;
    }

    public String getEncoding() {

        return encoding;
    }

    public void setEncoding(String encoding) {

        this.encoding = encoding;
    }
}
