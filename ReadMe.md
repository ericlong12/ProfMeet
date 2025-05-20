# ProfMeet: Faculty Office Hour & Appointment Scheduler

**Version:** 0.8  
**Technologies:** Java, JavaFX, SQLite

ProfMeet is a student-advising scheduling application designed to simplify the process of managing office hours and booking appointments. Built as a desktop application, it allows faculty members to create and manage time slots, courses, and appointments, while students can search, schedule, and edit sessions seamlessly.

---

## Features

- Manage office hours for advisors  
- Add available time slots for appointments  
- Manage courses for both students and advisors  
- Book advising appointments based on available time slots  
- Edit or delete previously scheduled appointments  
- Search office hours by student name  
- Time validation ensures correct scheduling (e.g., From Hour < To Hour)  
- Sort tables by time or date for better visibility  
- Central homepage with navigation to all features  

---

## Team Contributions

**Eric Long**  
- Integrated TimePicker with the database  
- Handled SQL insert logic and time slot sorting in the TableView  

**Hari Sowmith**  
- Connected the "View Time Slots" feature to the homepage  
- Assisted with UI layout and navigation improvements  

**Thao Nguyen**  
- Debugged and fixed time validation logic  
- Tested input scenarios and updated documentation  

**Frances Belleza**  
- Created Office Hours, Time Slots, and Courses pages  
- Implemented form validation, UI alignment, and search/edit functionality  

---

## Technical Highlights

### Polymorphism and Interface Usage

The application uses polymorphism to structure its navigation logic:

```java
public interface Page {
    Scene getScene(Stage stage);
}

public class SearchAndEditOfficeHoursPage implements Page {
    @Override
    public Scene getScene(Stage stage) {
        // implementation
    }
}
```

### Data Storage and Management
Data is stored using SQLite

All records (office hours, time slots, appointments, courses) are persistent across sessions

Time slots and appointments are sorted automatically in the display tables

### How to Use
Launch the application to access the homepage.

### Use the homepage buttons to:

Add Office Hours

Add Time Slots

Add Course

Add Appointment

View Time Slots

View Semester Office Hours

View Courses

View Appointments

Search and Edit Office Hours

### To schedule an appointment:

Advisors create available time slots

Students select a slot to book an advising session

### To edit/delete:

Use the "Search and Edit Office Hours" page to search by student name and modify existing entries

![image](https://github.com/user-attachments/assets/891dade4-873b-4105-b296-bc99dbc57e72)

