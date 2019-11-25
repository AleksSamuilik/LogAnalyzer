package it.alex.analyzer.analysis;

import java.util.List;
import java.util.Map;

public interface LogAnalysis {

    void initialArguments();

    boolean  isValid(String inputLine);

    List getArguments();

    List getFindedArgument();

}
