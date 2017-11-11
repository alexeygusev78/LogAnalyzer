package ru.ag78.utils.loganalyzer.logic.search;

public interface SearchResultReceiver {

    public void addInfo(String infoLine);

    public void onNewResult(String line);

    public void onSearchStarted();

    public void onSearchFinished();

    public void onError(String errorInfo);
}
