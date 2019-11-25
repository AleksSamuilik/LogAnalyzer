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

    public synchronized void handler(File inputFile) {
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
        notify();
    }

    private void printStatistic(Map statisticMap) {
        Map<String, Long> statistics = statisticMap;
        for (Map.Entry entry : statistics.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    public void start() throws ArgumentsException {
        logAnalysis.initialArguments();
        outputProvider.setPathOutputFile(fileList.get(0));
        outputProvider.createOutputFile();
        logStatistics.initialArguments(logAnalysis.getArguments());

        for (int i = 0; i < fileList.size(); i++) {
            MyThread myThread = new MyThread(this, fileList.get(i));
            myThread.start();
            synchronized (myThread) {
                try {
                    myThread.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        outputProvider.close();
        printStatistic(logStatistics.getStatistics());
    }

    class MyThread extends Thread {
        LogsHandler logsHandler;
        File file;

        public MyThread(LogsHandler logsHandler, File file) {
            this.logsHandler = logsHandler;
            this.file = file;
        }

        @Override
        public void run() {
            logsHandler.handler(file);
        }
    }
}