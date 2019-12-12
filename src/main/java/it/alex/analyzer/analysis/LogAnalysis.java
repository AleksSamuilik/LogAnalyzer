package it.alex.analyzer.analysis;

import java.util.List;

public interface LogAnalysis {

    void setFilterList(List<LogFilter> filterList);

    void initialArguments();

    boolean isValid(String inputLine);

    List getAllArguments();

    List getFindsArgument();

}
