package it.alex.analyzer.inputStream;

import java.util.List;

public interface ResourcesProvider {

    void loadFile() throws FileNotFoundException;

    List getFileList();
}
