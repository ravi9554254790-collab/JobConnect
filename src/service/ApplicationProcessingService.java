package service;

import model.Application;

public class ApplicationProcessingService {

    public synchronized void processApplication(Application application) {

        System.out.println(
            "Processing Application ID: "
            + application.getApplicationId()
            + " | Status: "
            + application.getStatus()
        );

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(
            "Application ID "
            + application.getApplicationId()
            + " processed successfully."
        );
    }

    public void processInBackground(Application application) {

        Thread thread = new Thread(() -> {
            processApplication(application);
        });

        thread.start();
    }
}