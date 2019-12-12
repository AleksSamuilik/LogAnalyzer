package it.alex.analyzer;

import it.alex.analyzer.analysis.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;


@RunWith(Parameterized.class)
public class FalseTest {
    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        Object[][] data = new Object[][]{
                {"2017/06/01 15:37:18,186  [INFO] Aleks main resourcePath.ResourceStarter:17 - Starting resources...", new ArrayList(Arrays.asList("-d-2017/06/21", "-n-Aleks"))},
                {"2017/06/01 15:37:18,186  [INFO] Aleks main resourcePath.ResourceStarter:17 - Starting resources...", new ArrayList(Arrays.asList("-p-Starts"))},
                {"2017/06/01 15:37:18,186  [INFO] Aleks main resourcePath.ResourceStarter:17 - Starting resources...", new ArrayList(Arrays.asList("-n-Sam"))},
                {"2017/06/01 15:37:18,186  [INFO] Aleks main resourcePath.ResourceStarter:17 - Starting resources...", new ArrayList(Arrays.asList("-d-2017/06/21"))},
                {"2017/06/01 15:37:18,186  [INFO] Sam main resourcePath.ResourceStarter:17 - Starting resources...", new ArrayList(Arrays.asList("-d-2017/06/21", "-d-2017/06/01", "-n-Maks", "-n-Aleks"))},
                {"2017/06/21 15:37:18,186  [INFO] Maks main resourcePath.ResourceStarter:17 - Starting resources...", new ArrayList(Arrays.asList("-d-2017/06/21", "-d-2017/06/01", "-n-Sam", "-n-Aleks", "-p-Start"))}};
        return Arrays.asList(data);
    }


    LogAnalysis logAnalysis = new SearchEngine();

    private String input;
    private List arguments;


    public FalseTest(String input, List arguments) {
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
    public void falseTest() {
        createFilterList();
        logAnalysis.initialArguments();
        assertFalse(logAnalysis.isValid(input));
    }
}

