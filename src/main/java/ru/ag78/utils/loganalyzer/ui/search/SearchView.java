package ru.ag78.utils.loganalyzer.ui.search;

import java.util.List;

import org.apache.log4j.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class SearchView {

    private static final String DEFAULT_LIMIT = "100";

    private static final Logger log = Logger.getLogger(SearchView.class);

    private Events eventListener;

    // UI controls
    private Node root;
    private ChoiceBox<String> cbSource;
    private TextField txtFilter;
    private TextField txtLimit;
    private TextArea searchResults;
    private Button btnSearch;
    private Button btnBreak;
    private Label lblFound;

    // local-data
    private StringProperty limit = new SimpleStringProperty(DEFAULT_LIMIT);

    /**
     * SearchView events interface
     * @author alexey
     *
     */
    public static interface Events {

        public void onSearch(String filter, int limit);

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

        VBox toolbars = new VBox();

        toolbars.getChildren().add(initButtonBar());
        toolbars.getChildren().add(initSearchBar());

        layout.setTop(toolbars);
        layout.setCenter(initSearchResults());
        layout.setBottom(initStatusBar());

        return layout;
    }

    private Node initStatusBar() {

        HBox statusBar = new HBox();

        lblFound = new Label("Not started yet.");
        statusBar.getChildren().add(lblFound);

        return statusBar;
    }

    private Node initSearchResults() {

        // Search results window
        searchResults = new TextArea();
        searchResults.setId("textSearchResults");

        return searchResults;
    }

    private Node initSearchBar() {

        HBox searchBar = new HBox();
        searchBar.getStyleClass().add("hbox");
        searchBar.setPadding(new Insets(2, 2, 2, 2));

        // label
        Label lblFilter = new Label("Filter:");
        //        lblFilter.setMaxWidth(Control.USE_PREF_SIZE);
        //        lblFilter.setMinWidth(lblFilter.getWidth());
        searchBar.getChildren().add(lblFilter);

        // text box
        txtFilter = new TextField();
        txtFilter.getStyleClass().add("text");
        txtFilter.setId("Search");
        txtFilter.setOnAction(act -> invokeOnSearch());
        searchBar.getChildren().add(txtFilter);
        HBox.setHgrow(txtFilter, Priority.ALWAYS); // grow priority to fit size of the HBox.

        // Search button
        btnSearch = new Button(">>");
        searchBar.getChildren().add(btnSearch);
        btnSearch.setOnAction(t -> invokeOnSearch());

        // Break button
        btnBreak = new Button("Stop");
        btnBreak.setDisable(true);
        btnBreak.setOnAction(t -> {
            eventListener.onBreak();
        });
        searchBar.getChildren().add(btnBreak);

        // for debug "Check"
        Button btnCheck = new Button("Check");
        btnCheck.setOnAction(t -> {
            log.debug("Check! txtFilter.widht=" + txtFilter.getWidth());
        });
        searchBar.getChildren().add(btnCheck);

        return searchBar;
    }

    private void invokeOnSearch() {

        try {
            int l = Integer.parseInt(limit.get());
            eventListener.onSearch(txtFilter.getText(), l);
        } catch (NumberFormatException e) {
            log.error("Failed to parse limit. Operation canceled.", e);
        }
    }

    private Node initButtonBar() {

        HBox buttonBar = new HBox();
        buttonBar.setPadding(new Insets(2, 2, 2, 2));
        buttonBar.getStyleClass().add("hbox");
        // buttonBar.setPrefWidth(1000.0);

        // Source
        buttonBar.getChildren().add(new Label("Source:"));
        cbSource = new ChoiceBox<String>();
        cbSource.setOnAction(t -> {
            eventListener.onSelectFileset(cbSource.getSelectionModel().getSelectedItem());
        });
        buttonBar.getChildren().add(cbSource);

        // Limit
        buttonBar.getChildren().add(new Label("Limit:"));
        txtLimit = new TextField(this.limit.get());
        this.limit = txtLimit.textProperty();
        buttonBar.getChildren().add(txtLimit);

        return buttonBar;
    }

    public void setFilesets(List<String> filesets) {

        cbSource.getItems().clear();
        filesets.stream().forEach(fs -> cbSource.getItems().add(fs));
        cbSource.getSelectionModel().selectFirst();
    }

    public void setSearchResult(String result) {

        searchResults.setText(result);
    }

    public void setLimit(int limit) {

        this.limit.set(Integer.toString(limit));
    }

    public void setFound(int found) {

        lblFound.setText("Found: " + Integer.toString(found));
    }
}
