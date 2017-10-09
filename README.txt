LogAnalyzer

loganalyzer.bat -?
usage: loganalyzer [<option>...]
LogAnalyzer command-line utility.
        -?                              Show help information
        -c,--category <arg>             Category package.
        -f,--filter <arg>               Filter condition.
        -l,--limit <arg>                Output limit value in lines of log. 0 - unlimited,
                                        By-default is unlimited.
        -s,--source <arg>               Source file name intended to analyze.
        -tf,--time_from <arg>           Timestamp from by pattern HH:mm:ss. By default from time is
                                        open.
        -tsp,--timestamp_pattern <arg>  Pattern of timestamp. By default the pattern is
                                        HH:mm:ss,SSS.
        -tt,--time_till <arg>           Timestamp till by pattern HH:mm:ss. By default till time is
                                        open.
Alexey Gusev 2017
