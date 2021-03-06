package it.alex.analyzer;

import it.alex.analyzer.analysis.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TryTest {

    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        Object[][] data = new Object[][]{
                {"2017/06/01 15:37:18,186  [INFO] Aleks main resourcePath.ResourceStarter:17 - Starting resources...", new ArrayList(Arrays.asList("-d-2017/06/21", "-d-2017/06/01", "-n-Sam", "-n-Aleks"))},
                {"2017/06/01 15:37:18,186  [INFO] Aleks main resourcePath.ResourceStarter:17 - Starting resources...", new ArrayList(Arrays.asList("-p-res"))},
                {"2017/06/01 15:37:18,186  [INFO] Aleks main resourcePath.ResourceStarter:17 - Starting resources...", new ArrayList(Arrays.asList("-n-Aleks"))},
                {"2017/06/01 15:37:18,186  [INFO] Aleks main resourcePath.ResourceStarter:17 - Starting resources...", new ArrayList(Arrays.asList("-d-2017/06/01"))},
                {"2017/06/01 15:37:18,186  [INFO] Sam main resourcePath.ResourceStarter:17 - Starting resources...", new ArrayList(Arrays.asList("-d-2017/06/21", "-d-2017/06/01", "-n-Sam", "-n-Aleks"))},
                {"2017/06/21 15:37:18,186  [INFO] Aleks main resourcePath.ResourceStarter:17 - Starting resources...", new ArrayList(Arrays.asList("-d-2017/06/21", "-d-2017/06/01", "-n-Sam", "-n-Aleks"))},
                {"2017/06/21 15:37:18,186  [INFO] Sam main resourcePath.ResourceStarter:17 - Starting resources...", new ArrayList(Arrays.asList("-d-2017/06/21", "-d-2017/06/01", "-n-Sam", "-n-Aleks"))},
                {"2017/06/01 15:37:18,186  [INFO] Sam main resourcePath.ResourceStarter:17 - Starting resources...", new ArrayList(Arrays.asList("-d-2017/06/21", "-d-2017/06/01", "-n-Sam", "-n-Aleks", "-p-Start"))}};
        return Arrays.asList(data);
    }


    LogAnalysis logAnalysis = new SearchEngine();

    private String input;
    private List arguments;


    public TryTest(String input, List arguments) {
        this.input = input;
        this.arguments = arguments;

    }

    public void createFilterList() {
        List<LogFilter> logFilterList = new ArrayList<>();
        logFilterList.add(new DateFilter(arguments));
        logFilterList.add(new UserFilter(arguments));
        logFilterList.add(new MessageFilter(arguments));
        logAnalysis.setFilterList(logFilterList);
    }


    @Test
    public void rightTest() {
        createFilterList();
        logAnalysis.initialArguments();
        assertEquals(true, logAnalysis.isValid(input));
    }
}