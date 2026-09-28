package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import model.Application;
import model.Job;
import service.ApplicationService;
import service.ApplicationStatusUpdater;
import dao.JobDAO;

public class RecruiterFrame extends JFrame {

    private JobDAO jobDAO = new JobDAO();
    private ApplicationService applicationService = new ApplicationService();

    public RecruiterFrame() {

        setTitle("JobConnect - Recruiter Dashboard");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel title = new JLabel(
                "Welcome to JobConnect - Recruiter",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 20));

        JButton postJobButton = new JButton("Post Job");
        JButton viewJobsButton = new JButton("My Posted Jobs");
        JButton applicationsButton = new JButton("View Applications");
        JButton statusButton = new JButton("Update Application Status");
        JButton logoutButton = new JButton("Logout");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));

        panel.add(title);
        panel.add(postJobButton);
        panel.add(viewJobsButton);
        panel.add(applicationsButton);
        panel.add(statusButton);
        panel.add(logoutButton);

        add(panel);

        postJobButton.addActionListener(e -> postJob());
        viewJobsButton.addActionListener(e -> viewPostedJobs());
        applicationsButton.addActionListener(e -> viewApplications());
        statusButton.addActionListener(e -> updateApplicationStatus());
        logoutButton.addActionListener(e -> logout());
    }


    // ================= POST JOB =================

    private void postJob() {

        JTextField titleField = new JTextField();
        JTextField companyField = new JTextField();
        JTextField locationField = new JTextField();
        JTextField skillsField = new JTextField();

        JTextArea descriptionArea = new JTextArea(4, 20);

        JPanel panel = new JPanel(
                new GridLayout(5, 2, 10, 10)
        );

        panel.add(new JLabel("Job Title:"));
        panel.add(titleField);

        panel.add(new JLabel("Company:"));
        panel.add(companyField);

        panel.add(new JLabel("Location:"));
        panel.add(locationField);

        panel.add(new JLabel("Skills:"));
        panel.add(skillsField);

        panel.add(new JLabel("Description:"));
        panel.add(new JScrollPane(descriptionArea));

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Post New Job",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String title = titleField.getText().trim();
        String company = companyField.getText().trim();
        String location = locationField.getText().trim();
        String skills = skillsField.getText().trim();
        String description = descriptionArea.getText().trim();

        if (title.isEmpty()
                || company.isEmpty()
                || location.isEmpty()
                || skills.isEmpty()
                || description.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        Job job = new Job(
                0,
                title,
                company,
                location,
                description,
                skills
        );

        try {

            jobDAO.addJob(job);

            JOptionPane.showMessageDialog(
                    this,
                    "Job posted successfully! ✅",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to post job!\n" + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }


    // ================= VIEW POSTED JOBS =================

    private void viewPostedJobs() {

        try {

            List<Job> jobs = jobDAO.getAllJobs();

            if (jobs.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "You have not posted any jobs yet.",
                        "My Posted Jobs",
                        JOptionPane.INFORMATION_MESSAGE
                );

                return;
            }

            StringBuilder jobsText = new StringBuilder();

            jobsText.append("Jobs from MySQL Database\n\n");

            for (Job job : jobs) {

                jobsText.append("Job ID: ")
                        .append(job.getJobId())
                        .append("\n");

                jobsText.append("Title: ")
                        .append(job.getTitle())
                        .append("\n");

                jobsText.append("Company: ")
                        .append(job.getCompany())
                        .append("\n");

                jobsText.append("Location: ")
                        .append(job.getLocation())
                        .append("\n");

                jobsText.append("Skills: ")
                        .append(job.getSkills())
                        .append("\n");

                jobsText.append("Description: ")
                        .append(job.getDescription())
                        .append("\n");

                jobsText.append("-----------------------------\n");
            }

            JOptionPane.showMessageDialog(
                    this,
                    jobsText.toString(),
                    "My Posted Jobs",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load jobs!\n" + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }


    // ================= VIEW APPLICATIONS =================

    private void viewApplications() {

        try {

            List<Application> applications =
                    applicationService.getAllApplications();

            if (applications.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "No applications available yet.",
                        "Applications",
                        JOptionPane.INFORMATION_MESSAGE
                );

                return;
            }

            StringBuilder applicationsText =
                    new StringBuilder();

            applicationsText.append("Applications\n\n");

            for (Application application : applications) {

                applicationsText.append("Application ID: ")
                        .append(application.getApplicationId())
                        .append("\n");

                applicationsText.append("Job ID: ")
                        .append(application.getJobId())
                        .append("\n");

                applicationsText.append("Applicant User ID: ")
                        .append(application.getUserId())
                        .append("\n");

                applicationsText.append("Status: ")
                        .append(application.getStatus())
                        .append("\n\n");
            }

            JOptionPane.showMessageDialog(
                    this,
                    applicationsText.toString(),
                    "Applications",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load applications!\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }


    // ================= UPDATE APPLICATION STATUS =================

    private void updateApplicationStatus() {

        try {

            List<Application> applications =
                    applicationService.getAllApplications();

            if (applications.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "No applications available yet.",
                        "Update Status",
                        JOptionPane.INFORMATION_MESSAGE
                );

                return;
            }

            String applicationIdText =
                    JOptionPane.showInputDialog(
                            this,
                            "Enter Application ID:"
                    );

            if (applicationIdText == null) {
                return;
            }

            int applicationId =
                    Integer.parseInt(applicationIdText);

            String[] statuses = {
                    "Applied",
                    "Shortlisted",
                    "Selected",
                    "Rejected"
            };

            String status = (String) JOptionPane.showInputDialog(
                    this,
                    "Select new status:",
                    "Update Application Status",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    statuses,
                    statuses[0]
            );

            if (status == null) {
                return;
            }

            // Update status in MySQL
            boolean updated =
                    applicationService.updateStatus(
                            applicationId,
                            status
                    );

            if (updated) {

                // Start multithreading
                ApplicationStatusUpdater updater =
                        new ApplicationStatusUpdater(
                                applicationId,
                                status
                        );

                updater.start();

                JOptionPane.showMessageDialog(
                        this,
                        "Application status updated successfully! ✅",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Application ID not found.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid Application ID.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to update application!\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }


    // ================= LOGOUT =================

    private void logout() {

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Do you want to logout?",
                "Logout",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {

            new LoginFrame().setVisible(true);
            dispose();
        }
    }
}