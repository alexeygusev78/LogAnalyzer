package ru.ag78.utils.loganalyzer.ui.fileset;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

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

        model.addFile(new LogFile(true, "~/dev/logs/20171009/blog1.log"));
        model.addFile(new LogFile(true, "~/dev/logs/20171009/blog2.log"));
        model.addFile(new LogFile(true, "~/dev/logs/20171009/blog3.log"));
        model.addFile(new LogFile(true, "~/dev/logs/20171009/blog4.log"));
        model.addFile(new LogFile(true, "~/dev/logs/20171009/blog5.log"));

        init();
    }

    private void init() {

        view.setFileList(model.getFiles());
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
                model.addFile(new LogFile(true, f.getCanonicalPath()));
            } catch (IOException e) {
                log.warn(e);
            }
        }
    }

    @Override
    public void onDeleteFile(LogFile item) {

        log.debug(".onDeleteFile item=" + item);
        model.deleteFile(item);
    }
}
