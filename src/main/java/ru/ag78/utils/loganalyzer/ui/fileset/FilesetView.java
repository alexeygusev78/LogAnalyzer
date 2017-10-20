package ru.ag78.utils.loganalyzer.ui.fileset;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Вьюха для панели Fileset
 * @author alexey
 *
 */
public class FilesetView {

    private Events eventListener;

    // UI controls
    private Node root;
    private Label labelTitle;
    private ListView<LogFile> listView;

    /**
     * События вьюхи Fileset
     * @author alexey
     *
     */
    public static interface Events {

        public void onAddDir();

        public void onAddFile();

        public void onDeleteFile(String filename);
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

        VBox vLayout = new VBox();
        vLayout.setId("fileset_bar");
        vLayout.getStyleClass().add("vbox");

        // title
        labelTitle = new Label();
        labelTitle.setId("title");

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
            eventListener.onAddFile();
        });

        Button btnDel = new Button("Delete");
        btnDel.setOnAction(t -> {
            eventListener.onDeleteFile("file");
        });
        // buttonProjected.setPrefSize(100, 20);
        toolbar.getChildren().addAll(btnAddDir, btnAddFile, btnDel);

        // listView
        listView = new ListView<LogFile>();

        vLayout.getChildren().addAll(labelTitle, toolbar, listView);

        return vLayout;
    }

    /**
     * Установить имя файлсета.
     * @param title
     */
    public void setTitle(String title) {

        labelTitle.setText(title);
    }

    /**
     * Установить коллекцию файлов для отображения в списке.
     * @param fileList
     */
    public void setFileList(List<LogFile> fileList) {

        ObservableList<LogFile> items = FXCollections.observableArrayList(fileList);

        listView.setItems(items);
    }

    /**
     * Request directory path from UI
     * @return
     */
    public String requestDirectory() {

        return "dir";
    }

    /**
     * Requeset file path from UI
     * @return
     */
    public String requestFile() {

        return "file";
    }
}
