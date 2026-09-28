package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;
import model.Application;

public class ApplicationDAO {

    // Add application into MySQL
    public void addApplication(Application application) throws Exception {

        String sql = "INSERT INTO applications " +
                     "(application_id, user_id, job_id, status) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, application.getApplicationId());
            ps.setInt(2, application.getUserId());
            ps.setInt(3, application.getJobId());
            ps.setString(4, application.getStatus());

            ps.executeUpdate();
        }
    }

    // Get all applications
    public List<Application> getAllApplications() throws Exception {

        List<Application> applications = new ArrayList<>();

        String sql = "SELECT application_id, user_id, job_id, status " +
                     "FROM applications";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Application application = new Application(
                    rs.getInt("application_id"),
                    rs.getInt("user_id"),
                    rs.getInt("job_id"),
                    rs.getString("status")
                );

                applications.add(application);
            }
        }

        return applications;
    }

    // Find application by ID
    public Application getApplicationById(int applicationId)
            throws Exception {

        String sql = "SELECT application_id, user_id, job_id, status " +
                     "FROM applications WHERE application_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, applicationId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new Application(
                        rs.getInt("application_id"),
                        rs.getInt("user_id"),
                        rs.getInt("job_id"),
                        rs.getString("status")
                    );
                }
            }
        }

        return null;
    }

    // Update application status
    public void updateStatus(int applicationId, String status)
            throws Exception {

        String sql = "UPDATE applications SET status = ? " +
                     "WHERE application_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, applicationId);

            ps.executeUpdate();
        }
    }
}