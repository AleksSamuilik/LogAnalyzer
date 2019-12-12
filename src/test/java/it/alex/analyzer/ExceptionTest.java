package it.alex.analyzer;

import it.alex.analyzer.analysis.*;
import it.alex.analyzer.inputStream.FileNotFoundException;
import it.alex.analyzer.inputStream.ResourcesLoader;
import it.alex.analyzer.inputStream.ResourcesProvider;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExceptionTest {


    LogAnalysis logAnalysis = new SearchEngine();
    ResourcesProvider resourcesProvider = new ResourcesLoader();
    public void createFilterList(String ... input) {
        List<LogFilter> logFilterList = new ArrayList<>();
        logFilterList.add(new DateFilter(new ArrayList(Arrays.asList(input))));
        logFilterList.add(new UserFilter(new ArrayList(Arrays.asList(input))));
        logFilterList.add(new MessageFilter(new ArrayList(Arrays.asList(input))));
        logAnalysis.setFilterList(logFilterList);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void argumentExceptionTest1() {
        createFilterList();
        thrown.expect(ArgumentsException.class);
        thrown.expectMessage("Incorrect arguments");
        logAnalysis.initialArguments();
    }

    @Test
    public void argumentExceptionTest2() {
        createFilterList("add");
        thrown.expect(ArgumentsException.class);
        thrown.expectMessage("Incorrect arguments");
        logAnalysis.initialArguments();
    }

    @Test
    public void fileNotFoundExceptionTest() {
        thrown.expect(FileNotFoundException.class);
        thrown.expectMessage("File not found");
        resourcesProvider.setPath("D:/Java/LogAnalyzer"); // usually point to a folder with test resources.
        resourcesProvider.loadFile();
    }
}
