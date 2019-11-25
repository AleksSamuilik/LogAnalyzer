package it.alex.analyzer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class AppTest {

    @Parameterized.Parameters(name = "{index}:Input String{0} expected result {1} input arguments {2}")
    public static Iterable<Object[]> data() {
        Object[][] data = new Object[][]{
                {

                }, {

        }, {

        }
        };
        return Arrays.asList(data);
    }


    private final String input;
    private final String expected;
    private final String arguments;


    public AppTest(String input, String expected, String arguments) {
        this.input = input;
        this.expected = expected;
        this.arguments = arguments;
    }


    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }


}