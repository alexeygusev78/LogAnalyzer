package ru.ag78.utils.loganalyzer.ui.fileset;

import java.io.File;
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

    /**
     * События вьюхи Fileset
     * @author alexey
     *
     */
    public static interface Events {

        public void onAddDir();

        public void onAddFile();

        public void onDeleteFile(LogFileItem item);
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
        btnAddDir.setOnAction(t -> {
            eventListener.onAddDir();
        });

        // btnShowFileset.setPrefSize(100, 20);
        Button btnAddFile = new Button("Add file");
        btnAddFile.setOnAction(t -> {
            // eventListener.onAddFile();
            itemsWrp.add(new LogFileItemWrp(new LogFileItem(false, "blog1.log")));
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
            onCheck();
        });

        // buttonProjected.setPrefSize(100, 20);
        toolbar.getChildren().addAll(btnAddDir, btnAddFile, btnDel, btnCheck);

        // listView
        tableView = initTableView();

        layout.setTop(toolbar);
        layout.setCenter(tableView);

        return layout;
    }

    private void onCheck() {

        log.debug("onCheck");
        for (LogFileItemWrp itm: items) {
            log.debug("  itm=" + itm.getItem().toString());
        }
    }

    private void onListChange() {

        log.debug("onListChange");
    }

    private TableView<LogFileItemWrp> initTableView() {

        itemsWrp = FXCollections.observableList(items);
        itemsWrp.addListener(new ListChangeListener<LogFileItemWrp>() {

            @Override
            public void onChanged(Change<? extends LogFileItemWrp> c) {

                onListChange();
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
        pathCol.setText("Path");
        pathCol.setCellValueFactory(new PropertyValueFactory<LogFileItemWrp, String>("path"));
        // firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));

        TableView<LogFileItemWrp> tv = new TableView<>();
        tv.setItems(itemsWrp);
        tv.setEditable(true);
        tv.getColumns().addAll(selectedCol, pathCol);

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

        // ObservableList<LogFile> items = FXCollections.observableArrayList(fileList);
        // this.fileList.clear();
        // this.fileList.addAll(fileList);
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
}
