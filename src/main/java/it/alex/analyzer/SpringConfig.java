package it.alex.analyzer;

import it.alex.analyzer.analysis.ArgumentsException;
import it.alex.analyzer.analysis.LogAnalysis;
import it.alex.analyzer.analysis.SearchEngine;
import it.alex.analyzer.inputStream.ResourcesLoader;
import it.alex.analyzer.inputStream.ResourcesProvider;
import it.alex.analyzer.outputStream.LogFileWriter;
import it.alex.analyzer.outputStream.OutputProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.List;

import static it.alex.analyzer.App.getArgs;

@Configuration
@ComponentScan
public class SpringConfig {

    private List<String> args = getArgs();

    @Bean
    public LogAnalysis logAnalysis(){
        return new SearchEngine(args);
    }

    @Bean
    public ResourcesProvider resourcesProvider() {
        return new ResourcesLoader(args.get(0));
    }

    @Bean
    public LogsHandler logsHandler(){
        return new LogsHandler(resourcesProvider().getFileList(), logAnalysis(),outputProvider());
    }

    @Bean
    @Scope("prototype")
    public OutputProvider outputProvider() {
        return new LogFileWriter();
    }
}
