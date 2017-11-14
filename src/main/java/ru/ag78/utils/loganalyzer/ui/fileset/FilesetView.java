package ru.ag78.utils.loganalyzer.ui.fileset;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import ru.ag78.utils.loganalyzer.ui.fileset.settings.FilesetSettingsDialog;

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

    /**
     * События вьюхи Fileset
     * @author alexey
     *
     */
    public static interface Events {

        public void onListChange(List<LogFileItem> files);

        public void onCheck();
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
        toolbar.setPadding(new Insets(2, 2, 2, 2)); // new Insets(15, 12, 15, 12)
        // hbox.setSpacing(10);
        // hbox.setStyle("-fx-background-color: #336699;");
        Button btnAddDir = new Button("Add dir");

        btnAddDir.setDisable(true);
        btnAddDir.setOnAction(t -> {
            onAddDir();
        });

        // btnShowFileset.setPrefSize(100, 20);
        Button btnAddFile = new Button("Add file");
        btnAddFile.setOnAction(t -> {
            onAddFile();
        });

        Button btnDel = new Button("Delete");
        btnDel.setOnAction(t -> {
            LogFileItemWrp selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                log.debug("onDelete selected=" + selected.getItem().toString());
                itemsWrp.remove(selected);
                // eventListener.onDeleteFile(selected);
            }
        });

        // btnShowFileset.setPrefSize(100, 20);
        Button btnCheck = new Button("Check");
        btnCheck.setOnAction(t -> {
            invokeOnCheck();
        });

        // buttonProjected.setPrefSize(100, 20);
        toolbar.getChildren().addAll(btnAddDir, btnAddFile, btnDel, btnCheck);

        // listView
        tableView = initTableView();

        layout.setTop(toolbar);
        layout.setCenter(tableView);

        return layout;
    }

    private void invokeOnCheck() {

        log.debug("invokeOnCheck");
        eventListener.onCheck();
        //        for (LogFileItemWrp itm: items) {
        //            log.debug("  itm=" + itm.getItem().toString());
        //        }
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
    public void setTitle(String title) {

        // labelTitle.setText(title);
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

    public void showSettings() {

        try {
            log.debug(".showSettings");

            Stage mainStage = MainView.getMainStage();
            FilesetSettingsDialog dlg = new FilesetSettingsDialog();
            dlg.start(mainStage);
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
    }
}
