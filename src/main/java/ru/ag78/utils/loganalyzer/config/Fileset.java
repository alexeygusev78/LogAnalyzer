package ru.ag78.utils.loganalyzer.config;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlType;

@XmlType
public class Fileset {

    private String name;
    private String description;

    private List<LogFile> files = new LinkedList<>();

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public List<LogFile> getFiles() {

        return files;
    }

    public void setFiles(List<LogFile> files) {

        this.files = files;
    }
}
