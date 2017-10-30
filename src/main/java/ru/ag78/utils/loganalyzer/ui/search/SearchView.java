package ru.ag78.utils.loganalyzer.ui.search;

import java.util.List;

import org.apache.log4j.Logger;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class SearchView {

    private static final Logger log = Logger.getLogger(SearchView.class);

    private Events eventListener;

    // UI controls
    private Node root;
    private ChoiceBox<String> cbSource;
    private TextField textFilter;

    /**
     * SearchView events interface
     * @author alexey
     *
     */
    public static interface Events {

        public void onSearch(String filter);

        public void onBreak();

        public void onSelectFileset(String name);
    }

    /**
     * Ctor with parameters
     * @param eventListener
     */
    public SearchView(Events eventListener) {

        super();
        this.eventListener = eventListener;
        getRoot();
    }

    public Node getRoot() {

        if (root == null) {
            root = initView();
        }

        return root;
    }

    private Node initView() {

        BorderPane layout = new BorderPane();
        layout.setId("search_bar");
        layout.getStyleClass().add("vbox");

        // toolbar
        BorderPane toolbar = new BorderPane();

        HBox hboxLeft = new HBox();
        hboxLeft.setPadding(new Insets(2, 2, 2, 2));
        hboxLeft.getStyleClass().add("hbox");

        // Source
        hboxLeft.getChildren().add(new Label("Source:"));
        cbSource = new ChoiceBox<String>();
        cbSource.setOnAction(t -> {
            eventListener.onSelectFileset(cbSource.getSelectionModel().getSelectedItem());
        });
        hboxLeft.getChildren().add(cbSource);

        // Filter
        hboxLeft.getChildren().add(new Label("Filter:"));

        textFilter = new TextField();
        textFilter.getStyleClass().add("text");
        textFilter.setId("Search");
        // hboxLeft.getChildren().add(textFilter);

        HBox hboxRight = new HBox();

        // Search button
        Button btnSearch = new Button("Search");
        hboxRight.getChildren().add(btnSearch);
        btnSearch.setOnAction(t -> {
            eventListener.onSearch(textFilter.getText());
        });

        // Break button
        Button btnBreak = new Button("Break");
        hboxRight.getChildren().add(btnBreak);
        btnBreak.setOnAction(t -> {
            eventListener.onBreak();
        });

        // Search results window
        TextArea searchResults = new TextArea();
        searchResults.setId("textSearchResults");

        // add to layout
        toolbar.setLeft(hboxLeft);
        toolbar.setCenter(textFilter);
        toolbar.setRight(hboxRight);

        layout.setTop(toolbar);
        layout.setCenter(searchResults);

        return layout;
    }

    public void setFilesets(List<String> filesets) {

        cbSource.getItems().clear();
        filesets.stream().forEach(fs -> cbSource.getItems().add(fs));
        cbSource.getSelectionModel().selectFirst();
    }
}
