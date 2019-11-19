package it.alex.analyzer.inputStream;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ResourcesLoader implements ResourcesProvider {
    private List<File> fileList = new ArrayList<>();


    public ResourcesLoader() {
        loadFile();
    }

    private void loadFile() {
        File dir = new File(getClass().getClassLoader().getResource("folderLogFiles").getFile());
        File[] folder = dir.listFiles();
        for (int i = 0; i < folder.length; i++) {
            if (folder[i].isFile()) {
                if (folder[i].getName().endsWith(".log")) ;
                fileList.add(folder[i]);
            }
        }
    }

    @Override
    public List getFile() {
        return fileList;
    }
}
