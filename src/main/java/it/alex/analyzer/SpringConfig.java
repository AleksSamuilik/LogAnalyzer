package it.alex.analyzer;

import it.alex.analyzer.analysis.DataAnalysis;
import it.alex.analyzer.analysis.FindUser;
import it.alex.analyzer.inputStream.ResourcesLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan
public class SpringConfig {

    @Bean
    public ResourcesLoader resourcesLoader() {
        return new ResourcesLoader();
    }

    @Bean
    public LogsHandler logsHandler() {
        return new LogsHandler(resourcesLoader().getFile(), getOperationMap());
    }

    @Bean
    public Map<String, DataAnalysis> getOperationMap() {
        Map<String, DataAnalysis> operationMap = new HashMap<>();
        operationMap.put(findUser().getNameOperation(), findUser());
        return operationMap;
    }

    @Bean
    public FindUser findUser() {
        return new FindUser();
    }

}
