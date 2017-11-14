package ru.ag78.utils.loganalyzer.ui.fileset.settings;

import org.apache.log4j.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FilesetSettingsDialog {

    private static final Logger log = Logger.getLogger(FilesetSettingsDialog.class);

    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("filesetSettingsDialog.fxml"));
        Scene scene = new Scene(root, 300, 275);
        stage.setTitle("Fileset settings");
        stage.setScene(scene);
        stage.showAndWait();
    }
}
