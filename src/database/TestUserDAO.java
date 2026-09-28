package database;

import dao.UserDAO;
import model.User;

public class TestUserDAO {

    public static void main(String[] args) {

        try {

            UserDAO userDAO = new UserDAO();

            User user = new User(
                0,
                "Test User",
                "testuser@gmail.com",
                "12345",
                "Job Seeker"
            );

            userDAO.insertUser(user);

            System.out.println("User inserted successfully!");

            for (User u : userDAO.getAllUsers()) {
                System.out.println(
                    u.getUserId() + " | " +
                    u.getName() + " | " +
                    u.getEmail() + " | " +
                    u.getRole()
                );
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}