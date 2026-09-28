package service;

import java.util.List;

import dao.ApplicationDAO;
import model.Application;
import exceptions.InvalidApplicationException;

public class ApplicationService {

    private ApplicationDAO applicationDAO = new ApplicationDAO();

    // Apply for a job
    public void apply(Application application)
            throws Exception {

        if (application == null) {
            throw new InvalidApplicationException(
                    "Application cannot be null."
            );
        }

        if (application.getJobId() <= 0) {
            throw new InvalidApplicationException(
                    "Invalid Job ID."
            );
        }

        if (application.getUserId() <= 0) {
            throw new InvalidApplicationException(
                    "Invalid User ID."
            );
        }

        applicationDAO.addApplication(application);
    }

    // Get all applications from MySQL
    public List<Application> getAllApplications()
            throws Exception {

        return applicationDAO.getAllApplications();
    }

    // Update application status
    public boolean updateStatus(
            int applicationId,
            String status)
            throws Exception {

        if (applicationId <= 0) {
            throw new InvalidApplicationException(
                    "Invalid Application ID."
            );
        }

        if (status == null || status.trim().isEmpty()) {
            throw new InvalidApplicationException(
                    "Application status cannot be empty."
            );
        }

        Application application =
                applicationDAO.getApplicationById(applicationId);

        if (application != null) {

            applicationDAO.updateStatus(
                    applicationId,
                    status
            );

            return true;
        }

        return false;
    }
}