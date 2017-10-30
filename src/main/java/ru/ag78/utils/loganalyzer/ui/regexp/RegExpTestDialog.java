package ru.ag78.utils.loganalyzer.ui.regexp;

import java.util.Queue;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.ag78.utils.loganalyzer.LogicParser;
import ru.ag78.utils.loganalyzer.Tokenizable;
import ru.ag78.utils.loganalyzer.TokenizerCustom;

public class RegExpTestDialog {

    private static final Logger log = Logger.getLogger(RegExpTestDialog.class);

    private TextField textRegExp;
    private TextField textQuery;
    private TextArea areaResults;

    public void show() {

        final Stage stage = new Stage();

        BorderPane mainLayout = new BorderPane();

        Scene scene = new Scene(mainLayout, 900, 400, Color.WHITESMOKE);
        stage.setScene(scene);
        stage.setTitle("RegExp Test Tool");
        stage.centerOnScreen();
        stage.show();

        textRegExp = new TextField("abcd");
        textQuery = new TextField();
        areaResults = new TextArea();

        Text text = new Text(20, 110, "JavaFX");
        text.setFill(Color.DODGERBLUE);
        text.setEffect(new Lighting());
        text.setFont(Font.font(Font.getDefault().getFamily(), 50));

        VBox topBars = new VBox();
        // topBars.getChildren().add(initButtonPane());
        topBars.getChildren().add(initRegExpPane());
        topBars.getChildren().add(initQueryPane());

        mainLayout.setTop(topBars);
        mainLayout.setCenter(areaResults);

        //add text to the main root group
        // rootGroup.getChildren().add(mainLayout);

    }

    private Node initQueryPane() {

        BorderPane layout = new BorderPane();

        layout.setLeft(new Label("Query:"));
        textQuery = new TextField();
        textQuery.setText("wolf");
        layout.setCenter(textQuery);

        Button btnTest1 = new Button("Search");
        btnTest1.setOnAction(t -> {
            log.debug("onSearch");
        });
        layout.setRight(btnTest1);

        return layout;
    }

    private Node initRegExpPane() {

        BorderPane layout = new BorderPane();

        layout.setLeft(new Label("RegExp:"));
        textRegExp = new TextField();
        textRegExp.setText("AND|OR|NOT|\\\"[\\w\\s\\.]+\\\"|\\(|\\)");
        layout.setCenter(textRegExp);

        HBox rGroup = new HBox();
        Button btnToTokens = new Button("To Tokens");
        btnToTokens.setOnAction(t -> {
            onToTokens();
        });

        Button btnClean = new Button("Clean");
        btnClean.setOnAction(t -> {
            log.debug(".onClean");
            areaResults.setText("");
        });
        rGroup.getChildren().addAll(btnToTokens, btnClean);

        layout.setRight(rGroup);

        return layout;
    }

    private Node initButtonPane() {

        // TODO Auto-generated method stub
        return null;
    }

    private void onToTokens() {

        log.debug("onToTokens");
        LogicParser lp = new LogicParser();
        try {
            Tokenizable t = new TokenizerCustom(textRegExp.getText());
            Queue<String> tokens = t.toTokens(textQuery.getText());
            String res = tokens.stream().collect(Collectors.joining("\r\n"));
            areaResults.setText(res);
        } catch (Exception e) {
            areaResults.setText(e.toString());
            log.error(e.toString(), e);
        }
    }
}
