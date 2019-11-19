package it.alex.analyzer;

import it.alex.analyzer.analysis.DataAnalysis;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class LogsHandler {
    private int numberThreads;
    private List<File> fileList;
    private Map<String, DataAnalysis> mapOperation;

    public LogsHandler(List<File> fileList, Map<String, DataAnalysis> mapOperation) {
        this.fileList = fileList;
        this.mapOperation = mapOperation;
    }

    public void handler(String[] args) {
        System.out.println(fileList);
        System.out.println(Arrays.toString(args));
    }

    public List<File> getFileList() {
        return fileList;
    }
}
