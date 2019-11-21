package it.alex.analyzer.outputStream;

import java.io.File;

public interface OutputProvider {
    void write(String string);

    void close();

    void createOutputFile();

    void setPathOutputFile(File inputFile);
}
