package ru.ag78.useful.helpers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Utils {

    private static final String USER_HOME = "user.home";
    private static final String CONFIG_FILE = "loganalyzer.dat";

    public static Properties getManifest() throws Exception {

        Properties props = new Properties();
        try (InputStream is = Utils.class.getResourceAsStream("MANIFEST.MF");
                        BufferedReader rdr = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {

        }

        return props;
    }

    public static String getManifestParam(String paramName) throws Exception {

        return getManifest().getProperty(paramName, "");
    }

    public static Path getConfigFile() {

        return Paths.get(System.getProperty(USER_HOME), CONFIG_FILE);
    }
}
