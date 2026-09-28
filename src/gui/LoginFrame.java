package gui;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dao.UserDAO;
import model.User;

public class LoginFrame extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;
    private JButton loginButton;
    private JButton registerButton;

    private UserDAO userDAO = new UserDAO();

    public LoginFrame() {

        setTitle("JobConnect - Login");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo((Component) null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel emailLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel roleLabel = new JLabel("Login As:");

        emailField = new JTextField();
        passwordField = new JPasswordField();

        String[] roles = {
            "Job Seeker",
            "Recruiter"
        };

        roleBox = new JComboBox<>(roles);

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        panel.add(emailLabel);
        panel.add(emailField);

        panel.add(passwordLabel);
        panel.add(passwordField);

        panel.add(roleLabel);
        panel.add(roleBox);

        panel.add(new JLabel(""));
        panel.add(loginButton);

        panel.add(new JLabel(""));
        panel.add(registerButton);

        add(panel);

        // Login button
        loginButton.addActionListener(e -> login());

        // Register button
        registerButton.addActionListener(e -> {

            new RegisterFrame().setVisible(true);

            dispose();
        });
    }

    // Login method
    private void login() {

        String email = emailField.getText().trim();

        String password =
                new String(passwordField.getPassword());

        String selectedRole =
                (String) roleBox.getSelectedItem();

        // Validation
        if (email.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "Please enter Email and Password",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        try {

            // Check user in MySQL
            User user =
                    userDAO.loginUser(
                        email,
                        password,
                        selectedRole
                    );

            // Invalid login
            if (user == null) {

                JOptionPane.showMessageDialog(
                    this,
                    "Invalid Email, Password or Role!",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            // Successful login
            JOptionPane.showMessageDialog(
                this,
                "Login Successful! ✅",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );

            // Open dashboard according to role
            if (selectedRole.equals("Recruiter")) {

                new RecruiterFrame().setVisible(true);

            } else {

                new JobSeekerFrame().setVisible(true);
            }

            // Close login window
            dispose();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                this,
                "Database Error!\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }
}