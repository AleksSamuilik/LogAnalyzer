package it.alex.analyzer;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class App {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                SpringConfig.class
        );
        LogsHandler handler = context.getBean("logsHandler", LogsHandler.class);
        handler.handler(args);
    }
}

