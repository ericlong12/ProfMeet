package s25.cs151.application;

import java.sql.*;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:office_hours.db";

    public static void initializeDatabase() {
        String createTable = "CREATE TABLE IF NOT EXISTS semester_office_hours ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "semester TEXT NOT NULL, "
                + "year INTEGER NOT NULL, "
                + "days TEXT NOT NULL, "
                + "timeSlots TEXT, "
                + "courseCode TEXT, "
                + "courseName TEXT, "
                + "UNIQUE(semester, year, days, timeSlots, courseCode, courseName)"
                + ");";
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(createTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    

    public static boolean insertSemester(String semester, int year, String days, String timeSlots, String courseCode, String courseName) {
        String insertSQL = "INSERT OR IGNORE INTO semester_office_hours (semester, year, days, timeSlots, courseCode, courseName) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, semester);
            pstmt.setInt(2, year);
            pstmt.setString(3, days);
            pstmt.setString(4, timeSlots);
            pstmt.setString(5, courseCode);
            pstmt.setString(6, courseName);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}
