package ru.ag78.useful.helpers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import ru.ag78.api.utils.SafeArrays;

public class Utils {

    private static final String USER_HOME = "user.home";
    private static final String CONFIG_FILE = "loganalyzer.dat";

    public static Properties getManifest() throws Exception {

        Properties props = new Properties();
        try (InputStream is = Utils.class.getResourceAsStream("/META-INF/MANIFEST.MF");
                        BufferedReader rdr = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
            String line = null;
            while ((line = rdr.readLine()) != null) {
                String[] tokens = line.split(":");
                String key = SafeArrays.getSafeItem(tokens, 0);
                String value = SafeArrays.getSafeItem(tokens, 1);
                if (key.isEmpty()) {
                    continue;
                }

                props.setProperty(key, value);
            }
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
