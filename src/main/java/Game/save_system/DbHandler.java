package Game.save_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class DbHandler {
    private static final String url = "jdbc:sqlite:database.db";

    DbHandler() {
        connect();
        init();
    }
    
    public void connect() {
        Connection conn = null;
        try {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                System.out.println("Error setting SQLite class");
                System.out.println(e.getMessage());
            }
            // Connect to the database
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
            
        } catch (SQLException e) {
            System.out.println("Error in SQL connection");
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error in SQL connection");
                System.out.println(ex.getMessage());
            }
        }
    }

    public void init() {
        String sql1 = "CREATE TABLE IF NOT EXISTS Structures (\n"
                + "    Name TEXT PRIMARY KEY,\n"
                + "    Grid TEXT\n"
                + ");";

        String sql2 = "CREATE TABLE IF NOT EXISTS Game (\n"
                + "    Name TEXT PRIMARY KEY,\n"
                + "    Grid TEXT\n"
                + ");";

        String sql3 = "CREATE TABLE IF NOT EXISTS Rulebooks (\n"
                + "    Name TEXT PRIMARY KEY,\n"
                + "    Content TEXT\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()) {
            // Create tables if they dont already exist
            stmt.execute(sql1);
            stmt.execute(sql2);
            stmt.execute(sql3);
            System.out.println("Tables created successfully.");
        } catch (SQLException e) {
            System.out.println("Error in SQL initialization");
            System.out.println(e.getMessage());
        }
    }

    // Specifically for grid-based stuff, does not encompass rules
    public void addGridEntry(String tableName, String name, String grid) {
        String sql = "INSERT INTO " + tableName + " (Name, Grid) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, grid);
            pstmt.executeUpdate();
            System.out.println("Entry added successfully to " + tableName + " table.");
        } catch (SQLException e) {
            System.out.println("Error in SQL when adding grid entry");
            System.out.println(e.getMessage());
        }
    }

    public void addRulebookEntry(String name, String content){
        String sql = "INSERT INTO Rulebooks (Name, Content) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, name);
        pstmt.setString(2, content);
        pstmt.executeUpdate();
        System.out.println("Entry added successfully to Rulebooks table.");
        } catch (SQLException e) {
            System.out.println("Error in SQL when adding grid entry");
            System.out.println(e.getMessage());
        }
    }

    public String getEntry(String tableName, String name, String column) {
        String sql = "SELECT * FROM " + tableName + " WHERE Name = ?";
        String result = "";
    
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
    
            // Check if the ResultSet has any rows
            if (rs.next()) {
                result = rs.getString(column);
            } else {
                System.out.println("No entry found with the name '" + name + "' in the table '" + tableName + "'.");
            }
        } catch (SQLException e) {
            System.out.println("Error in SQL when getting entry");
            System.out.println(e.getMessage());
        }
    
        return result;
    }    
}
