package it.alex.analyzer.statistics;

import java.util.*;

public class StatisticsEngine implements LogStatistics {
    Map<String, Long> statisticsMap = new HashMap<>();

    @Override
    public Map getStatistics() {
       return statisticsMap;
}

    @Override
    public void statisticsCounting(String key) {

        Long count = statisticsMap.get(key);
        statisticsMap.put(key, ++count);
    }

    @Override
    public void initialArguments(Set keySet) {
        Iterator iterator = keySet.iterator();
        while (iterator.hasNext()) {
            statisticsMap.put((String) iterator.next(), 0l);
        }
    }
}
