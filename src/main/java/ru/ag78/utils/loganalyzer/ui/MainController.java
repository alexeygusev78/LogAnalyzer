package ru.ag78.utils.loganalyzer.ui;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import ru.ag78.utils.loganalyzer.ui.fileset.FilesetController;
import ru.ag78.utils.loganalyzer.ui.search.SearchController;

/**
 * Responsible for:
 * <ol><li>Receive client's actions (requests)</li>
 * <li>Analyze request</li>
 * <li>Next action in system</li></ol>
 * @author alexey
 *
 */
public class MainController implements MainViewEvents {

    private static final Logger log = Logger.getLogger(MainController.class);

    private MainView view;
    private MainModel model;

    private List<FilesetController> filesets = new LinkedList<>();
    private List<SearchController> searches = new LinkedList<>();

    /**
     * Ctor with params
     * @param view
     * @param model
     */
    public MainController(MainView view) {

        super();
        this.view = view;
        this.model = new MainModel();

        init();
    }

    /**
     * Initialize controller.
     * We assume that the MainView already initialized.
     */
    private void init() {

    }

    /**
     * Add fileset to fileset collection.
     * @param fsc
     */
    private void addFileset(FilesetController fsc) {

        filesets.add(fsc);
        view.addFileSet(fsc.getView().getRoot(), fsc.getModel().getName());
    }

    /**
     * Add search view to searches collection.
     * @param sc
     */
    private void addSearch(SearchController sc) {

        searches.add(sc);
        view.addSearch(sc.getView().getRoot(), sc.getModel().getName());
    }

    @Override
    public void onClose() {

        log.debug(".onClose");
        view.close();
    }

    @Override
    public void onNewFileset() {

        log.debug(".onNewFileset");

        FilesetController fsc = new FilesetController(model.getNextFilesetName());
        addFileset(fsc);
    }

    @Override
    public void onNewSearch() {

        log.debug(".onNewSearch");
        SearchController sc = new SearchController(model.getNextSearchName());
        addSearch(sc);
    }
}
