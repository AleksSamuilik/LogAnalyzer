package it.alex.analyzer;

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
 * command line arguments example: D:/LogFolder -d-2019/11/01 -n-Sam -p-Context: ClassPath
 * log files directory\
 * * -d-yyyy/MM/dd
 * -n-UserName
 * -p-message
 */

public class App {
    private static List<String> argsList;

    public static void main(String[] args) {
        argsList=new ArrayList(Arrays.asList(args));
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                SpringConfig.class
        );
        LogsHandler handler = context.getBean("logsHandler", LogsHandler.class);
        handler.start();
        context.close();

    }

    public static   List getArgs() {
        return argsList;
    }
}

