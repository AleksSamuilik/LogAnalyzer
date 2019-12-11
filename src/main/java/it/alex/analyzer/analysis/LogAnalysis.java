package it.alex.analyzer.analysis;

import java.util.List;

public interface LogAnalysis {

    void setArgsList(List argsList);

    void initialArguments();

    boolean isValid(String inputLine);

    List getAllArguments();

    List getFindsArgument();

}
