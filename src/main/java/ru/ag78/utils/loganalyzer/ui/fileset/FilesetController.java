package ru.ag78.utils.loganalyzer.ui.fileset;

import java.util.List;

import org.apache.log4j.Logger;

import javafx.collections.FXCollections;
import ru.ag78.utils.loganalyzer.ui.MainView;

public class FilesetController implements FilesetView.Events {

    private static final Logger log = Logger.getLogger(FilesetController.class);

    private FilesetView view;
    private FilesetModel model;

    private Events eventListener;

    public interface Events {

        public void onFilesetChanged(FilesetModel model);
    }

    /**
     * Ctor usign fields.
     * @param view
     * @param model
     */
    public FilesetController(String name, Events eventListener) {

        super();
        this.eventListener = eventListener;
        this.view = new FilesetView(this);
        this.model = new FilesetModel(name);

        init();
    }

    private void init() {

        view.setFileList(FXCollections.observableList(model.getFiles()));
        view.setTitle(model.getName(), model.getDescription());

        // view.subscribeOnCloseTab(t -> this.onClosed());
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
    public void onSettings() {

        log.debug(".onSettings name=" + model.getName());
        try {
            log.debug(".showSettings");

            FilesetSettingsDialog dlg = new FilesetSettingsDialog();
            if (dlg.start(model, MainView.getMainStage())) {
                view.setTitle(model.getName(), model.getDescription());
                eventListener.onFilesetChanged(model);
            }
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
    }

    @Override
    public void onSelect(final boolean checked) {

        model.getFiles().stream().forEach(i -> i.setChecked(checked));
        view.setFileList(FXCollections.observableList(model.getFiles()));
    }

    @Override
    public void onInvertSelection() {

        model.getFiles().stream().forEach(i -> i.setChecked(!i.isChecked()));
        view.setFileList(FXCollections.observableList(model.getFiles()));
    }

    //    public void onClosed() {
    //
    //        log.debug(".onClosed filesetname=" + model.getName());
    //    }
}
