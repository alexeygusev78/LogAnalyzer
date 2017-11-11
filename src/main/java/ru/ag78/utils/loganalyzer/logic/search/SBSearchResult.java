package ru.ag78.utils.loganalyzer.logic.search;

import ru.ag78.api.utils.Counter;

/**
 * SearchResult based on StringBuilder mechanism.
 * @author alexey
 *
 */
public class SBSearchResult implements SearchResultReceiver {

    private Counter counter = new Counter();
    private StringBuilder sb = new StringBuilder();

    /**
     * Defaut ctor
     */
    public SBSearchResult() {

    }

    /**
     * Ctor with info parameter.
     * @param info
     */
    public SBSearchResult(String info) {

        sb.append(info).append("\r\n");
    }

    @Override
    public void addInfo(String infoLine) {

        sb.append(infoLine).append("\r\n");
    }

    @Override
    public void onNewResult(String line) {

        counter.increment();
        sb.append(line).append("\r\n");
    }

    @Override
    public void onSearchStarted() {

        counter = new Counter();
    }

    @Override
    public void onSearchFinished() {

        addInfo(counter.toString());
        addInfo("========================================");
    }

    public Counter getCounter() {

        return counter;
    }

    @Override
    public String toString() {

        return sb.toString();
    }

    @Override
    public void onError(String errorInfo) {

        sb.append(errorInfo).append("\r\n");
    }
}
