package ru.alex55498.useful.helpers;

import java.io.PrintWriter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import ru.alfabank.useful.utils.SafeTypes;

/**
 * Class for parsing & handle program options.
 * @author Алексей
 *
 */
public class OptionsHelper {

    private CommandLine cmd;
    private Options options;
    private OptionsInitializer oi;

    /**
     * Ctor with command-line arguments.
     * @param args
     */
    public OptionsHelper(String[] args, OptionsInitializer oi) throws Exception {

        if (oi == null) {
            throw new IllegalArgumentException("OptionsInitializer cannot be null");
        }
        this.oi = oi;

        options = new Options();
        options.addOption("?", "Show help information");
        oi.initOptions(options);

        cmd = parseCommandLine(options, args);
    }

    //    {
    //
    //        Options opt = new Options();
    //
    //        // -h
    //        opt.addOption("h", "show help information");
    //
    //        // -host <hostname> 
    //        Option host = Option.builder("host")
    //                        .desc("Host")
    //                        .argName("host")
    //                        .hasArg(true)
    //                        .build();
    //        opt.addOption(host);
    //
    //        // -port <port> 
    //        Option port = Option.builder("port")
    //                        .desc("Port")
    //                        .argName("port")
    //                        .hasArg(true)
    //                        .build();
    //        opt.addOption(port);
    //
    //        // -login <login> 
    //        Option login = Option.builder("login")
    //                        .desc("Login")
    //                        .argName("login")
    //                        .hasArg(true)
    //                        .build();
    //        opt.addOption(login);
    //
    //        // -password <password> 
    //        Option password = Option.builder("password")
    //                        .desc("password")
    //                        .argName("password")
    //                        .hasArg(true)
    //                        .build();
    //        opt.addOption(password);
    //
    //        // -mode { BL_SEND } 
    //        Option mode = Option.builder("mode")
    //                        .desc("Mode { BL_SEND }")
    //                        .argName("mode")
    //                        .hasArg(true)
    //                        .build();
    //        opt.addOption(mode);
    //
    //        // -target  
    //        Option target = Option.builder("target")
    //                        .desc("Target name of AFX module")
    //                        .argName("target")
    //                        .hasArg(true)
    //                        .build();
    //        opt.addOption(target);
    //
    //        // -mps  
    //        Option mps = Option.builder("mps")
    //                        .desc("Messages per second. By-default 10.")
    //                        .longOpt("messages-per-second")
    //                        .argName("mps")
    //                        .hasArg(true)
    //                        .build();
    //        opt.addOption(mps);
    //    }

    private CommandLine parseCommandLine(Options options, String[] args) throws Exception {

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        return cmd;
    }

    /**
     * Безопасно возвращает значение заданной опции. В случае ошибки возвращается пустая строка "".
     * @param name
     * @return
     */
    public String getOption(String name) {

        return getOption(name, "");
    }

    public String getOption(String name, String defaultValue) {

        if (isOption(name)) {
            return SafeTypes.getSafeString(cmd.getOptionValue(name), defaultValue);
        }

        return defaultValue;
    }

    /**
     * Безопасно возвращает: задана ли опция с указанным именем.
     * @param name
     * @return
     */
    public boolean isOption(String name) {

        return cmd.hasOption(name);
    }

    /**
     * Show help-information.
     * @param cmdLineSyntax - something like 'java -jar LoadTool.jar [<options>]...'
     * @param header
     * @param footer
     */
    public void showHelp(String cmdLineSyntax, String header, String footer) {

        try (PrintWriter pw = new PrintWriter(System.out, true);) {
            showHelp(cmdLineSyntax, header, footer, pw);
        } catch (Exception e) {
            System.err.print(e);
        }
    }

    public void showHelp(String cmdLineSyntax, String header, String footer, PrintWriter pw) {

        HelpFormatter hf = new HelpFormatter();
        hf.printHelp(pw, 100, cmdLineSyntax, header, options, 8, 2, footer);
    }

    public boolean isHelp() {

        return cmd.hasOption("?");
    }
}
