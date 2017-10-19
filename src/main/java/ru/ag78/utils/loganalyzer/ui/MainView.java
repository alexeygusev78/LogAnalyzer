package ru.ag78.utils.loganalyzer.ui;

import org.apache.log4j.Logger;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ru.ag78.utils.loganalyzer.ui.fileset.FilesetController;
import ru.ag78.utils.loganalyzer.ui.fileset.FilesetModel;
import ru.ag78.utils.loganalyzer.ui.fileset.FilesetView;

/**
 * UI Console for LogAnalyzer.
 * 
 * @author Алексей
 *
 */
public class MainView extends Application {

    private static final Logger log = Logger.getLogger(MainView.class);

    private MainController ctrl;
    private MainModel model;

    private FilesetController fsc;

    // ui controls
    private BorderPane mainLayout;
    private CheckMenuItem mnuShowFileset;

    /**
     * Default ctor, invoked from JavaFX.
     */
    public MainView() {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        String css = getClass().getResource("/main.css").toExternalForm();

        // init controller & view
        model = new MainModel();
        ctrl = new MainController(this, model);

        mainLayout = new BorderPane();
        Scene scene = new Scene(mainLayout, 1200, 800);
        scene.getStylesheets().add(css);
        Node topBar = initTop();

        // init fileset panel & data
        FilesetModel fsm = model.getFilesetModel();
        fsc = new FilesetController(new FilesetView(), fsm);
        FilesetView fsv = fsc.getView();

        mainLayout.setTop(topBar);
        mainLayout.setLeft(fsv.initView());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Node initTop() {

        VBox topBar = new VBox();
        topBar.getChildren().add(createMenu());
        topBar.getChildren().add(createToolBar());

        return topBar;
    }

    private MenuBar createMenu() {

        MenuBar bar = new MenuBar();
        bar.getMenus().addAll(createMenuFile(), createMenuView());

        return bar;
    }

    private Menu createMenuFile() {

        Menu menu = new Menu("File");

        MenuItem mnuFileset = new MenuItem("New fileset...");
        mnuFileset.setOnAction(t -> {
            ctrl.onNewFileset();
        });

        MenuItem mnuExit = new MenuItem("Exit");
        mnuExit.setOnAction(t -> {
            ctrl.onExit();
        });

        menu.getItems().addAll(mnuFileset, mnuExit);

        return menu;
    }

    private Menu createMenuView() {

        Menu menu = new Menu("View");
        mnuShowFileset = new CheckMenuItem("Fileset panel");
        model.showFilesetPanelProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                log.debug("showFileset menu changed. newValue={" + newValue + "}");
                mnuShowFileset.setSelected(newValue.booleanValue());
            }
        });

        menu.getItems().addAll(mnuShowFileset);

        return menu;
    }

    private HBox createToolBar() {

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
}
