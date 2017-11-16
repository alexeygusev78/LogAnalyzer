package ru.ag78.utils.loganalyzer;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Queue;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.cli.Options;

import ru.ag78.api.utils.SafeTypes;
import ru.ag78.useful.helpers.OptionsHelper;
import ru.ag78.useful.helpers.OptionsInitializer;
import ru.ag78.utils.loganalyzer.ui.LogAnalyzerGUI;

/**
 * Command-line tool to analyze log-files.
 * @author U_M00Y1
 *
 */
public class LogAnalyzer implements OptionsInitializer {

    private OptionsHelper options;

    public class Opts {

        public static final String INFO = "i";
        public static final String SOURCE = "s";
        public static final String TIME_FROM = "tf";
        public static final String TIME_TILL = "tt";
        public static final String TIMESTAMP_PATTERN = "tsp";
        public static final String FILTER = "f";
        public static final String LIMIT = "l";
        public static final String CATEGORY = "c";
        public static final String GUI = "gui";
    }

    /**
     * Entry-point
     * @param args
     */
    public static void main(String[] args) {

        try {
            LogAnalyzer t = new LogAnalyzer();
            t.start(args);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        System.exit(0);
    }

    /**
     * Begin work
     * @param args
     * @throws Exception
     */
    private void start(String[] args) throws Exception {

        options = new OptionsHelper(args, this);
        if (options.isHelp()) {
            StringBuilder header = new StringBuilder();
            header.append("LogAnalyzer command-line utility.").append(System.lineSeparator());

            StringBuilder synt = new StringBuilder("loganalyzer [<option>...]");
            synt.append(" [-Dfile.encoding=<encoding_name>]");
            options.showHelp(synt.toString(), header.toString(), "Alexey Gusev 2017");
            return;
        }

        if (options.isOption(Opts.INFO)) {
            showInfo();
            return;
        }

        if (options.isOption(Opts.GUI) || options.isEmpty()) {
            LogAnalyzerGUI.main(args);
            return;
        }

        if (!options.isOption(Opts.SOURCE)) {
            throw new Exception("Option " + Opts.SOURCE + " is not set.");
        }

        Stream<String> s = null;
        try {
            s = Files.lines(Paths.get(options.getOption(Opts.SOURCE)), Charset.defaultCharset());

            Predicate<String> main = x -> x != null;
            String tsPattern = options.getOption(Opts.TIMESTAMP_PATTERN, "HH:mm:ss,SSS");

            if (options.isOption(Opts.TIME_FROM)) {
                main = main.and(applyOptionTimeFrom(options.getOption(Opts.TIME_FROM), tsPattern));
            }

            if (options.isOption(Opts.TIME_TILL)) {
                main = main.and(applyOptionTimeTill(options.getOption(Opts.TIME_TILL), tsPattern));
            }
            s = s.filter(main);

            if (options.isOption(Opts.FILTER)) {
                final String filter = options.getOption(Opts.FILTER);
                System.out.println("filter={" + filter + "}");
                LogicParserOld lp = new LogicParserOld();
                Tokenizable t = new Tokenizer1();
                Queue<String> tokens = t.toTokens(filter);
                Predicate<String> p = lp.makePredicate(tokens).getPredicate();
                s = s.filter(p);
            }

            if (options.isOption(Opts.LIMIT)) {
                int limit = SafeTypes.parseSafeInt(options.getOption(Opts.LIMIT), 0);
                s = applyOptionLimit(s, limit);
            }

            s.forEach(System.out::println);
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }

    private void showInfo() {

        for (Object key: System.getProperties().keySet()) {
            System.out.println(key.toString() + "=" + System.getProperty(key.toString()));
        }

        //        try (InputStream in = getClass().getResourceAsStream("/main.css"); BufferedReader reader = new BufferedReader(new InputStreamReader(in));) {
        //            String line = "";
        //            while ((line = reader.readLine()) != null) {
        //                System.out.println(">" + line);
        //            }
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //        }
    }

    /**
     * Apply timestamp from
     * @param s
     * @param option
     */
    private Predicate<String> applyOptionTimeFrom(String strFrom, final String pattern) {

        final LocalTime ltFrom = LocalTime.parse(strFrom, DateTimeFormatter.ofPattern("HH:mm:ss"));
        Predicate<String> p = x -> {
            try {
                String[] tokens = x.split("\\s");
                String time = tokens[1];

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
                LocalTime curTime = LocalTime.parse(time, dtf);

                return curTime.isAfter(ltFrom);
            } catch (Exception e) {
                return false;
            }
        };

        return p;
    }

    /**
     * Apply timestamp till
     * @param strTill
     * @param pattern
     * @return
     */
    private Predicate<String> applyOptionTimeTill(String strTill, final String pattern) {

        final LocalTime ltTill = LocalTime.parse(strTill, DateTimeFormatter.ofPattern("HH:mm:ss"));
        Predicate<String> p = x -> {
            try {
                String[] tokens = x.split("\\s");
                String time = tokens[1];

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
                LocalTime curTime = LocalTime.parse(time, dtf);

                return curTime.isBefore(ltTill);
            } catch (Exception e) {
                return false;
            }
        };

        return p;
    }

    /**
     * Apply limit
     * @param s
     * @param limit
     * @return
     */
    private Stream<String> applyOptionLimit(Stream<String> s, int limit) {

        if (limit != 0) {
            return s.limit(limit);
        }

        return s;
    }

    @Override
    public void initOptions(Options opt) {

        opt.addOption(Opts.INFO, "info", false, "Show system information.");
        opt.addOption(Opts.SOURCE, "source", true, "Source file name intended to analyze.");
        opt.addOption(Opts.TIME_FROM, "time_from", true, "Timestamp from by pattern HH:mm:ss. By default from time is open.");
        opt.addOption(Opts.TIME_TILL, "time_till", true, "Timestamp till by pattern HH:mm:ss. By default till time is open.");
        opt.addOption(Opts.TIMESTAMP_PATTERN, "timestamp_pattern", true, "Pattern of timestamp. By default the pattern is HH:mm:ss,SSS.");
        opt.addOption(Opts.FILTER, "filter", true, "Filter condition.");
        opt.addOption(Opts.LIMIT, "limit", true, "Output limit value in lines of log. 0 - unlimited, By-default is unlimited.");
        opt.addOption(Opts.CATEGORY, "category", true, "Category package.");
        opt.addOption(Opts.GUI, false, "Show user console.");
    }
}
