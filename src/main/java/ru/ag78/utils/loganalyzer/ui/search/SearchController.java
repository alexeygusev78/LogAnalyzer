package ru.ag78.utils.loganalyzer.ui.search;

import org.apache.log4j.Logger;

public class SearchController implements SearchView.Events {

    private static final Logger log = Logger.getLogger(SearchController.class);

    private SearchModel model;
    private SearchView view;

    /**
     * Ctor using fields
     * @param model
     * @param view
     */
    public SearchController(String name) {

        super();
        this.model = new SearchModel(name);
        this.view = new SearchView(this);
    }

    public SearchModel getModel() {

        return model;
    }

    public SearchView getView() {

        return view;
    }

    @Override
    public void onSearch(String source, String filter) {

        log.debug("onSearch source=" + source + " filter=" + filter);
    }

    @Override
    public void onBreak() {

        log.debug("onBreak");
    }
}
