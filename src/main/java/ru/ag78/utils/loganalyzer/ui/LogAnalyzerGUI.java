package ru.ag78.utils.loganalyzer.ui;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Entry-point for gui-based LogAnalyzer.
 * @author alexey
 *
 */
public class LogAnalyzerGUI extends Application {

    public static void main(String[] args) {

        Application.launch(LogAnalyzerGUI.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        MainView view = new MainView(primaryStage);
        MainController ctrl = new MainController(view);
    }
}
