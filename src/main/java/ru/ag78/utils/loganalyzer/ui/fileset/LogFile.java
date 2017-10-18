package ru.ag78.utils.loganalyzer.ui.fileset;

/**
 * Структура, представляющая собой ссылку на лог-файл.
 * @author alexey
 *
 */
public class LogFile {

    private final boolean selected;
    private final String path;

    /**
     * Constructor using fields.
     * @param selected
     * @param path
     */
    public LogFile(boolean selected, String path) {

        super();
        this.selected = selected;
        this.path = path;
    }

    @Override
    public String toString() {

        return "[" + (selected ? "V" : " ") + "] " + path;
    }
}
