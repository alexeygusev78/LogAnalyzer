package ru.ag78.utils.loganalyzer.bind;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import ru.ag78.utils.loganalyzer.ui.fileset.LogFileItem;
import ru.ag78.utils.loganalyzer.ui.fileset.LogFileItemWrp;

public class BindingTest {

    @Test
    public void test1() {

        LogFileItem lfi = new LogFileItem(false, "blog1.log");
        Assert.assertNotNull(lfi);
        Assert.assertEquals("blog1.log", lfi.getPath());

        LogFileItemWrp lfiw = new LogFileItemWrp(lfi);
        Assert.assertNotNull(lfiw);
        lfiw.setPath("blog2.log");
        Assert.assertEquals("blog2.log", lfi.getPath());
    }

    @Test
    public void observableListTest() {

        List<LogFileItem> list = new LinkedList<>();
        list.add(new LogFileItem(false, "blog1.log"));
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals("blog1.log", list.get(0).getPath());

        ObservableList<LogFileItem> obsList = FXCollections.observableArrayList(list);
        obsList.addListener(new ListChangeListener<LogFileItem>() {

            @Override
            public void onChanged(Change<? extends LogFileItem> c) {

                System.out.println("onChange c=" + c);
                for (LogFileItem lfi: c.getList()) {
                    System.out.println(lfi);
                }
            }
        });

        Assert.assertNotNull(obsList);
        Assert.assertEquals(1, list.size());
        obsList.add(new LogFileItem(false, "blog2.log"));

    }
}
