#Name of Application: ProfMeet

#Version: 0.5

#Acknowledgements:

  Hari Sowmith: Connected the new "View Time Slots" feature to the homepage, assisted with UI layout improvements
  Thao Nguyen: Debugged and fixed time validation logic (From Hour < To Hour), tested input scenarios, updated README
  Eric Long: Built and connected the TimePicker component to the database, handled SQL insert logic and Time Slot sorting in the TableView
  Frances Belleza: Created and validated Office Hours, Time Slots, and Courses pages, implemented form validation and UI alignment


#Description:

ProfMeet is a student advising appointment system designed to simplify the scheduling process for students and advisors. The app allows students to book appointments during available office hours, and advisors can manage their office hours, time slots, and courses. 

#This application supports the following features:
 Manage office hours for advisors.
 Add available time slots for appointments.
 Manage courses for both students and advisors.
 Book advising appointments based on available time slots.

#Features:

Homepage: Displays the current semester and year with interactive buttons for various functions.
Add Office Hours: Allows advisors to add and manage their office hours.
Add Time Slots: Let advisors set specific available times for advising appointments.
Add Course: Enables instructors to add courses for students to select when booking appointments.
Add Appointment: Students can book advising sessions based on available office hours and time slots.

How to Use:
1. Homepage: When you first open the application, the homepage will display the current semester and year. It provides the following buttons:
    Add Office Hours: This is for advisors to manage their available office hours.
    Add Time Slots: For advisors to set specific time slots for appointments.
    Add Course: For instructors to add courses for students to book advising appointments.
    Add Appointment: For students to book an appointment with an advisor during available office hours.
    View Time Slots: Opens a table that displays all stored time slots sorted by starting time.

2. Office Hours Page: Advisors can set their office hours by clicking the "Add Office Hours" button. The available office hours are then displayed for students to view and book.

3. Booking Appointments: Students can view the available time slots based on the office hours set by the advisors and select a time that works best for them.

4. Time Slot Validation: The app checks to make sure the "To Hour" is later than the "From Hour". If not, it shows an error message and blocks the entry.

5. Table Sorting: Saved time slots are displayed in a table and are automatically sorted in ascending order by the start time (e.g., 8:00 AM before 10:30 AM).
