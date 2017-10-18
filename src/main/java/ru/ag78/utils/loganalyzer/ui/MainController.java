package ru.ag78.utils.loganalyzer.ui;

import org.apache.log4j.Logger;

/**
 * Responsible for:
 * <ol><li>Receive client's actions (requests)</li>
 * <li>Analyze request</li>
 * <li>Next action in system</li></ol>
 * @author alexey
 *
 */
public class MainController {

    private static final Logger log = Logger.getLogger(MainController.class);

    private MainModel model;

    /**
     * Ctor with params
     * @param view
     * @param model
     */
    public MainController(MainView view, MainModel model) {

        super();
        this.model = model;
    }

    public void onNewFileset() {

        log.debug("onNewFileset");
    }

    public void onExit() {

        log.debug("onExit");
    }
}
