/* This is Data Handling File
*   Written by Hari Sowmith Reddy
*
*
*/
package s25cs151.application;

import java.sql.*;

    public class OfficeHoursDataHandling {
        private static final String DB_URL = "jdbc:sqlite:faculty_office_hours.db";

        public OfficeHoursDataHandling() {
            createTables();
        }

        private void createTables() {
            try (Connection conn = DriverManager.getConnection(DB_URL);
                 Statement stmt = conn.createStatement()) {

                // Create Courses Table
                String createCoursesTable = "CREATE TABLE IF NOT EXISTS courses (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "code TEXT NOT NULL, " +
                        "name TEXT NOT NULL, " +
                        "section TEXT NOT NULL)";

                // Create Office Hours Table
                String createOfficeHoursTable = "CREATE TABLE IF NOT EXISTS office_hours (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "semester TEXT NOT NULL, " +
                        "year INTEGER NOT NULL, " +
                        "days TEXT NOT NULL)";

                // Create Time Slots Table
                String createTimeSlotsTable = "CREATE TABLE IF NOT EXISTS time_slots (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "from_time TEXT NOT NULL, " +
                        "to_time TEXT NOT NULL)";

                stmt.execute(createCoursesTable);
                stmt.execute(createOfficeHoursTable);
                stmt.execute(createTimeSlotsTable);

                System.out.println("Tables created successfully (if not exist).");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public Connection connect() {
            try {
                return DriverManager.getConnection(DB_URL);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        // Add Course to the Database
        public void addCourse(String code, String name, String section) {
            String sql = "INSERT INTO courses (code, name, section) VALUES (?, ?, ?)";

            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, code);
                pstmt.setString(2, name);
                pstmt.setString(3, section);
                pstmt.executeUpdate();
                System.out.println("Course added: " + code);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //  Add Office Hours to the Database
        public void addOfficeHours(String semester, int year, String days) {
            String sql = "INSERT INTO office_hours (semester, year, days) VALUES (?, ?, ?)";

            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, semester);
                pstmt.setInt(2, year);
                pstmt.setString(3, days);
                pstmt.executeUpdate();
                System.out.println("Office hours added for " + semester + " " + year);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Add Time Slots to the Database
        public void addTimeSlot(String fromTime, String toTime) {
            String sql = "INSERT INTO time_slots (from_time, to_time) VALUES (?, ?)";

            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, fromTime);
                pstmt.setString(2, toTime);
                pstmt.executeUpdate();
                System.out.println("Time slot added: " + fromTime + " - " + toTime);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


