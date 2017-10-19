package ru.ag78.utils.loganalyzer.ui.fileset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class FilesetView {

    private FilesetController ctrl;

    // UI controls
    private Label title;

    /**
     * Default ctor
     */
    public FilesetView() {

    }

    /**
     * Ctor with parameters
     * @param ctrl
     */
    public FilesetView(FilesetController ctrl) {

        this.ctrl = ctrl;
    }

    public FilesetController getCtrl() {

        return ctrl;
    }

    public void setCtrl(FilesetController ctrl) {

        this.ctrl = ctrl;
    }

    public Node initView() {

        VBox vLayout = new VBox();
        vLayout.setId("fileset_bar");
        vLayout.getStyleClass().add("vbox");

        title = new Label("Наборы файлов");
        title.setId("label1");

        vLayout.getChildren().add(title);

        return vLayout;
    }
}
