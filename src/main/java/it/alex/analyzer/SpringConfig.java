package it.alex.analyzer;

import it.alex.analyzer.analysis.LogAnalysis;
import it.alex.analyzer.analysis.SearchEngine;
import it.alex.analyzer.inputStream.ResourcesLoader;
import it.alex.analyzer.inputStream.ResourcesProvider;
import it.alex.analyzer.outputStream.LogFileWriter;
import it.alex.analyzer.outputStream.OutputProvider;
import it.alex.analyzer.statistics.LogStatistics;
import it.alex.analyzer.statistics.StatisticsEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class SpringConfig {

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