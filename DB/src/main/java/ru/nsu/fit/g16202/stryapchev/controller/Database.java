package main.java.ru.nsu.fit.g16202.stryapchev.controller;

import java.sql.*;

public class Database {

    private static final String DB_URL = "jdbc:postgresql:postgres";
    private static final String USER = "postgres";
    private static final String PASS = "1748";
    private static Connection connection;

    public void setConnection() {
        System.out.println("Testing connection to PostgreSQL JDBC");
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
            return;
        }

        System.out.println("PostgreSQL JDBC Driver successfully connected");

        try {
            connection = DriverManager
                    .getConnection(DB_URL, USER, PASS);

        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("You successfully connected to database now");
        } else {
            System.out.println("Failed to make connection to database");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void getLawyersNamesForCase(Integer _case_id) throws SQLException {
        Statement statement;
        statement = connection.createStatement();
        String query = "SELECT l.lawyer_name FROM cases c JOIN lawyers " +
                "l ON c.case_lawyer_id = l.lawyer_id WHERE c.case_id = " + _case_id;
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            StringBuilder line = new StringBuilder();
        }
    }
}