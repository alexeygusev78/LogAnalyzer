package ru.ag78.utils.loganalyzer.ui.search;

import java.util.List;
import java.util.Queue;
import java.util.function.Predicate;

import org.apache.log4j.Logger;

import ru.ag78.utils.loganalyzer.Token;
import ru.ag78.utils.loganalyzer.Tokenizable;
import ru.ag78.utils.loganalyzer.Tokenizer1;
import ru.ag78.utils.loganalyzer.logic.LogicParser;
import ru.ag78.utils.loganalyzer.ui.MainController;
import ru.ag78.utils.loganalyzer.ui.fileset.FilesetController;
import ru.ag78.utils.loganalyzer.ui.fileset.FilesetModel;
import ru.ag78.utils.loganalyzer.ui.fileset.LogFileItem;

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
    public void onSearch(String filter, int limit) {

        log.debug("onSearch source=" + model.getSelectedFileset() + " filter=" + filter + " limit=" + limit);

        try {
            String fsName = model.getSelectedFileset();
            FilesetController fsctrl = mainCtrl.getFilesetController(fsName);
            FilesetModel fsm = fsctrl.getModel();
            log.debug("Loaded Fileset name=" + fsm.getName());

            Tokenizable t = new Tokenizer1();
            Queue<Token> tokens = t.toTokens2(filter);

            LogicParser lp = new LogicParser();
            Predicate<String> p = lp.parse(tokens);

            StringBuilder sb = new StringBuilder();
            sb.append("Search string={" + filter + "}").append("\r\n");

            List<LogFileItem> files = fsm.getSelectedFiles();
            for (LogFileItem f: files) {
                log.debug("f=" + f.toString());
                model.search(sb, f, p, limit);
            }

            view.setSearchResult(sb.toString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
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
