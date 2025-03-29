package s25.cs151.application;

import java.io.File;
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
                + "courseSection TEXT,"
                + "UNIQUE(semester, year, days, timeSlots, courseCode, courseName, courseSection)"
                + ");";
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(createTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    public static boolean insertSemester(String semester, int year, String days, String timeSlots, String courseCode, String courseName, String courseSection) {
        String insertSQL = "INSERT OR IGNORE INTO semester_office_hours (semester, year, days, timeSlots, courseCode, courseName, courseSection) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, semester);
            pstmt.setInt(2, year);
            pstmt.setString(3, days);
            pstmt.setString(4, timeSlots);
            pstmt.setString(5, courseCode);
            pstmt.setString(6, courseName);
            pstmt.setString(7, courseSection);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }


    /* -------------------------------   I NEEDED FOR DEBUGGING (-frances) ------------------------------------- */

//    public static boolean insertSemester(String semester, int year, String days, String timeSlots,
//                                         String courseCode, String courseName, String courseSection) {
//        System.out.println("Attempting to insert into database...");
//        System.out.println("Semester: " + semester);
//        System.out.println("Year: " + year);
//        System.out.println("Days: " + days);
//        System.out.println("TimeSlots: " + timeSlots);
//        System.out.println("Course Code: " + courseCode);
//        System.out.println("Course Name: " + courseName);
//        System.out.println("Course Section: " + courseSection);
//
//        String insertSQL = "INSERT OR IGNORE INTO semester_office_hours (semester, year, days, timeSlots, courseCode, courseName, courseSection) VALUES (?, ?, ?, ?, ?, ?, ?)";
//
//        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
//            pstmt.setString(1, semester);
//            pstmt.setInt(2, year);
//            pstmt.setString(3, days);
//            pstmt.setString(4, timeSlots);
//            pstmt.setString(5, courseCode);
//            pstmt.setString(6, courseName);
//            pstmt.setString(7, courseSection);
//
//            int rowsInserted = pstmt.executeUpdate();
//            System.out.println("Rows inserted: " + rowsInserted);
//            return rowsInserted > 0;
//        } catch (SQLException e) {
//            System.out.println("SQL ERROR: " + e.getMessage());
//            e.printStackTrace();
//            return false;
//        }
//    }

    /* -------------------------------   I NEEDED FOR DEBUGGING (-frances) ------------------------------------- */
//
//    private static Connection connect() throws SQLException {
//        try {
//            Class.forName("org.sqlite.JDBC"); // Force load SQLite driver
//        } catch (ClassNotFoundException e) {
//            System.out.println("--> ERROR: SQLite JDBC Driver not found.");
//            e.printStackTrace();
//            throw new SQLException("Driver not found");
//        }
//
//        // Get and print the expected database path
//        String dbPath = System.getProperty("user.dir") + "/office_hours.db";
//        System.out.println("🔍 Java is looking for database at: " + dbPath);
//
//        File dbFile = new File(dbPath);
//        if (!dbFile.exists()) {
//            System.out.println("--> ERROR: Database file NOT found at: " + dbPath);
//            throw new SQLException("Database file not found");
//        } else {
//            System.out.println("--> Database file found at: " + dbPath);
//        }
//
//        return DriverManager.getConnection("jdbc:sqlite:" + dbPath);
//    }


}