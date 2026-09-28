package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;
import model.Job;

public class JobDAO {

    // ================= GET ALL JOBS =================
    public List<Job> getAllJobs() throws Exception {

        List<Job> jobs = new ArrayList<>();

        String sql = "SELECT job_id, title, company, location, description, skills " +
                     "FROM jobs";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Job job = new Job(
                        rs.getInt("job_id"),
                        rs.getString("title"),
                        rs.getString("company"),
                        rs.getString("location"),
                        rs.getString("description"),
                        rs.getString("skills")
                );

                jobs.add(job);
            }
        }

        return jobs;
    }


    // ================= GET JOB BY ID =================
    public Job getJobById(int jobId) throws Exception {

        String sql = "SELECT job_id, title, company, location, " +
                     "description, skills " +
                     "FROM jobs WHERE job_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, jobId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new Job(
                            rs.getInt("job_id"),
                            rs.getString("title"),
                            rs.getString("company"),
                            rs.getString("location"),
                            rs.getString("description"),
                            rs.getString("skills")
                    );
                }
            }
        }

        return null;
    }


    // ================= ADD NEW JOB =================
    public void addJob(Job job) throws Exception {

        String sql = "INSERT INTO jobs " +
                     "(title, company, location, description, skills) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, job.getTitle());
            ps.setString(2, job.getCompany());
            ps.setString(3, job.getLocation());
            ps.setString(4, job.getDescription());
            ps.setString(5, job.getSkills());

            ps.executeUpdate();
        }
    }
}