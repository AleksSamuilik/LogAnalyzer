package it.alex.analyzer.statistics;

import java.util.List;
import java.util.Map;

public interface LogStatistics {
    Map getStatistics();

    void statisticsCounting(List key);

    void initialArguments(List list);
}