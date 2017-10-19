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
import javafx.scene.layout.Region;
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
    private Stage stage;
    private BorderPane mainLayout;
    private CheckMenuItem mnuShowFileset;
    private TabPane filesetPane;

    /**
     * Default ctor, invoked from JavaFX.
     */
    public MainView() {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

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

        // init fileset panel & data
        //        FilesetModel fsm = model.getFilesetModel();
        //        fsc = new FilesetController(new FilesetView(), fsm);
        //        FilesetView fsv = fsc.getView();

        // init Fileset Pane
        Node filesetPane = initFilesetPane();

        // init SearchView
        Node searchView = initSearchView();

        // set layout
        splitPane.getItems().addAll(filesetPane, searchView);
        mainLayout.setTop(topBar);
        mainLayout.setCenter(splitPane);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Region initFilesetPane() {

        filesetPane = new TabPane();

        return filesetPane;
    }

    private Node initSearchView() {

        return new Region();
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
        MenuItem mnuFileset = new MenuItem("New fileset...");
        mnuFileset.setOnAction(t -> {
            eventListener.onNewFileset();
        });
        MenuItem mnuExit = new MenuItem("Exit");
        mnuExit.setOnAction(t -> {
            eventListener.onClose();
        });
        mnuFile.getItems().addAll(mnuFileset, mnuExit);

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
        Button btnShowFileset = new Button("Fileset");
        btnShowFileset.setPrefSize(100, 20);
        Button buttonProjected = new Button("Projected");
        buttonProjected.setPrefSize(100, 20);
        hbox.getChildren().addAll(btnShowFileset, buttonProjected);
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
     * Закрыть окно приложения
     */
    public void close() {

        stage.close();
    }

}
