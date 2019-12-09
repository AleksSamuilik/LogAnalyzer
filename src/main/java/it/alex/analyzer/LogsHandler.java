package it.alex.analyzer;

import it.alex.analyzer.analysis.ArgumentsException;
import it.alex.analyzer.analysis.LogAnalysis;
import it.alex.analyzer.inputStream.FileNotFoundException;
import it.alex.analyzer.inputStream.ResourcesProvider;
import it.alex.analyzer.outputStream.OutputProvider;
import it.alex.analyzer.statistics.LogStatistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class LogsHandler {

    private List<File> fileList;
    private LogAnalysis logAnalysis;
    private OutputProvider outputProvider;
    private LogStatistics logStatistics;
    ResourcesProvider resourcesProvider;
    private int numberThread;

    public LogsHandler() {
    }

    public LogsHandler(ResourcesProvider resourcesProvider, LogAnalysis logAnalysis, LogStatistics logStatistics, OutputProvider outputProvider) {
        this.logAnalysis = logAnalysis;
        this.outputProvider = outputProvider;
        this.logStatistics = logStatistics;
        this.resourcesProvider = resourcesProvider;
    }

    public void handler(File inputFile) {
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
        Map<String, Long> statistics = statisticMap;
        for (Map.Entry entry : statistics.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    private int initialNumberOfThreads() {
        List argsList = App.getArgs();
        Iterator iterator = argsList.iterator();
        while (iterator.hasNext()) {
            int numberThread = 0;
            String input = (String) iterator.next();
            if (input.contains("-t-")) {
                numberThread = Integer.parseInt(input.replaceAll("-t-", ""));
                if (numberThread > fileList.size()) {
                    return fileList.size();
                } else {
                    return numberThread;
                }
            }
        }
        return 1;
    }

    public void start() throws ArgumentsException, FileNotFoundException {
        loadConfig();

        while (!fileList.isEmpty()) {
            if (numberThread <= fileList.size() && numberThread > 1) {
                List<MyThread> threadList = getThreadList();
                for (int i = 0; i < threadList.size(); i++) {
                    threadList.get(i).start();
                }
                for (int i = 0; i < threadList.size(); i++) {
                    try {
                        threadList.get(i).join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                handler(fileList.get(fileList.size() - 1));
                fileList.remove(fileList.size() - 1);
            }

        }
        outputProvider.close();
        printStatistic(logStatistics.getStatistics());
    }

    private void loadConfig() throws ArgumentsException, FileNotFoundException {
        resourcesProvider.loadFile();
        this.fileList = Collections.synchronizedList(resourcesProvider.getFileList());
        this.numberThread = initialNumberOfThreads();
        logAnalysis.initialArguments();
        outputProvider.setPathOutputFile(fileList.get(0));
        outputProvider.createOutputFile();
        logStatistics.initialArguments(logAnalysis.getArguments());
    }

    private List<MyThread> getThreadList() {
        List<MyThread> threadList = new ArrayList<>();
        for (int i = 0; i < numberThread; i++) {

            threadList.add(new MyThread(this, fileList.get(fileList.size() - 1)));
            fileList.remove(fileList.size() - 1);
        }
        return threadList;
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