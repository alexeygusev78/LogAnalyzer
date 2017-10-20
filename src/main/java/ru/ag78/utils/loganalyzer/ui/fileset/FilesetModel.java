package ru.ag78.utils.loganalyzer.ui.fileset;

import java.util.ArrayList;
import java.util.List;

public class FilesetModel {

    private List<LogFile> files = new ArrayList<>();
    private String name;

    /**
     * Ctor with parameters
     * @param name
     */
    public FilesetModel(String name) {

        super();
        this.name = name;
    }

    public List<LogFile> getFiles() {

        return files;
    }

    public void addFile(LogFile file) {

        files.add(file);
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }
}
