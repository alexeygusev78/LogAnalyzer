# LogAnalyzer
Advanced filtering, search and analyzing tool for yours log-files.

## Author
Gusev Alexey
alexey.gusev.78@mail.ru

## Features
1. Search by group of files, called 'Fileset'
2. Ability to change encoding for each log-file
3. Advanced search mechanism, based on RD-parser
4. Limit output

## Search Language
Search request constist of three types of elements:
- Logical AND, OR, NOT
- literal: sometext or "some string as single token
- brackets: ( or )

### Examples:
>wolf AND fox - searches lines, containing both 'wolf' and 'fox' substrings
>wolf AND NOT fox - searches the lines, containing 'wolf' and 'not' containing fox substrings
>wolf fox - interpreted as: wolf AND fox
>wolf AND (fox OR squirrel) - searches the lines, containing a 'wolf' and something of 'fox' or 'squirrel'

## Planned Features
1. Saving Filesets
2. ...

## Build
for build use
>gradle fatJar

## Requirements
Java8, JavaFX

## Usage
for start use
>loganalyzer.bat [options] (Windows)
or
>./la.sh [options] (Linux)

## Help
>loganalyzer.bat -h
or 
>loganalyzer.bat --help

outputs:
usage: loganalyzer [<option>...] [-Dfile.encoding=<encoding_name>]
LogAnalyzer command-line utility.
        -c,--category <arg>             Category package.
        -f,--filter <arg>               Filter condition.
        -gui                            Show user console.
        -h,--help                       Show this help information
        -i,--info                       Show system information.
        -l,--limit <arg>                Output limit value in lines of log. 0 - unlimited,
                                        By-default is unlimited.
        -s,--source <arg>               Source file name intended to analyze.
        -tf,--time_from <arg>           Timestamp from by pattern HH:mm:ss. By default from time is
                                        open.
        -tsp,--timestamp_pattern <arg>  Pattern of timestamp. By default the pattern is
                                        HH:mm:ss,SSS.
        -tt,--time_till <arg>           Timestamp till by pattern HH:mm:ss. By default till time is
                                        open.
