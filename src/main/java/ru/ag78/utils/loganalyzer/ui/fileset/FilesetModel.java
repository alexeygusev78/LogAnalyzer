package ru.ag78.utils.loganalyzer.ui.fileset;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FilesetModel {

    private ObservableList<LogFile> files = FXCollections.observableArrayList(new ArrayList<LogFile>());
    private String name;

    /**
     * Ctor with parameters
     * @param name
     */
    public FilesetModel(String name) {

        super();
        this.name = name;
    }

    public ObservableList<LogFile> getFiles() {

        return files;
    }

    public void addFile(LogFile file) {

        files.add(file);
    }

    /**
     * Remove file from the list by its name.
     * @param filename
     * @return - true if file has been removed & false if file did not found in list.
     */
    public boolean deleteFile(LogFile item) {

        return files.removeIf(i -> item.equals(i));
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }
}
