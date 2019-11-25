package it.alex.analyzer.outputStream;

import java.io.*;

public class LogFileWriter implements OutputProvider {

    private int flushCounter = 0;
    private final int flushFrequency = 100;
    private String outputFilePath;

    private PrintWriter logWriter;

    public LogFileWriter() {
    }

    @Override
    public synchronized void write(String inputString) {
        String text = inputString + "\n";
        logWriter.write(text, 0, text.length());
        flushCounter++;
        if (flushCounter > flushFrequency) {
            logWriter.flush();
            flushCounter = 0;
        }
    }

    @Override
    public void close() {
        logWriter.close();
    }

    @Override
    public void createOutputFile() {
        File outputFile = new File(outputFilePath);
        if (!outputFile.exists()) {
            try {
                outputFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            this.logWriter = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setPathOutputFile(File inputFile) {
        outputFilePath = inputFile.getParent() + "\\LogAnalyzer.log";
    }
}