package ru.ag78.utils.loganalyzer.ui.fileset;

import org.apache.log4j.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class FilesetSettingsDialog {

    private static final Logger log = Logger.getLogger(FilesetSettingsDialog.class);

    private FilesetModel model;

    //    @FXML
    //    private TextField txtName;

    @FXML
    private SimpleStringProperty name = new SimpleStringProperty("abc");

    @FXML
    private SimpleStringProperty description = new SimpleStringProperty("descr");

    @FXML
    private TextField txtDescription;

    @FXML
    private CheckBox chkPersistent;

    private Stage dlg;

    private boolean result;

    /**
     * Ctor with parameters
     * @param model
     */
    public FilesetSettingsDialog() throws Exception {

    }

    public boolean start(FilesetModel model, Window parent) throws Exception {

        this.model = model;

        Parent root = FXMLLoader.load(getClass().getResource("/filesetSettingsDialog.fxml"));
        Scene scene = new Scene(root, 500, 350);

        dlg = new Stage();
        dlg.setTitle("Fileset settings");
        dlg.initOwner(parent);
        dlg.initStyle(StageStyle.UTILITY);
        dlg.setScene(scene);

        name.setValue(model.getName());
        //        description.setValue(model.getDescription());

        dlg.show();

        return result;
    }

    @FXML
    private void onOK(ActionEvent actionEvent) {

        // log.debug(".onOK txtName=" + txtName.getText() + " txtDescription=" + txtDescription.getText());
        String newName = name.getValue();
        model.setName(newName);
        // model.setDescription(description.getValue());

        result = true;
        Node n = (Node) actionEvent.getSource();
        n.getScene().getWindow().hide();
    }

    @FXML
    private void onCancel(ActionEvent actionEvent) {

        log.debug(".onCancel");

        result = false;
        Node n = (Node) actionEvent.getSource();
        n.getScene().getWindow().hide();
    }

    public String getName() {

        return name.getValue();
    }

    public void setName(String name) {

        this.name.setValue(name);
    }

    public StringProperty getNameProperty() {

        return name;
    }

    public String getDescription() {

        return description.getValue();
    }

    public void setDescription(String description) {

        this.description.setValue(description);
    }

    public StringProperty getDescriptionProperty() {

        return description;
    }
}
