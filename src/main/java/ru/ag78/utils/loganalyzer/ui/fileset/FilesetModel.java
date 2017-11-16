package ru.ag78.utils.loganalyzer.ui.fileset;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class FilesetModel {

    private List<LogFileItem> files = new LinkedList<LogFileItem>();
    private String name;
    private String description;
    private boolean persist;

    /**
     * Ctor with parameters
     * @param name
     */
    public FilesetModel(String name) {

        super();
        this.name = name;
    }

    public void setFiles(List<LogFileItem> files) {

        this.files = files;
    }

    public List<LogFileItem> getFiles() {

        return files;
    }

    public List<LogFileItem> getSelectedFiles() {

        return files.stream().filter(f -> f.isChecked()).map(f -> f.clone()).collect(Collectors.toList());
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

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public boolean isPersist() {

        return persist;
    }

    public void setPersist(boolean persist) {

        this.persist = persist;
    }

}
