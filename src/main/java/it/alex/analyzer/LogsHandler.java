package it.alex.analyzer;

import it.alex.analyzer.analysis.ArgumentsException;
import it.alex.analyzer.analysis.LogAnalysis;
import it.alex.analyzer.inputStream.FileNotFoundException;
import it.alex.analyzer.inputStream.ResourcesProvider;
import it.alex.analyzer.outputStream.OutputProvider;
import it.alex.analyzer.statistics.LogStatistics;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static it.alex.analyzer.App.getArgs;

public class LogsHandler {

    @Autowired
    private LogAnalysis logAnalysis;
    @Autowired
    private OutputProvider outputProvider;
    @Autowired
    private LogStatistics logStatistics;
    @Autowired
    ResourcesProvider resourcesProvider;

    private List<File> fileList;
    private int numberThread;

    public void handler(File inputFile) {
        try (FileReader fileReader = new FileReader(inputFile);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (logAnalysis.isValid(line)) {
                    logStatistics.statisticsCounting(logAnalysis.getFindsArgument());
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
        List argsList = getArgs();
        Iterator iterator = argsList.iterator();
        while (iterator.hasNext()) {
            int numberThread;
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
        if (numberThread <= fileList.size() && numberThread > 1) {

            while (fileList.size() - numberThread >= 0) {
                startAltThread();
            }
            startMainThread();
        } else {
            startMainThread();
        }
        outputProvider.close();
        printStatistic(logStatistics.getStatistics());
    }

    private void startMainThread() {
        while (fileList.size() != 0) {
            handler(fileList.get(fileList.size() - 1));
            fileList.remove(fileList.size() - 1);
        }
    }

    private void startAltThread() {
        List<MyThread> threadList = getThreadList();
        for (int i = 0; i < threadList.size(); i++) {
            threadList.get(i).start();
        }
        if (fileList.size() - numberThread >= 0 || fileList.size() % numberThread == 0) {
            for (int i = 0; i < threadList.size(); i++) {
                try {
                    threadList.get(i).join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadConfig() throws ArgumentsException, FileNotFoundException {
        resourcesProvider.setPath((String) getArgs().get(0));
        resourcesProvider.loadFile();
        this.fileList = Collections.synchronizedList(resourcesProvider.getFileList());
        this.numberThread = initialNumberOfThreads();
        logAnalysis.initialArguments();
        outputProvider.setPathOutputFile(fileList.get(0));
        outputProvider.createOutputFile();
        logStatistics.initialArguments(logAnalysis.getAllArguments());
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