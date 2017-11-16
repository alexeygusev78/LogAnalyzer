package ru.ag78.utils.loganalyzer.ui.fileset;

/**
 * Структура, представляющая собой ссылку на лог-файл.
 * @author alexey
 *
 */
public class LogFileItem implements Cloneable {

    private boolean checked;
    private String path;
    private String encoding;

    /**
     * Default initialization block
     */
    {
        encoding = System.getProperty("file.encoding", "UTF-8");
    }

    /**
     * Constructor using fields.
     * @param checked
     * @param path
     */
    public LogFileItem(boolean checked, String path) {

        super();
        this.checked = checked;
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
        this.checked = selected;
        this.path = path;
        this.encoding = encoding;
    }

    @Override
    protected LogFileItem clone() {

        return new LogFileItem(checked, path, encoding);
    }

    @Override
    public String toString() {

        return "[" + (checked ? "V" : " ") + "] " + path + " (" + encoding + ")";
    }

    public boolean isChecked() {

        return checked;
    }

    public void setChecked(boolean checked) {

        this.checked = checked;
    }

    public String getPath() {

        return path;
    }

    public void setPath(String path) {

        this.path = path;
    }

    public String getEncoding() {

        return encoding;
    }

    public void setEncoding(String encoding) {

        this.encoding = encoding;
    }
}
