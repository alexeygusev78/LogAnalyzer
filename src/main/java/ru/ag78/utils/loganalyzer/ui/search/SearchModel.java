package ru.ag78.utils.loganalyzer.ui.search;

public class SearchModel {

    private String name;

    /**
     * Ctor using fields
     * @param name
     */
    public SearchModel(String name) {

        super();
        this.name = name;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }
}
