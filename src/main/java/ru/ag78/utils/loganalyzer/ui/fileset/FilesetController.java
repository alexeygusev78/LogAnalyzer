package ru.ag78.utils.loganalyzer.ui.fileset;

import java.util.List;

import org.apache.log4j.Logger;

import javafx.collections.FXCollections;

public class FilesetController implements FilesetView.Events {

    private static final Logger log = Logger.getLogger(FilesetController.class);

    private FilesetView view;
    private FilesetModel model;

    /**
     * Ctor usign fields.
     * @param view
     * @param model
     */
    public FilesetController(String name) {

        super();
        this.view = new FilesetView(this);
        this.model = new FilesetModel(name);

        init();
    }

    private void init() {

        view.setFileList(FXCollections.observableList(model.getFiles()));
        view.setTitle(model.getName());
    }

    @Override
    public int hashCode() {

        return model.getName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        return model.getName().equals(obj);
    }

    public FilesetView getView() {

        return view;
    }

    public FilesetModel getModel() {

        return model;
    }

    @Override
    public void onListChange(List<LogFileItem> files) {

        model.setFiles(files);
    }

    @Override
    public void onCheck() {

        log.debug("onCheck");
        List<LogFileItem> files = model.getFiles();
        for (LogFileItem f: files) {
            log.debug("  f=" + f.toString());
        }
    }
}
