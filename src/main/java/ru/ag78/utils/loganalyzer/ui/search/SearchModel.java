package ru.ag78.utils.loganalyzer.ui.search;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

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
     * @return
     * @throws Exception
     */
    public StringBuilder search(LogFileItem f, Predicate<String> p) throws Exception {

        final int limit = 100;

        StringBuilder sb = new StringBuilder();
        sb.append("File=").append(f.getPath()).append(" encoding=" + f.getEncoding()).append("\r\n");

        Stream<String> s = null;
        try {
            Path path = Paths.get(f.getPath());
            sb.append("exists=").append(path.toFile().exists()).append("\r\n");

            s = Files.lines(path, Charset.forName(f.getEncoding()));
            s = s.filter(p);

            if (limit > 0) {
                s = s.limit(limit);
            }

            s.forEach(l -> sb.append(l).append("\r\n"));
        } catch (Exception e) {
            log.error(e.toString(), e);
            sb.append("Error: " + e.toString()).append("\r\n");
        } finally {
            if (s != null) {
                s.close();
            }

            sb.append("\r\n");
            sb.append("========================================");
            sb.append("\r\n");
        }

        return sb;
    }
}
