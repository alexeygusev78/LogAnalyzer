package ru.ag78.utils.loganalyzer.ui.search;

public class SearchModel {

    private String name;
    private String selectedFileset;

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

    public String getSelectedFileset() {

        return selectedFileset;
    }

    public void setSelectedFileset(String selectedFileset) {

        this.selectedFileset = selectedFileset;
    }
}
