package ru.ag78.utils.loganalyzer.ui.fileset;

import java.util.ArrayList;
import java.util.List;

public class FilesetModel {

    private List<LogFile> files = new ArrayList<>();

    public List<LogFile> getFiles() {

        return files;
    }

    public void addFile(LogFile file) {

        files.add(file);
    }
}
