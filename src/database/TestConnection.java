package database;

import java.sql.Connection;

public class TestConnection {

    public static void main(String[] args) {

        try {
            Connection connection = DatabaseConnection.getConnection();

            System.out.println("MySQL Connection Successful! ✅");

            connection.close();

        } catch (Exception e) {
            System.out.println("Connection Failed! ❌");
            e.printStackTrace();
        }
    }
}