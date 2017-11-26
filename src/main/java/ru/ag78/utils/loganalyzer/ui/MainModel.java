package ru.ag78.utils.loganalyzer.ui;

import java.io.FileWriter;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import ru.ag78.useful.helpers.Utils;
import ru.ag78.utils.loganalyzer.config.Configuration;
import ru.ag78.utils.loganalyzer.config.Fileset;
import ru.ag78.utils.loganalyzer.config.LogFile;
import ru.ag78.utils.loganalyzer.ui.fileset.FilesetModel;
import ru.ag78.utils.loganalyzer.ui.fileset.LogFileItem;

public class MainModel {

    private Configuration config = new Configuration();

    private int filesetCounter = 1;
    private int searchCounter = 1;

    private Map<String, FilesetModel> filesets = new HashMap<>();

    public String getNextFilesetName() {

        return "Fileset" + Integer.toString(filesetCounter++);
    }

    public String getNextSearchName() {

        return "Search" + Integer.toString(searchCounter++);
    }

    public FilesetModel getFileset(String filesetName) {

        return filesets.get(filesetName);
    }

    /**
     * Save current configuration.
     * @throws Exception
     */
    public void saveConfig() throws Exception {

        filesets.values().stream().filter(fs -> fs.isPersist()).forEach(fs -> config.getFilesets().add(createFileset(fs)));

        Path p = Utils.getConfigFile();
        try (FileWriter fw = new FileWriter(p.toFile(), false);) {
            JAXBContext context = JAXBContext.newInstance(Configuration.class);
            Marshaller m = context.createMarshaller();
            m.marshal(config, fw);
        } catch (JAXBException e) {
            throw new Exception("Failed to save config", e);
        }
    }

    private static Fileset createFileset(FilesetModel fsModel) {

        Fileset fs = new Fileset();
        fs.setName(fsModel.getName());
        fs.setDescription(fsModel.getDescription());

        for (LogFileItem lfi: fsModel.getFiles()) {
            LogFile lf = createLogFile(lfi);
            fs.getFiles().add(lf);
        }

        return fs;
    }

    private static LogFile createLogFile(LogFileItem logFileItem) {

        LogFile lf = new LogFile();
        lf.setPath(logFileItem.getPath());
        lf.setEncoding(logFileItem.getEncoding());

        return lf;
    }
}
