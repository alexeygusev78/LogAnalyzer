package ru.ag78.utils.loganalyzer.ui.search;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

import ru.ag78.utils.loganalyzer.logic.search.SearchResultReceiver;
import ru.ag78.utils.loganalyzer.ui.fileset.LogFileItem;

public class SearchModel {

    private static final Logger log = Logger.getLogger(SearchModel.class);

    private String name;
    private String selectedFileset;

    /**
     * Ctor using fields
     * @param name
     */
    public SearchModel(String name) {

        super();
        this.name = name;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getSelectedFileset() {

        return selectedFileset;
    }

    public void setSelectedFileset(String selectedFileset) {

        this.selectedFileset = selectedFileset;
    }

    /**
     * Synchronouscly search through the file.
     * TODO: extract to SearchEngine.
     * @param f
     * @param filter
     * @param p
     * @return int - returns count of found elements.
     * @throws Exception
     */
    public void search(SearchResultReceiver srr, LogFileItem f, Predicate<String> p, int limit) throws Exception {

        srr.addInfo("LogFile=" + f.toString() + " limit=" + limit);

        srr.onSearchStarted();
        Stream<String> s = null;
        try {
            Path path = Paths.get(f.getPath());
            srr.addInfo("exists=" + path.toFile().exists());

            s = Files.lines(path, Charset.forName(f.getEncoding()));
            s = s.filter(p);

            if (limit > 0) {
                s = s.limit(limit);
            }

            s.forEach(l -> srr.onNewResult(l));
        } catch (Exception e) {
            log.error(e.toString(), e);
            srr.onError("Error: " + e.toString());
        } finally {
            if (s != null) {
                s.close();
            }

            srr.onSearchFinished();
        }
    }
}
