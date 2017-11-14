package ru.ag78.utils.loganalyzer.ui;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import ru.ag78.utils.loganalyzer.ui.fileset.FilesetController;
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

    public MainModel getModel() {

        return model;
    }

    /**
     * Add fileset to fileset collection.
     * @param fsc
     */
    private void addFileset(final FilesetController fsc) {

        filesets.add(fsc);
        ContextMenu fsContextMenu = createFsContextMenu();
        view.addFileSet(fsc.getView().getRoot(), fsc.getModel().getName(), fsContextMenu);
        fsContextMenu.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                log.debug(".onSettings2 fileset=" + fsc.getModel().getName());
            }
        });

        List<String> fsNames = getFilesetNames();

        // обновить во всех SearchView список доступных Fileset'ов
        for (SearchController sc: searches) {
            sc.setFilesets(fsNames);
        }
    }

    private ContextMenu createFsContextMenu() {

        ContextMenu ctxMenu = new ContextMenu();
        MenuItem mnuSettings = new MenuItem("Settings...");
        ctxMenu.getItems().add(mnuSettings);
        return ctxMenu;
    }

    public FilesetController getFilesetController(String name) {

        for (FilesetController fsc: filesets) {
            if (fsc.getModel().getName().equalsIgnoreCase(name)) {
                return fsc;
            }
        }

        return null;
    }

    private List<String> getFilesetNames() {

        return filesets.stream().map(f -> f.getModel().getName()).collect(Collectors.toList());
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
        SearchController sc = new SearchController(this, model.getNextSearchName());
        addSearch(sc);
        sc.setFilesets(getFilesetNames());
    }

    @Override
    public void onRegExpTest() {

        log.debug(".onRegExpTest");
        RegExpTestDialog dlg = new RegExpTestDialog();
        dlg.show();
    }
}
