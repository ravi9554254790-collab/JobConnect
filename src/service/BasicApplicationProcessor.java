package service;

import model.Application;

public class BasicApplicationProcessor implements ApplicationProcessor {

    @Override
    public void process(Application application) {

        System.out.println(
            "Basic Processing - Application ID: "
            + application.getApplicationId()
        );

        System.out.println(
            "Status: " + application.getStatus()
        );
    }
}