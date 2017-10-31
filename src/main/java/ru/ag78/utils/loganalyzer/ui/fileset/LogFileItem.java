package ru.ag78.utils.loganalyzer.ui.fileset;

/**
 * Структура, представляющая собой ссылку на лог-файл.
 * @author alexey
 *
 */
public class LogFileItem implements Cloneable {

    private final boolean selected;
    private final String path;
    private String encoding;

    /**
     * Default initialization block
     */
    {
        encoding = System.getProperty("file.encoding", "UTF-8");
    }

    /**
     * Constructor using fields.
     * @param selected
     * @param path
     */
    public LogFileItem(boolean selected, String path) {

        super();
        this.selected = selected;
        this.path = path;
    }

    /**
     * Copy-constructor
     * @param selected
     * @param path
     * @param encoding
     */
    public LogFileItem(boolean selected, String path, String encoding) {

        super();
        this.selected = selected;
        this.path = path;
        this.encoding = encoding;
    }

    @Override
    protected LogFileItem clone() {

        return new LogFileItem(selected, path, encoding);
    }

    public String getEncoding() {

        return encoding;
    }

    public void setEncoding(String encoding) {

        this.encoding = encoding;
    }

    public boolean isSelected() {

        return selected;
    }

    public String getPath() {

        return path;
    }

    @Override
    public String toString() {

        return "[" + (selected ? "V" : " ") + "] " + path;
    }
}
