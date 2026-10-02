package service;

import model.Application;

public class ApplicationProcessorDemo {

    public static void main(String[] args) {

        Application application =
                new Application(101, 1, 1, "Applied");

        ApplicationProcessor processor;

        processor = new BasicApplicationProcessor();
        processor.process(application);

        processor = new PriorityApplicationProcessor();
        processor.process(application);
    }
}