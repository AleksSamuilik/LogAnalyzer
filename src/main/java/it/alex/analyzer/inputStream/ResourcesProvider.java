package it.alex.analyzer.inputStream;

import java.util.List;

public interface ResourcesProvider {

    void setPath(String path);

    void loadFile() throws FileNotFoundException;

    List getFileList();
}