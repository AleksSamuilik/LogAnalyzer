package it.alex.analyzer;

import it.alex.analyzer.analysis.ArgumentsException;
import it.alex.analyzer.analysis.LogAnalysis;
import it.alex.analyzer.outputStream.OutputProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


public class LogsHandler {
    private List<File> fileList;
    private LogAnalysis logAnalysis;
    private OutputProvider outputProvider;


    public LogsHandler() {
    }

    public LogsHandler(List<File> fileList, LogAnalysis logAnalysis, OutputProvider outputProvider) {
        this.fileList = fileList;
        this.logAnalysis = logAnalysis;
        this.outputProvider = outputProvider;


    }


    public void start() throws ArgumentsException {
        logAnalysis.initialArguments();
        outputProvider.setPathOutputFile(fileList.get(0));
        outputProvider.createOutputFile();
        for (int i = 0; i < fileList.size(); i++) {
            File inputFile = fileList.get(i);
            System.out.println(fileList.get(i).getPath());
            try (FileReader fileReader = new FileReader(inputFile);
                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (logAnalysis.isValid(line)) {
                        outputProvider.write(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        outputProvider.close();
    }
}
