package ru.ag78.utils.loganalyzer.ui.fileset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class FilesetView {

    private FilesetController ctrl;

    // mount CSS
    String css;

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

        String css = getClass().getResource("/main.css").toExternalForm();
        System.out.println("css={" + css + "}");

        VBox vLayout = new VBox();
        vLayout.setId("fileset_bar");
        vLayout.getStyleClass().add("vbox");
        vLayout.getStylesheets().add(css);

        title = new Label("Наборы файлов");
        title.setId("label1");
        title.getStylesheets().add(css);

        vLayout.getChildren().add(title);

        return vLayout;
    }
}
