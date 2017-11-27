package ru.ag78.utils.loganalyzer.ui;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import ru.ag78.useful.helpers.Utils;
import ru.ag78.utils.loganalyzer.config.Configuration;
import ru.ag78.utils.loganalyzer.config.Fileset;
import ru.ag78.utils.loganalyzer.config.LogFile;
import ru.ag78.utils.loganalyzer.ui.fileset.FilesetController;
import ru.ag78.utils.loganalyzer.ui.fileset.FilesetModel;
import ru.ag78.utils.loganalyzer.ui.fileset.LogFileItem;

public class MainModel {

    private static final Logger log = Logger.getLogger(MainModel.class);
    private Configuration config = new Configuration();

    private List<FilesetController> filesets = new LinkedList<>();
    private String version;

    private int filesetCounter = 1;
    private int searchCounter = 1;

    /**
     * Default ctor
     */
    public MainModel() {

        try {
            Properties props = Utils.getManifest();
            version = props.getProperty("Version");

        } catch (Exception e) {
            log.error("Failed to load manifest", e);
        }
    }

    public String getVersion() {

        return version;
    }

    public String getNextFilesetName() {

        return "Fileset" + Integer.toString(filesetCounter++);
    }

    public String getNextSearchName() {

        return "Search" + Integer.toString(searchCounter++);
    }

    public void addFileset(FilesetController fsc) {

        filesets.add(fsc);
    }

    public FilesetController getFileset(String name) {

        for (FilesetController fsc: filesets) {
            if (fsc.getModel().getName().equalsIgnoreCase(name)) {
                return fsc;
            }
        }
        return null;
    }

    public void removeFileset(String name) {

        filesets.removeIf(fsc -> fsc.getModel().getName().equals(name));
    }

    public List<String> getFilesetNames() {

        return filesets.stream().map(f -> f.getModel().getName()).collect(Collectors.toList());
    }

    /**
     * Save current configuration.
     * @throws Exception
     */
    public void saveConfig() throws Exception {

        config.getFilesets().clear();
        filesets.stream().map(fsc -> fsc.getModel()).filter(m -> m.isPersist()).forEach(m -> config.getFilesets().add(createFileset(m)));

        Path p = Utils.getConfigFile();
        try (FileWriter fw = new FileWriter(p.toFile(), false);) {
            JAXBContext context = JAXBContext.newInstance(Configuration.class);
            Marshaller m = context.createMarshaller();
            m.marshal(config, fw);
        } catch (JAXBException e) {
            throw new Exception("Failed to save config", e);
        }
    }

    /**
     * Load current configuration from fiile.
     * @throws Exception
     */
    public void loadConfig() throws Exception {

        File f = Utils.getConfigFile().toFile();
        if (!f.exists()) {
            log.warn("File " + f.getAbsolutePath() + " not found");
            return;
        }

        try (FileReader fr = new FileReader(f)) {
            JAXBContext context = JAXBContext.newInstance(Configuration.class);
            Unmarshaller u = context.createUnmarshaller();
            config = (Configuration) u.unmarshal(f);
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

    public Configuration getConfig() {

        return config;
    }
}
