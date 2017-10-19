package ru.ag78.utils.loganalyzer.ui.fileset;

public class FilesetController implements FilesetView.Events {

    private FilesetView view;
    private FilesetModel model;

    /**
     * Ctor usign fields.
     * @param view
     * @param model
     */
    public FilesetController() {

        super();
        this.view = new FilesetView(this, "default");
        this.model = new FilesetModel();

        model.addFile(new LogFile(true, "~/dev/logs/20171009/blog1.log"));
        model.addFile(new LogFile(true, "~/dev/logs/20171009/blog2.log"));
        model.addFile(new LogFile(true, "~/dev/logs/20171009/blog3.log"));
        model.addFile(new LogFile(true, "~/dev/logs/20171009/blog4.log"));
        model.addFile(new LogFile(true, "~/dev/logs/20171009/blog5.log"));
    }

    public FilesetView getView() {

        return view;
    }

    public FilesetModel getModel() {

        return model;
    }
}
