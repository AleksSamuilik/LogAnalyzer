package it.alex.analyzer.outputStream;

import java.io.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class LogFileWriter implements OutputProvider {

    private int flushCounter = 0;
    private final int flushFrequency = 100;
    private String outputFilePath;

    private PrintWriter logWriter;

    public LogFileWriter() {
    }

    @Override
    public synchronized void write(String inputString) {

        System.out.println("Write "+Thread.currentThread().getName());
        String text = inputString + "\n";
        logWriter.write(text, 0, text.length());
        flushCounter++;
        if (flushCounter > flushFrequency) {
            logWriter.flush();
            flushCounter = 0;
        }
        try {
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(500));
        } catch (InterruptedException e) {
            e.printStackTrace();
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