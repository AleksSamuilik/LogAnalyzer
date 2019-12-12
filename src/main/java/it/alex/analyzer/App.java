package it.alex.analyzer;

import it.alex.analyzer.analysis.ArgumentsException;
import it.alex.analyzer.inputStream.FileNotFoundException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * FORMAT LOG FILE:
 * yyyy/MM/dd hh:mm:ss,sss  [TYPE_MESSAGE] USER_NAME SERVER NAME_CLASS NAME_METHOD - TEXT MESSAGE
 * EXAMPLE:
 * 2017/06/01 15:37:18,186  [INFO] Aleks main resourcePath.ResourceStarter:17 - Starting resources...
 * <p>
 * String[]args
 * [0] - file directory location
 * [1] - number of threads
 * [2] - date to search
 * [3] - user name to search
 * [4] - pattern message to search
 * <p>
 * java -jar LogAnalyzer-1.0-SNAPSHOT-jar-with-dependencies.jar D:\Java\LogAnalyzer\folderLogFiles -t-3 -d-2017/06/21 -d-2017/06/01 -n-Sam -n-Aleks
 * command line arguments example: D:\Java\LogAnalyzer\folderLogFiles -t-3 -d-2019/11/01 -d-2019/11/21 -n-Sam -p-Context: ClassPath
 * log files directory\
 * -t-1* default.
 * If more than the number of input files is specified, then the value will be equal to the number of input files.
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
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List getArgs() {
        return argsList;
    }
}