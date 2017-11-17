package ru.ag78.utils.loganalyzer.ui.fileset;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import ru.ag78.utils.loganalyzer.ui.MainView;

/**
 * Вьюха для панели Fileset
 * @author alexey
 *
 */
public class FilesetView {

    private static final Logger log = Logger.getLogger(FilesetView.class);

    private Events eventListener;

    // UI controls
    private Node root;
    private TableView<LogFileItemWrp> tableView;
    private List<LogFileItemWrp> items = new LinkedList<LogFileItemWrp>();
    private ObservableList<LogFileItemWrp> itemsWrp;
    private Tab tab;
    private Tooltip tooltip;

    /**
     * События вьюхи Fileset
     * @author alexey
     *
     */
    public static interface Events {

        public void onListChange(List<LogFileItem> files);

        public void onSettings();

        public void onSelect(boolean checked);

        public void onInvertSelection();
    }

    /**
     * Ctor with parameters
     * @param ctrl
     */
    public FilesetView(FilesetView.Events eventListener) {

        this.eventListener = eventListener;

        root = getRoot();
    }

    public Node getRoot() {

        if (root == null) {
            root = initView();
        }

        return root;
    }

    private Node initView() {

        BorderPane layout = new BorderPane();
        layout.setId("fileset_bar");
        layout.getStyleClass().add("vbox");

        HBox toolbar = new HBox();
        toolbar.setPadding(new Insets(2, 2, 2, 2));
        Button btnAddDir = new Button("Add dir");

        btnAddDir.setDisable(true);
        btnAddDir.setOnAction(t -> {
            onAddDir();
        });

        // btnShowFileset.setPrefSize(100, 20);
        Button btnAddFile = new Button("Add file");
        btnAddFile.setTooltip(new Tooltip("Add file(s)"));
        btnAddFile.setOnAction(t -> {
            onAddFile();
        });

        Button btnDel = new Button("Delete");
        btnDel.setTooltip(new Tooltip("Delete selected item(s)"));
        btnDel.setOnAction(t -> {
            LogFileItemWrp selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                log.debug("onDelete selected=" + selected.getItem().toString());
                itemsWrp.remove(selected);
            }
        });

        // select all button
        Button btnPlus = new Button("+");
        btnPlus.setTooltip(new Tooltip("Check all items"));
        btnPlus.setOnAction(t -> {
            invokeOnPlus();
        });

        // deselect all button
        Button btnMinus = new Button("-");
        btnMinus.setTooltip(new Tooltip("Uncheck all items"));
        btnMinus.setOnAction(t -> {
            invokeOnMinus();
        });

        // invert selection button
        Button btnAsterisk = new Button("*");
        btnAsterisk.setTooltip(new Tooltip("Invert checking items"));
        btnAsterisk.setOnAction(t -> {
            invokeOnAsterisk();
        });

        toolbar.getChildren().addAll(btnAddDir, btnAddFile, btnDel, btnPlus, btnMinus, btnAsterisk);

        // listView
        tableView = initTableView();

        layout.setTop(toolbar);
        layout.setCenter(tableView);

        tooltip = new Tooltip();

        // create tab for this view
        tab = new Tab();
        tab.setContent(layout);
        tab.setText("");
        tab.setTooltip(tooltip);
        tab.setContextMenu(createFsContextMenu());

        return layout;
    }

    private void invokeOnDeleteTab() {

    }

    private void invokeOnAsterisk() {

        eventListener.onInvertSelection();
    }

    private void invokeOnMinus() {

        eventListener.onSelect(false);
    }

    private void invokeOnPlus() {

        eventListener.onSelect(true);
    }

    private void invokeOnListChange() {

        log.debug("invokeOnListChange");
        List<LogFileItem> files = new LinkedList<>();
        items.stream().forEach(i -> files.add(i.getItem()));

        eventListener.onListChange(files);
    }

    private TableView<LogFileItemWrp> initTableView() {

        itemsWrp = FXCollections.observableList(items);
        itemsWrp.addListener(new ListChangeListener<LogFileItemWrp>() {

            @Override
            public void onChanged(Change<? extends LogFileItemWrp> c) {

                invokeOnListChange();
            }
        });

        StringConverter<Object> sc = new StringConverter<Object>() {

            @Override
            public String toString(Object t) {

                return t == null ? null : t.toString();
            }

            @Override
            public Object fromString(String string) {

                return string;
            }
        };

        TableColumn<LogFileItemWrp, Boolean> selectedCol = new TableColumn<LogFileItemWrp, Boolean>();
        selectedCol.setText("Use");
        selectedCol.setMinWidth(70);
        selectedCol.setCellValueFactory(new PropertyValueFactory<LogFileItemWrp, Boolean>("checked"));
        selectedCol.setCellFactory(CheckBoxTableCell.forTableColumn(selectedCol));

        TableColumn<LogFileItemWrp, String> pathCol = new TableColumn<LogFileItemWrp, String>();
        pathCol.setText("File");
        pathCol.setCellValueFactory(new PropertyValueFactory<LogFileItemWrp, String>("file"));
        // firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));

        TableColumn<LogFileItemWrp, String> encodingCol = new TableColumn<LogFileItemWrp, String>();
        encodingCol.setText("Encoding");
        encodingCol.setCellValueFactory(new PropertyValueFactory<LogFileItemWrp, String>("encoding"));
        encodingCol.setCellFactory(TextFieldTableCell.forTableColumn());

        TableView<LogFileItemWrp> tv = new TableView<>();
        tv.setItems(itemsWrp);
        tv.setEditable(true);
        tv.getColumns().addAll(selectedCol, pathCol, encodingCol);

        return tv;
    }

    /**
     * Установить имя файлсета.
     * @param title
     */
    public void setTitle(String title, String tooltip) {

        tab.setText(title);
        this.tooltip.setText(tooltip);
    }

    /**
     * Установить коллекцию файлов для отображения в списке.
     * @param fileList
     */
    public void setFileList(List<LogFileItem> fileList) {

        itemsWrp.clear();
        fileList.stream().forEach(i -> itemsWrp.add(new LogFileItemWrp(i)));
    }

    /**
     * Request directory path from UI
     * @return
     */
    public String requestDirectory() {

        return "dir";
    }

    /**
     * Requeset file objects.
     * Never returns null.
     * @return
     */
    public List<File> requestFile() {

        List<File> files = new LinkedList<>();
        try {
            Stage mainStage = MainView.getMainStage();

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Log File");
            fileChooser.getExtensionFilters().addAll(
                            new ExtensionFilter("Log Files", "*.log", "*log*.*"),
                            new ExtensionFilter("All Files", "*.*"));
            List<File> selectedFiles = fileChooser.showOpenMultipleDialog(mainStage);
            if (selectedFiles != null && !selectedFiles.isEmpty()) {
                files.addAll(selectedFiles);
            }
        } catch (Exception e) {
            log.error(e);
        }
        return files;
    }

    private void onAddFile() {

        log.debug(".onAddFile");
        List<File> files = requestFile();
        for (File f: files) {
            try {
                itemsWrp.add(new LogFileItemWrp(new LogFileItem(true, f.getCanonicalPath())));
            } catch (IOException e) {
                log.warn(e);
            }
        }
    }

    private void onAddDir() {

    }

    private ContextMenu createFsContextMenu() {

        ContextMenu mnu = new ContextMenu();
        MenuItem mnuSettings = new MenuItem("Settings...");
        mnu.getItems().add(mnuSettings);

        mnu.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                eventListener.onSettings();
            }
        });

        return mnu;
    }

    public Tab getTab() {

        return tab;
    }

    /**
     * Subscribe client to closeTab events.
     * Model will be supplied to the consumer when the event occurs.
     * @param consumer
     * @param model
     */
    public void subscribeOnCloseTab(final Consumer<FilesetModel> consumer, final FilesetModel model) {

        tab.setOnClosed(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {

                consumer.accept(model);
            }
        });
    }
}
