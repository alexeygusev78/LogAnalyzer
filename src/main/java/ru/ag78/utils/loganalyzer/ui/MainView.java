package ru.ag78.utils.loganalyzer.ui;

import org.apache.log4j.Logger;

import javafx.application.Application;
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

/**
 * UI Console for LogAnalyzer.
 * 
 * @author Алексей
 *
 */
public class MainView extends Application {

    private static final Logger log = Logger.getLogger(MainView.class);

    // main MVC
    private MainViewEvents eventListener;

    // ui controls
    private static Stage mainStage;
    private Stage stage;
    private BorderPane mainLayout;
    private CheckMenuItem mnuShowFileset;
    private TabPane filesetPane;
    private TabPane searchPane;

    /**
     * Default ctor, invoked from JavaFX.
     */
    public MainView() {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        MainView.mainStage = primaryStage;
        this.stage = primaryStage;
        String css = getClass().getResource("/main.css").toExternalForm();

        // init controller & view
        eventListener = new MainController(this);

        mainLayout = new BorderPane();
        Scene scene = new Scene(mainLayout, 800, 600);
        scene.getStylesheets().add(css);
        SplitPane splitPane = new SplitPane();

        // init menu & toolbar
        Node topBar = initTop();

        // init Fileset Pane
        filesetPane = new TabPane();

        // init Search Pane
        searchPane = new TabPane();

        // set layout
        splitPane.getItems().addAll(filesetPane, searchPane);
        mainLayout.setTop(topBar);
        mainLayout.setCenter(splitPane);

        primaryStage.setScene(scene);
        primaryStage.show();
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

        MenuItem mnuExit = new MenuItem("Exit");
        mnuExit.setOnAction(t -> {
            eventListener.onClose();
        });
        mnuFile.getItems().addAll(mnuNewFileset, mnuNewSearch, mnuExit);

        // View
        Menu mnuView = new Menu("View");
        mnuShowFileset = new CheckMenuItem("Fileset panel");
        mnuView.getItems().addAll(mnuShowFileset);

        // Add to main menu
        bar.getMenus().addAll(mnuFile, mnuView);

        return bar;
    }

    private HBox initToolBar() {

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(2, 2, 2, 2)); // new Insets(15, 12, 15, 12)
        // hbox.setSpacing(10);
        // hbox.setStyle("-fx-background-color: #336699;");
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

        hbox.getChildren().addAll(btnNewFileset, btnNewSearch);
        return hbox;
    }

    /**
     * Добавить вьюху Fileset
     * @param fsv
     * @param name
     */
    public void addFileSet(Node fsv, String name) {

        Tab tab = new Tab();
        tab.setContent(fsv);
        tab.setText(name);

        filesetPane.getTabs().add(tab);
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

        stage.close();
    }

}
