package ru.ag78.utils.loganalyzer.ui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import ru.ag78.utils.loganalyzer.ui.fileset.FilesetModel;

public class MainModel {

    private BooleanProperty showFilesetPanel = new SimpleBooleanProperty();
    private FilesetModel filesetModel = new FilesetModel();

    public boolean isShowFilesetPanel() {

        return showFilesetPanel.get();
    }

    public void setShowFilesetPanel(boolean showFilesetPanel) {

        this.showFilesetPanel.set(showFilesetPanel);
    }

    public BooleanProperty showFilesetPanelProperty() {

        return showFilesetPanel;
    }

    public FilesetModel getFilesetModel() {

        return filesetModel;
    }

    public void setFilesetModel(FilesetModel filesetModel) {

        this.filesetModel = filesetModel;
    }
}
