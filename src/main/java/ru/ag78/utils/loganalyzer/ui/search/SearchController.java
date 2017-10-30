package ru.ag78.utils.loganalyzer.ui.search;

import java.util.List;

import org.apache.log4j.Logger;

import ru.ag78.utils.loganalyzer.ui.MainController;

public class SearchController implements SearchView.Events {

    private static final Logger log = Logger.getLogger(SearchController.class);

    private MainController mainCtrl;
    private SearchModel model;
    private SearchView view;

    /**
     * Ctor using fields
     * @param model
     * @param view
     */
    public SearchController(MainController mainCtrl, String name) {

        super();
        this.mainCtrl = mainCtrl;
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
    public void onSearch(String filter) {

        log.debug("onSearch source=" + model.getSelectedFileset() + " filter=" + filter);
    }

    @Override
    public void onBreak() {

        log.debug("onBreak");
    }

    /**
     * Renew list of available filesets for this SearchView.
     * 
     * @param filesets
     */
    public void setFilesets(List<String> filesets) {

        view.setFilesets(filesets);
    }

    @Override
    public void onSelectFileset(String name) {

        model.setSelectedFileset(name);
    }
}
