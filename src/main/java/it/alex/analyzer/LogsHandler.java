package it.alex.analyzer;

import it.alex.analyzer.analysis.ArgumentsException;
import it.alex.analyzer.analysis.LogAnalysis;
import it.alex.analyzer.outputStream.OutputProvider;
import it.alex.analyzer.statistics.LogStatistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class LogsHandler {
    private List<File> fileList;
    private LogAnalysis logAnalysis;
    private OutputProvider outputProvider;
    private LogStatistics logStatistics;


    public LogsHandler() {
    }

    public LogsHandler(List<File> fileList, LogAnalysis logAnalysis, LogStatistics logStatistics, OutputProvider outputProvider) {
        this.fileList = fileList;
        this.logAnalysis = logAnalysis;
        this.outputProvider = outputProvider;
        this.logStatistics = logStatistics;
    }

    private void handler(File inputFile) {
        try (FileReader fileReader = new FileReader(inputFile);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (logAnalysis.isValid(line)) {
                    logStatistics.statisticsCounting(logAnalysis.getFindedArgument());
                    outputProvider.write(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printStatistic(Map statisticMap) {
        Map<String, Long> statistics = logStatistics.getStatistics();
        for (Map.Entry entry : statistics.entrySet()) {
            System.out.println(entry.getKey()+" -> "+entry.getValue());
        }
    }

    public void start() throws ArgumentsException {
        logAnalysis.initialArguments();
        outputProvider.setPathOutputFile(fileList.get(0));
        outputProvider.createOutputFile();
        logStatistics.initialArguments(logAnalysis.getArguments().keySet());
        for (int i = 0; i < fileList.size(); i++) {
            handler(fileList.get(i));
        }
        outputProvider.close();
        printStatistic(logStatistics.getStatistics());
    }

}
