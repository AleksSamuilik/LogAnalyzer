package it.alex.analyzer.inputStream;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ResourcesLoader implements ResourcesProvider {
    private String path;
    private List<File> fileList = new ArrayList<>();

    public ResourcesLoader(String path) throws FileNotFoundException {
        this.path = path;
    }

    @Override
    public void loadFile() throws FileNotFoundException{
        File dir = new File(path);
        File[] folder = dir.listFiles();

        for (int i = 0; i < folder.length; i++) {
            if (folder[i].isFile()) {
                if (folder[i].getName().equals("LogAnalyzer.log")) {
                    continue;
                } else if (folder[i].getName().endsWith(".log")) {
                    fileList.add(folder[i]);
                }
            }
        }
        if (fileList.isEmpty()){
           throw new FileNotFoundException("File not found");
        }
    }

    @Override
    public List getFileList() {
        return fileList;
    }
}