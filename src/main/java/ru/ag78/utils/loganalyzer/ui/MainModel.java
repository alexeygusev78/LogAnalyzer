package ru.ag78.utils.loganalyzer.ui;

public class MainModel {

    private int filesetCounter = 1;

    public String getNextFilesetName() {

        return "Fileset" + Integer.toString(filesetCounter++);
    }
}
