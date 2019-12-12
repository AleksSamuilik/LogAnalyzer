package it.alex.analyzer.statistics;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StatisticsEngine implements LogStatistics {
    Map<String, Long> statisticsMap = new HashMap<>();

    @Override
    public Map getStatistics() {
        return statisticsMap;
    }

    @Override
    public synchronized void statisticsCounting(List key) {
        Iterator iterator = key.iterator();
        while (iterator.hasNext()) {
            String line = (String) iterator.next();
            Long count = statisticsMap.get(line);
            statisticsMap.put(line, ++count);
        }
    }

    @Override
    public void initialArguments(List list) {
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            statisticsMap.put((String) iterator.next(), 0l);
        }
    }
}