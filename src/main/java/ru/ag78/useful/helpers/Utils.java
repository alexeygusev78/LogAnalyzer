package ru.ag78.useful.helpers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class Utils {

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
}
