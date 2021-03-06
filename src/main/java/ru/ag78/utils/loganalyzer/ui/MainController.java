package ru.ag78.utils.loganalyzer.ui;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import ru.ag78.utils.loganalyzer.config.Configuration;
import ru.ag78.utils.loganalyzer.config.Fileset;
import ru.ag78.utils.loganalyzer.config.LogFile;
import ru.ag78.utils.loganalyzer.ui.fileset.FilesetController;
import ru.ag78.utils.loganalyzer.ui.fileset.FilesetModel;
import ru.ag78.utils.loganalyzer.ui.fileset.LogFileItem;
import ru.ag78.utils.loganalyzer.ui.regexp.RegExpTestDialog;
import ru.ag78.utils.loganalyzer.ui.search.SearchController;

/**
 * Responsible for:
 * <ol><li>Receive client's actions (requests)</li>
 * <li>Analyze request</li>
 * <li>Next action in system</li></ol>
 * @author alexey
 *
 */
public class MainController implements MainView.Events, FilesetController.Events {

    private static final Logger log = Logger.getLogger(MainController.class);

    private MainView view;
    private MainModel model;

    private static MainController instance;

    private List<SearchController> searches = new LinkedList<>();

    /**
     * Ctor with params
     * @param view
     * @param model
     */
    public MainController(MainView view) throws Exception {

        super();
        this.instance = this;
        this.view = view;
        this.model = new MainModel();

        view.start(this);

        loadConfig(view);

        view.setTitle("LogAnalyzer v" + model.getVersion());
    }

    public static MainController getInstance() {

        return instance;
    }

    private void loadConfig(MainView view) throws Exception {

        model.loadConfig();
        for (Fileset fs: model.getConfig().getFilesets()) {
            FilesetController fsc = new FilesetController(fs.getName(), this);
            FilesetModel fsm = fsc.getModel();

            fsm.setName(fs.getName());
            fsm.setDescription(fs.getDescription());
            fsm.setPersist(true);

            for (LogFile lf: fs.getFiles()) {
                LogFileItem lfi = new LogFileItem();
                lfi.setChecked(true);
                lfi.setPath(lf.getPath());
                lfi.setEncoding(lf.getEncoding());
                fsm.addFile(lfi);
            }
            fsc.getView().setFileList(fsm.getFiles());

            model.addFileset(fsc);
            view.addFileSet(fsc.getView());
            fsc.getView().subscribeOnCloseTab(t -> onCloseFileset(t), fsc.getModel());
        }
        updateFsListInSearches();
    }

    public MainModel getModel() {

        return model;
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

        FilesetController fsc = new FilesetController(model.getNextFilesetName(), this);

        model.addFileset(fsc);
        view.addFileSet(fsc.getView());
        fsc.getView().subscribeOnCloseTab(t -> onCloseFileset(t), fsc.getModel());

        updateFsListInSearches();
    }

    private void onCloseFileset(FilesetModel fsModel) {

        log.debug(".onCloseFileset name=" + fsModel.getName());
        model.removeFileset(fsModel.getName());
        updateFsListInSearches();
    }

    private void updateFsListInSearches() {

        List<String> fsNames = model.getFilesetNames();
        // обновить во всех SearchView список доступных Fileset'ов
        for (SearchController sc: searches) {
            sc.setFilesets(fsNames);
        }
    }

    @Override
    public void onNewSearch() {

        log.debug(".onNewSearch");
        SearchController sc = new SearchController(this, model.getNextSearchName());
        addSearch(sc);
        sc.setFilesets(model.getFilesetNames());
    }

    @Override
    public void onRegExpTest() {

        log.debug(".onRegExpTest");
        RegExpTestDialog dlg = new RegExpTestDialog();
        dlg.show();
    }

    @Override
    public void onFilesetChanged(FilesetModel model) {

        updateFsListInSearches();
    }

    @Override
    public void onSaveConfig() {

        log.debug(".onSaveConfig");
        try {
            model.saveConfig();
            log.info("Configuration saved successfully.");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private Configuration loadConfig() {

        return new Configuration();
    }
}
