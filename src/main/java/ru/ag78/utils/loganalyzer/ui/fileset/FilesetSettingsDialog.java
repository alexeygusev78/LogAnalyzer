package ru.ag78.utils.loganalyzer.ui.fileset;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class FilesetSettingsDialog {

    public boolean start(FilesetModel model, Window parent) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/filesetSettingsDialog.fxml"));
        Parent root = loader.load();
        FilesetSettingsController ctrl = (FilesetSettingsController) loader.getController();
        ctrl.setModel(model);

        Scene scene = new Scene(root, 500, 350);

        Stage dlg = new Stage();
        dlg.setTitle("Fileset settings");
        dlg.initOwner(parent);
        dlg.initStyle(StageStyle.UTILITY);
        dlg.setScene(scene);

        dlg.showAndWait();

        return ctrl.isOk();
    }
}
