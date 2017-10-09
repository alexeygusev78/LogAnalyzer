package ru.ag78.useful.helpers;

import org.apache.commons.cli.Options;

public interface OptionsInitializer {

    /**
     * Абстрактная функция, в которой необходимо проинициализировать набор CommandLine опций.
     * @param opt
     */
    public void initOptions(Options opt);

}
