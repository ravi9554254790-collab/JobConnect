package service;

import model.Application;

public class PriorityApplicationProcessor
        extends BasicApplicationProcessor {

    @Override
    public void process(Application application) {

        System.out.println(
            "Priority Processing - Application ID: "
            + application.getApplicationId()
        );

        System.out.println(
            "Priority Status: " + application.getStatus()
        );
    }
}