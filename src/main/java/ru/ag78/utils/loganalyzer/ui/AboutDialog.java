package ru.ag78.utils.loganalyzer.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class AboutDialog {

    public static boolean start(Window parent) throws Exception {

        FXMLLoader loader = new FXMLLoader(AboutDialog.class.getResource("/aboutDialog.fxml"));
        Parent root = loader.load();
        // AboutDialog ctrl = (AboutDialog) loader.getController();

        Scene scene = new Scene(root, 500, 350);

        Stage dlg = new Stage();
        dlg.setTitle("About");
        dlg.initOwner(parent);
        dlg.initStyle(StageStyle.UTILITY);
        dlg.setScene(scene);

        dlg.showAndWait();

        return true;
    }

}
