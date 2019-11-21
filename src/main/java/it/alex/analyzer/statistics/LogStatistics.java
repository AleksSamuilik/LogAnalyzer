package it.alex.analyzer.statistics;

import java.util.Map;
import java.util.Set;

public interface LogStatistics {
    Map getStatistics();

    void statisticsCounting(String keyArgs);

    void initialArguments(Set keySet);
}
