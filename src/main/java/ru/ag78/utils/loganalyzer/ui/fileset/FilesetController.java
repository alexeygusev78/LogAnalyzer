package ru.ag78.utils.loganalyzer.ui.fileset;

import java.io.File;
import java.io.IOException;
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

        //        model.addFile(new LogFileItem(true, "~/dev/logs/20171009/blog1.log"));
        //        model.addFile(new LogFileItem(true, "~/dev/logs/20171009/blog2.log"));
        //        model.addFile(new LogFileItem(true, "~/dev/logs/20171009/blog3.log"));
        //        model.addFile(new LogFileItem(true, "~/dev/logs/20171009/blog4.log"));
        //        model.addFile(new LogFileItem(true, "~/dev/logs/20171009/blog5.log"));

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
    public void onAddDir() {

        log.debug(".onAddDir");
    }

    @Override
    public void onAddFile() {

        log.debug(".onAddFile");

        List<File> files = view.requestFile();
        for (File f: files) {
            try {
                model.addFile(new LogFileItem(true, f.getCanonicalPath()));
                view.setFileList(model.getFiles());
            } catch (IOException e) {
                log.warn(e);
            }
        }
    }

    @Override
    public void onDeleteFile(LogFileItem item) {

        log.debug(".onDeleteFile item=" + item);
        model.deleteFile(item);
    }
}
