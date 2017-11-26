package ru.ag78.utils.loganalyzer.ui;

import org.apache.log4j.Logger;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ru.ag78.utils.loganalyzer.ui.fileset.FilesetView;

/**
 * UI Console for LogAnalyzer.
 * 
 * @author Алексей
 *
 */
public class MainView {

    private static final Logger log = Logger.getLogger(MainView.class);

    // main MVC
    private Events eventListener;

    // ui controls
    private static Stage mainStage;
    private BorderPane mainLayout;
    private CheckMenuItem mnuShowFileset;
    private TabPane filesetPane;
    private TabPane searchPane;

    /**
     * Events interface
     * @author alexey
     *
     */
    public interface Events {

        public void onNewFileset();

        public void onNewSearch();

        public void onRegExpTest();

        public void onClose();

        public void onSaveConfig();
    }

    /**
     * Default ctor, invoked from JavaFX.
     */
    public MainView(Stage mainStage) {

        MainView.mainStage = mainStage;
    }

    public void start(Events eventListener) throws Exception {

        this.eventListener = eventListener;

        String css = getClass().getResource("/main.css").toExternalForm();

        // init controller & view
        mainLayout = new BorderPane();
        Scene scene = new Scene(mainLayout, 800, 600);
        scene.getStylesheets().add(css);
        SplitPane splitPane = new SplitPane();

        // init menu & toolbar
        Node topBar = initTop();

        // init Fileset Pane
        filesetPane = new TabPane();
        // filesetPane.setContextMenu(initFilesetContextMenu());

        // init Search Pane
        searchPane = new TabPane();

        // set layout
        splitPane.getItems().addAll(filesetPane, searchPane);
        mainLayout.setTop(topBar);
        mainLayout.setCenter(splitPane);

        mainStage.setTitle("LogAnalyzer v1.3.2");
        mainStage.setScene(scene);
        mainStage.show();
    }

    public static Stage getMainStage() {

        return mainStage;
    }

    private Node initSearchPane() {

        searchPane = new TabPane();

        return searchPane;
    }

    /**
     * Инициализировать меню и тулбар
     * @return
     */
    private Node initTop() {

        VBox topBar = new VBox();
        topBar.getChildren().add(initMenu());
        topBar.getChildren().add(initToolBar());

        return topBar;
    }

    private MenuBar initMenu() {

        MenuBar bar = new MenuBar();

        // File
        Menu mnuFile = new Menu("File");
        MenuItem mnuNewFileset = new MenuItem("New fileset...");
        mnuNewFileset.setOnAction(t -> {
            eventListener.onNewFileset();
        });

        MenuItem mnuNewSearch = new MenuItem("New search");
        mnuNewSearch.setOnAction(t -> {
            eventListener.onNewSearch();
        });

        MenuItem mnuSave = new MenuItem("Save");
        mnuSave.setOnAction(t -> {
            eventListener.onSaveConfig();
        });

        MenuItem mnuExit = new MenuItem("Exit");
        mnuExit.setOnAction(t -> {
            eventListener.onClose();
        });
        mnuFile.getItems().addAll(mnuNewFileset, mnuNewSearch, mnuExit);

        // View
        Menu mnuView = new Menu("View");
        mnuShowFileset = new CheckMenuItem("Fileset panel");
        mnuView.getItems().addAll(mnuShowFileset);

        Menu mnuHelp = new Menu("Help");
        MenuItem mnuAbout = new MenuItem("About ...");
        mnuAbout.setOnAction(t -> {
            showAbout();
        });
        mnuHelp.getItems().add(mnuAbout);

        // Add to main menu
        bar.getMenus().addAll(mnuFile, mnuView, mnuHelp);

        return bar;
    }

    /**
     * Shows "About" modal dialog.
     */
    private void showAbout() {

        log.debug("showAbout");

        try {
            AboutDialog.start(MainView.getMainStage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private HBox initToolBar() {

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(2, 2, 2, 2));
        hbox.getStyleClass().add("hbox");
        Button btnNewFileset = new Button("New Fileset");
        btnNewFileset.setPrefSize(100, 20);
        btnNewFileset.setOnAction(t -> {
            eventListener.onNewFileset();
        });

        Button btnNewSearch = new Button("New Search");
        btnNewSearch.setPrefSize(100, 20);
        btnNewSearch.setOnAction(t -> {
            eventListener.onNewSearch();
        });

        Button btnRegExpTest = new Button("RegEx Test...");
        btnRegExpTest.setOnAction(t -> {
            eventListener.onRegExpTest();
        });

        Button btnSave = new Button("Save");
        btnSave.setOnAction(t -> {
            eventListener.onSaveConfig();
        });

        hbox.getChildren().addAll(btnNewFileset, btnNewSearch, btnSave, btnRegExpTest);
        return hbox;
    }

    /**
     * Добавить вьюху Fileset
     * @param fsv
     * @param name
     */
    public void addFileSet(FilesetView fsv) {

        filesetPane.getTabs().add(fsv.getTab());
    }

    /**
     * Добавить вьюху Search
     * @param sv
     * @param name
     */
    public void addSearch(Node sv, String name) {

        Tab tab = new Tab();
        tab.setContent(sv);
        tab.setText(name);

        searchPane.getTabs().add(tab);
    }

    /**
     * Закрыть окно приложения
     */
    public void close() {

        mainStage.close();
    }
}
