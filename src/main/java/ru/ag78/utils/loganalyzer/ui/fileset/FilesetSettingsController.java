package ru.ag78.utils.loganalyzer.ui.fileset;

import org.apache.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class FilesetSettingsController {

    private static final Logger log = Logger.getLogger(FilesetSettingsController.class);

    private boolean ok;
    private FilesetModel model;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtDescription;

    @FXML
    private CheckBox chkPersistent;

    @FXML
    private void onOK(ActionEvent actionEvent) {

        log.debug(".onOK name=" + txtName.getText() + " description=" + txtDescription.getText() + " persist=" + chkPersistent.isSelected());
        model.setName(txtName.getText());
        model.setDescription(txtDescription.getText());
        model.setPersist(chkPersistent.isSelected());

        ok = true;
        Node n = (Node) actionEvent.getSource();
        n.getScene().getWindow().hide();
    }

    @FXML
    private void onCancel(ActionEvent actionEvent) {

        log.debug(".onCancel");

        ok = false;
        Node n = (Node) actionEvent.getSource();
        n.getScene().getWindow().hide();
    }

    public void setModel(FilesetModel model) {

        this.model = model;
        txtName.setText(model.getName());
        txtDescription.setText(model.getDescription());
        chkPersistent.setSelected(model.isPersist());
    }

    public boolean isOk() {

        return ok;
    }
}
