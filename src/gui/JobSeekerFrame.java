package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import model.Application;
import model.Job;
import dao.JobDAO;
import service.ApplicationService;

public class JobSeekerFrame extends JFrame {

    private ApplicationService applicationService = new ApplicationService();
    private JobDAO jobDAO = new JobDAO();

    private int applicationId = 1;

    private String userName = "Ravi Singh";
    private String userEmail = "ravi@example.com";
    private String userRole = "Job Seeker";

    public JobSeekerFrame() {

        setTitle("JobConnect - Job Seeker Dashboard");
        setSize(600, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel title = new JLabel(
                "Welcome to JobConnect - Job Seeker",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 20));

        JButton searchButton = new JButton("Search Jobs");
        JButton viewButton = new JButton("View Jobs");
        JButton applicationButton = new JButton("My Applications");
        JButton profileButton = new JButton("My Profile");
        JButton logoutButton = new JButton("Logout");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));

        panel.add(title);
        panel.add(searchButton);
        panel.add(viewButton);
        panel.add(applicationButton);
        panel.add(profileButton);
        panel.add(logoutButton);

        add(panel);

        searchButton.addActionListener(e -> searchJobs());
        viewButton.addActionListener(e -> viewJobs());
        applicationButton.addActionListener(e -> showApplications());
        profileButton.addActionListener(e -> showProfile());
        logoutButton.addActionListener(e -> logout());
    }

    // SEARCH JOBS FROM MYSQL
    private void searchJobs() {

        try {

            List<Job> jobs = jobDAO.getAllJobs();

            if (jobs.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "No jobs available.",
                        "Search Jobs",
                        JOptionPane.INFORMATION_MESSAGE
                );

                return;
            }

            String[] jobOptions = new String[jobs.size()];

            for (int i = 0; i < jobs.size(); i++) {

                Job job = jobs.get(i);

                jobOptions[i] =
                        job.getJobId() + ". " +
                        job.getTitle() + " - " +
                        job.getCompany();
            }

            String selected = (String) JOptionPane.showInputDialog(
                    this,
                    "Select a job:",
                    "Search Jobs",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    jobOptions,
                    jobOptions[0]
            );

            if (selected == null) {
                return;
            }

            int selectedJobId =
                    Integer.parseInt(selected.split("\\.")[0]);

            Job selectedJob = jobDAO.getJobById(selectedJobId);

            if (selectedJob == null) {
                return;
            }

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Job Details\n\n" +
                    "Title: " + selectedJob.getTitle() + "\n" +
                    "Company: " + selectedJob.getCompany() + "\n" +
                    "Location: " + selectedJob.getLocation() + "\n" +
                    "Skills: " + selectedJob.getSkills() + "\n" +
                    "Description: " + selectedJob.getDescription() +
                    "\n\nDo you want to apply?",
                    "Job Details",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {

                Application application = new Application(
                        applicationId++,
                        1,
                        selectedJob.getJobId(),
                        "Applied"
                );

                // Save application into MySQL
                applicationService.apply(application);

                JOptionPane.showMessageDialog(
                        this,
                        "Application submitted successfully! ✅",
                        "Application",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load jobs.\n" + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

    // MY APPLICATIONS FROM MYSQL
    private void showApplications() {

        try {

            List<Application> applicationList =
                    applicationService.getAllApplications();

            if (applicationList.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "You have not applied for any jobs yet.",
                        "My Applications",
                        JOptionPane.INFORMATION_MESSAGE
                );

                return;
            }

            StringBuilder applications = new StringBuilder();

            applications.append("My Applications\n\n");

            for (Application application : applicationList) {

                applications.append("Application ID: ")
                        .append(application.getApplicationId())
                        .append("\n");

                applications.append("Job ID: ")
                        .append(application.getJobId())
                        .append("\n");

                applications.append("Status: ")
                        .append(application.getStatus())
                        .append("\n\n");
            }

            JOptionPane.showMessageDialog(
                    this,
                    applications.toString(),
                    "My Applications",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load applications.\n" + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

    // MY PROFILE
    private void showProfile() {

        String profile =
                "My Profile\n\n" +
                "Name: " + userName + "\n" +
                "Email: " + userEmail + "\n" +
                "Role: " + userRole;

        int choice = JOptionPane.showConfirmDialog(
                this,
                profile + "\n\nDo you want to edit your profile?",
                "My Profile",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            editProfile();
        }
    }

    // EDIT PROFILE
    private void editProfile() {

        JTextField nameField = new JTextField(userName);
        JTextField emailField = new JTextField(userEmail);

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        panel.add(new JLabel("Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Edit Profile",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {

            if (nameField.getText().isEmpty()
                    || emailField.getText().isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Name and Email cannot be empty."
                );

                return;
            }

            userName = nameField.getText();
            userEmail = emailField.getText();

            JOptionPane.showMessageDialog(
                    this,
                    "Profile updated successfully! ✅"
            );
        }
    }

    // VIEW JOBS FROM MYSQL
    private void viewJobs() {

        try {

            List<Job> jobs = jobDAO.getAllJobs();

            if (jobs.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "No jobs available.",
                        "Available Jobs",
                        JOptionPane.INFORMATION_MESSAGE
                );

                return;
            }

            StringBuilder jobList = new StringBuilder();

            jobList.append("Available Jobs\n\n");

            for (Job job : jobs) {

                jobList.append("Job ID: ")
                        .append(job.getJobId())
                        .append("\n");

                jobList.append("Title: ")
                        .append(job.getTitle())
                        .append("\n");

                jobList.append("Company: ")
                        .append(job.getCompany())
                        .append("\n");

                jobList.append("Location: ")
                        .append(job.getLocation())
                        .append("\n");

                jobList.append("Skills: ")
                        .append(job.getSkills())
                        .append("\n");

                jobList.append("Description: ")
                        .append(job.getDescription())
                        .append("\n\n");
            }

            JOptionPane.showMessageDialog(
                    this,
                    jobList.toString(),
                    "Available Jobs",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load jobs.\n" + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

    // LOGOUT
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