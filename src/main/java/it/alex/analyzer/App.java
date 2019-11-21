package it.alex.analyzer;

import it.alex.analyzer.analysis.ArgumentsException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * String[]args
 * [0] - file directory location
 * [1] - date to search
 * [2] - user name to search
 * [3] - pattern message to search
 * <p>
 * command line arguments example: D:\Java\LogAnalyzer\folderLogFiles -d-2019/11/01 -n-Sam -p-Context: ClassPath
 * log files directory\
 * -d-yyyy/MM/dd
 * -n-UserName
 * -p-message
 */

public class App {
    private static List<String> argsList;

    public static void main(String[] args) {
        argsList = new ArrayList(Arrays.asList(args));
        try {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                    SpringConfig.class
            );
            LogsHandler handler = context.getBean("logsHandler", LogsHandler.class);
            handler.start();
            context.close();
        } catch (ArgumentsException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List getArgs() {
        return argsList;
    }
}

