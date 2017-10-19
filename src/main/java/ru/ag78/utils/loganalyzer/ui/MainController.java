package ru.ag78.utils.loganalyzer.ui;

import org.apache.log4j.Logger;

import ru.ag78.utils.loganalyzer.ui.fileset.FilesetController;

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

    /**
     * Ctor with params
     * @param view
     * @param model
     */
    public MainController(MainView view) {

        super();
        this.view = view;
        this.model = new MainModel();
    }

    @Override
    public void onClose() {

        log.debug(".onClose");
        view.close();
    }

    @Override
    public void onNewFileset() {

        log.debug(".onNewFileset");
        FilesetController ctrl = new FilesetController();

        view.addFileSet(ctrl.getView().getRoot(), "default");
    }
}
