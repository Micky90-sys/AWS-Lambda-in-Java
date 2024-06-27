package com.example.awslambda.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseService {

    private static final String URL = "jdbc:mysql://your-rds-endpoint:3306/your-database";
    private static final String USER = "your-username";
    private static final String PASSWORD = "your-password";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public String createRecord(String data) {
        try (Connection connection = getConnection()) {
            String query = "INSERT INTO your_table (data) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, data);
            statement.executeUpdate();
            return "Record created successfully";
        } catch (SQLException e) {
            return "Error creating record: " + e.getMessage();
        }
    }

    public String readRecord(String id) {
        try (Connection connection = getConnection()) {
            String query = "SELECT data FROM your_table WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return "Record data: " + resultSet.getString("data");
            } else {
                return "Record not found";
            }
        } catch (SQLException e) {
            return "Error reading record: " + e.getMessage();
        }
    }

    public String updateRecord(String data) {
        try (Connection connection = getConnection()) {
            String query = "UPDATE your_table SET data = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, data);
            statement.setString(2, "your-id"); // Replace with appropriate ID
            statement.executeUpdate();
            return "Record updated successfully";
        } catch (SQLException e) {
            return "Error updating record: " + e.getMessage();
        }
    }

    public String deleteRecord(String id) {
        try (Connection connection = getConnection()) {
            String query = "DELETE FROM your_table WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            statement.executeUpdate();
            return "Record deleted successfully";
        } catch (SQLException e) {
            return "Error deleting record: " + e.getMessage();
        }
    }
}
