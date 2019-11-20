package it.alex.analyzer;

import it.alex.analyzer.analysis.SearchEngine;
import it.alex.analyzer.inputStream.ResourcesLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static it.alex.analyzer.App.getArgs;

@Configuration
@ComponentScan
public class SpringConfig {

    private List<String> args =getArgs();

    @Bean
    public SearchEngine handler() {
        return new SearchEngine(args);
    }


    @Bean
    public ResourcesLoader resourcesLoader() {

        return new ResourcesLoader(args.get(0));
    }

    @Bean
    public LogsHandler logsHandler() {
        return new LogsHandler(resourcesLoader().getFileList(), handler());
    }


}
