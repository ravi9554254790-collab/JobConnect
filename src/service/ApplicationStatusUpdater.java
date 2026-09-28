package service;

public class ApplicationStatusUpdater extends Thread {

    private int applicationId;
    private String status;

    public ApplicationStatusUpdater(int applicationId, String status) {
        this.applicationId = applicationId;
        this.status = status;
    }

    @Override
    public synchronized void run() {

        System.out.println(
                "Updating Application ID: "
                + applicationId
                + " to status: "
                + status
        );

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted.");
        }

        System.out.println(
                "Application status updated successfully!"
        );
    }
}