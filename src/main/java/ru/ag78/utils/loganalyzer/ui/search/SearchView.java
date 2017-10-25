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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SearchView {

    private static final Logger log = Logger.getLogger(SearchView.class);

    private Events eventListener;

    // UI controls
    private Node root;
    private Label labelTitle;
    private ChoiceBox<String> cbSource;

    /**
     * SearchView events interface
     * @author alexey
     *
     */
    public static interface Events {

        public void onSearch(String source, String filter);

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

        VBox vLayout = new VBox();
        vLayout.setId("search_bar");
        vLayout.getStyleClass().add("vbox");

        // title
        labelTitle = new Label();
        labelTitle.setId("title");

        HBox toolbar = new HBox();
        toolbar.setPadding(new Insets(2, 2, 2, 2));

        // Source
        toolbar.getChildren().add(new Label("Source:"));
        cbSource = new ChoiceBox<String>();
        cbSource.setOnAction(t -> {
            eventListener.onSelectFileset(cbSource.getSelectionModel().getSelectedItem());
        });
        toolbar.getChildren().add(cbSource);

        // Filter
        toolbar.getChildren().add(new Label("Filter:"));
        TextField textFilter = new TextField();
        toolbar.getChildren().add(textFilter);

        // Search button
        Button btnSearch = new Button("Search");
        toolbar.getChildren().add(btnSearch);
        btnSearch.setOnAction(t -> {
            eventListener.onSearch("fileset1", "filter");
        });

        // Break button
        Button btnBreak = new Button("Break");
        toolbar.getChildren().add(btnBreak);
        btnBreak.setOnAction(t -> {
            eventListener.onBreak();
        });

        // Search results window
        TextArea textSearchResults = new TextArea();
        textSearchResults.setId("textSearchResults");

        vLayout.getChildren().addAll(labelTitle, toolbar, textSearchResults);

        return vLayout;
    }

    public void setFilesets(List<String> filesets) {

        cbSource.getItems().clear();
        filesets.stream().forEach(fs -> cbSource.getItems().add(fs));
        cbSource.getSelectionModel().selectFirst();
    }
}
