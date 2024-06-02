package demo_users_crud;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class DbConnectionManager {
    String url = "jdbc:postgresql://localhost:5432/users";
    String login = "postgres";
    String password = "AdminTest1!";
    Connection c = null;
    Statement stmt = null;

    public void connect() {
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, login, password);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void close() {
        try {
            c.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public ResultSet query(String query) {
        ResultSet rs = null;
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public boolean update(String query) {
        try {
            stmt = c.createStatement();
            stmt.executeUpdate(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
