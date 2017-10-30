package ru.ag78.utils.loganalyzer.ui;

import java.util.HashMap;
import java.util.Map;

import ru.ag78.utils.loganalyzer.ui.fileset.FilesetModel;

public class MainModel {

    private int filesetCounter = 1;
    private int searchCounter = 1;

    private Map<String, FilesetModel> filesets = new HashMap<>();

    public String getNextFilesetName() {

        return "Fileset" + Integer.toString(filesetCounter++);
    }

    public String getNextSearchName() {

        return "Search" + Integer.toString(searchCounter++);
    }

    public FilesetModel getFileset(String filesetName) {

        return filesets.get(filesetName);
    }
}
