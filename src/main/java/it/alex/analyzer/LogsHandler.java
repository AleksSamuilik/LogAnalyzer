package it.alex.analyzer;

import it.alex.analyzer.analysis.LogAnalysis;

import java.io.File;
import java.util.List;


public class LogsHandler {
    private List<File> fileList;
    private LogAnalysis logAnalysis;
    private File outputFile;


    public LogsHandler() {
    }

    public LogsHandler(List<File> fileList, LogAnalysis logAnalysis) {
        this.fileList = fileList;
        this.logAnalysis = logAnalysis;

    }




    public void start() {
        String input = "2019/11/01 15:37:18,857  [INFO] Sam main resourcePath.ResourceStarter:32 - ClassPathXmlApplicationContext: ClassPathContextResource";
        System.out.println(logAnalysis.isValid(input));
        System.out.println(fileList);

    }
}
