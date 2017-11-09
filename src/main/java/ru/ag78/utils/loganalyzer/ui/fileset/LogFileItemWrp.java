package ru.ag78.utils.loganalyzer.ui.fileset;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class LogFileItemWrp {

    private LogFileItem item;

    private BooleanProperty checked = new SimpleBooleanProperty();
    private StringProperty path = new SimpleStringProperty();
    private StringProperty encoding = new SimpleStringProperty();

    /**
     * Ctor with parameters.
     * @param item
     */
    public LogFileItemWrp(final LogFileItem _item) {

        super();

        this.item = _item;

        checked.set(item.isChecked());
        path.set(item.getPath());
        encoding.set(item.getEncoding());

        path.addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                item.setPath(newValue);
            }
        });

        checked.addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                item.setSelected(newValue);
            }
        });
    }

    public LogFileItem getItem() {

        return item;
    }

    public BooleanProperty checkedProperty() {

        return checked;
    }

    public boolean getChecked() {

        return checked.get();
    }

    public void setChecked(boolean checked) {

        this.checked.set(checked);
    }

    public StringProperty pathProperty() {

        return path;
    }

    public String getPath() {

        return path.get();
    }

    public void setPath(String path) {

        this.path.set(path);
    }

    public StringProperty encodingProperty() {

        return encoding;
    }

    public String getEncoding() {

        return encoding.get();
    }

    public void setEncoding(String encoding) {

        this.encoding.set(encoding);
    }
}
