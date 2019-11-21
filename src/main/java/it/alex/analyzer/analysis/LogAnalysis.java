package it.alex.analyzer.analysis;

import java.util.Map;

public interface LogAnalysis {

    void initialArguments();

    boolean isValid(String inputLine);

    Map getArguments();

    String getFindedArgument();

}
