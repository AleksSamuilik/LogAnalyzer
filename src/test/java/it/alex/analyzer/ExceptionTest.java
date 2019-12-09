package it.alex.analyzer;

import it.alex.analyzer.analysis.ArgumentsException;
import it.alex.analyzer.analysis.LogAnalysis;
import it.alex.analyzer.analysis.SearchEngine;
import it.alex.analyzer.inputStream.FileNotFoundException;
import it.alex.analyzer.inputStream.ResourcesLoader;
import it.alex.analyzer.inputStream.ResourcesProvider;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;

public class ExceptionTest {


    LogAnalysis logAnalysis;
    ResourcesProvider resourcesProvider;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void argumentExceptionTest1() {
        thrown.expect(ArgumentsException.class);
        thrown.expectMessage("Incorrect arguments");
        this.logAnalysis = new SearchEngine();
        logAnalysis.setArgsList(new ArrayList(Arrays.asList()));
        logAnalysis.initialArguments();
    }

    @Test
    public void argumentExceptionTest2() {
        thrown.expect(ArgumentsException.class);
        thrown.expectMessage("Incorrect arguments");
        this.logAnalysis = new SearchEngine();
        logAnalysis.setArgsList(new ArrayList(Arrays.asList("add")));
        logAnalysis.initialArguments();
    }

    @Test
    public void fileNotFoundExceptionTest() {
        thrown.expect(FileNotFoundException.class);
        thrown.expectMessage("File not found");
        this.resourcesProvider = new ResourcesLoader();
        resourcesProvider.setPath("D:/Java/LogAnalyzer"); // usually point to a folder with test resources.
        resourcesProvider.loadFile();
    }
}
