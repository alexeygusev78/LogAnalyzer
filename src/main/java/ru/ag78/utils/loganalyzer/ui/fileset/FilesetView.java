package ru.ag78.utils.loganalyzer.ui.fileset;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

/**
 * Вьюха для панели Fileset
 * @author alexey
 *
 */
public class FilesetView {

    private Events eventListener;

    // local data
    private String title;

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

    }

    /**
     * Ctor with parameters
     * @param ctrl
     */
    public FilesetView(FilesetView.Events eventListener, String title) {

        this.eventListener = eventListener;
        this.title = title;
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
        labelTitle = new Label(title);
        labelTitle.setId("title");

        // listView
        listView = new ListView<LogFile>();

        vLayout.getChildren().add(labelTitle);
        vLayout.getChildren().add(listView);

        return vLayout;
    }

    /**
     * Установить имя файлсета.
     * @param title
     */
    public void setTitle(String title) {

        this.title = title;
        labelTitle.setText(title);
    }

    /**
     * Установить коллекцию файлов для отображения в списке.
     * @param fileList
     */
    public void setFileList(List<LogFile> fileList) {

        listView.setItems(FXCollections.observableArrayList(fileList));
    }
}
