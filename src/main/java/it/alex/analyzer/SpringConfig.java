package it.alex.analyzer;

import it.alex.analyzer.analysis.*;
import it.alex.analyzer.inputStream.ResourcesLoader;
import it.alex.analyzer.inputStream.ResourcesProvider;
import it.alex.analyzer.outputStream.LogFileWriter;
import it.alex.analyzer.outputStream.OutputProvider;
import it.alex.analyzer.statistics.LogStatistics;
import it.alex.analyzer.statistics.StatisticsEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

import static it.alex.analyzer.App.getArgs;

@Configuration
@ComponentScan
public class SpringConfig {

    @Bean
    public List<LogFilter> list() {
        List<LogFilter> logFilterList = new ArrayList<>();
        logFilterList.add(new DateFilter(getArgs()));
        logFilterList.add(new UserFilter(getArgs()));
        logFilterList.add(new MessageFilter(getArgs()));
        return logFilterList;
    }

    @Bean
    public LogsHandler logsHandler() {
        return new LogsHandler();
    }

    @Bean
    public LogAnalysis logAnalysis() {
        return new SearchEngine();
    }

    @Bean
    public ResourcesProvider resourcesProvider() {
        return new ResourcesLoader();
    }

    @Bean
    public OutputProvider outputProvider() {
        return new LogFileWriter();
    }

    @Bean
    public LogStatistics logStatistics() {
        return new StatisticsEngine();
    }
}