package s25.cs151.application;

import java.io.File;
import java.sql.*;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:office_hours.db";
    public static void initializeDatabase() {
        // SQL for creating the office_hours table
        String createOfficeHoursTable = "CREATE TABLE IF NOT EXISTS office_hours ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "semester TEXT NOT NULL, "
                + "year INTEGER NOT NULL, "
                + "days TEXT NOT NULL"
                + ");";

        // SQL for creating the time_slots table; each time slot is linked to an office hour session
        String createTimeSlotsTable = "CREATE TABLE IF NOT EXISTS time_slots ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "office_hour_id INTEGER NOT NULL, "
                + "timeSlot TEXT NOT NULL, "
                + "UNIQUE (office_hour_id, timeSlot), "
                + "FOREIGN KEY (office_hour_id) REFERENCES office_hours(id)"
                + ");";

        // SQL for creating the courses table; optionally link courses to an office hour session
        String createCoursesTable = "CREATE TABLE IF NOT EXISTS courses ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "office_hour_id INTEGER, "    // Optional: remove if not linking courses to office hours
                + "courseCode TEXT NOT NULL, "
                + "courseName TEXT NOT NULL, "
                + "courseSection TEXT NOT NULL, "
                + "UNIQUE (courseCode, courseName, courseSection), "
                + "FOREIGN KEY (office_hour_id) REFERENCES office_hours(id)"
                + ");";

        // Execute all three statements on a single connection
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(createOfficeHoursTable);
            stmt.execute(createTimeSlotsTable);
            stmt.execute(createCoursesTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int insertOfficeHours(String semester, int year, String days) {
        String sql = "INSERT INTO office_hours (semester, year, days) VALUES (?, ?, ?)";
        System.out.println("[DEBUG] Inserting Office Hours: " + semester + ", " + year + ", " + days);
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, semester);
            pstmt.setInt(2, year);
            pstmt.setString(3, days);

            int rowsInserted = pstmt.executeUpdate();
            System.out.println("[DEBUG] rowsInserted: " + rowsInserted);
            if (rowsInserted > 0) {
                // Attempt to use getGeneratedKeys() first:
                try {
                    try (ResultSet rs = pstmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            int id = rs.getInt(1);
                            System.out.println("[DEBUG] Generated id via getGeneratedKeys: " + id);
                            return id;
                        }
                    }
                } catch (SQLFeatureNotSupportedException e) {
                    System.out.println("[DEBUG] getGeneratedKeys() not supported, using last_insert_rowid()");
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid() AS id")) {
                        if (rs.next()) {
                            int id = rs.getInt("id");
                            System.out.println("[DEBUG] Generated id via last_insert_rowid(): " + id);
                            return id;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("[DEBUG] SQL Exception: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }

    public static boolean insertTimeSlot(int officeHourId, String timeSlot) {
        String sql = "INSERT OR IGNORE INTO time_slots (office_hour_id, timeSlot) VALUES (?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, officeHourId);
            pstmt.setString(2, timeSlot);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean insertCourse(int officeHourId, String courseCode, String courseName, String courseSection) {
        String sql = "INSERT OR IGNORE INTO courses (office_hour_id, courseCode, courseName, courseSection) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, officeHourId);
            pstmt.setString(2, courseCode);
            pstmt.setString(3, courseName);
            pstmt.setString(4, courseSection);

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