package gui;

import javax.swing.*;
import java.awt.*;

import model.User;
import dao.UserDAO;

public class RegisterFrame extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;
    private JButton registerButton;

    private UserDAO userDAO = new UserDAO();

    public RegisterFrame() {

        setTitle("JobConnect - Registration");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel nameLabel = new JLabel("Name:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel roleLabel = new JLabel("Role:");

        nameField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField();

        String[] roles = {
            "Job Seeker",
            "Recruiter"
        };

        roleBox = new JComboBox<>(roles);

        registerButton = new JButton("Register");

        panel.add(nameLabel);
        panel.add(nameField);

        panel.add(emailLabel);
        panel.add(emailField);

        panel.add(passwordLabel);
        panel.add(passwordField);

        panel.add(roleLabel);
        panel.add(roleBox);

        panel.add(new JLabel(""));
        panel.add(registerButton);

        add(panel);

        registerButton.addActionListener(e -> register());
    }

    private void register() {

        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String role = (String) roleBox.getSelectedItem();

        // Validation
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "Please fill all fields.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        try {

            // Create User object
            User user = new User(
                0,
                name,
                email,
                password,
                role
            );

            // Insert user into MySQL
            userDAO.insertUser(user);

            JOptionPane.showMessageDialog(
                this,
                "Registration successful! ✅",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );

            // Clear fields
            nameField.setText("");
            emailField.setText("");
            passwordField.setText("");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                this,
                "Registration failed!\n" + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }
}