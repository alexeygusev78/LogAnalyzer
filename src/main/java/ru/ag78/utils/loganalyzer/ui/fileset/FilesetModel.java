package ru.ag78.utils.loganalyzer.ui.fileset;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FilesetModel {

    private ObservableList<LogFileItem> files = FXCollections.observableArrayList(new ArrayList<LogFileItem>());
    private String name;

    /**
     * Ctor with parameters
     * @param name
     */
    public FilesetModel(String name) {

        super();
        this.name = name;
    }

    public ObservableList<LogFileItem> getFiles() {

        return files;
    }

    public List<LogFileItem> getSelectedFiles() {

        return files.stream().filter(f -> f.isSelected()).map(f -> f.clone()).collect(Collectors.toList());
    }

    public void addFile(LogFileItem file) {

        files.add(file);
    }

    /**
     * Remove file from the list by its name.
     * @param filename
     * @return - true if file has been removed & false if file did not found in list.
     */
    public boolean deleteFile(LogFileItem item) {

        return files.removeIf(i -> item.equals(i));
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }
}
