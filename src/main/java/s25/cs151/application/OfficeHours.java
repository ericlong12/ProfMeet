package s25.cs151.application;

import javafx.beans.property.SimpleStringProperty;

public class OfficeHours {
    private final SimpleStringProperty semester;
    private final SimpleStringProperty year;
    private final SimpleStringProperty days;
    private final SimpleStringProperty timeSlots;

    //removed course info & added it to its own class -FB

    public OfficeHours(String semester, String year, String days, String timeSlots) {
        this.semester = new SimpleStringProperty(semester);
        this.year = new SimpleStringProperty(year);
        this.days = new SimpleStringProperty(days);
        this.timeSlots = new SimpleStringProperty(timeSlots);
    }

    public String getSemester() { return semester.get(); }
    public void setSemester(String value) { semester.set(value); }

    public String getYear() { return year.get(); }
    public void setYear(String value) { year.set(value); }

    public String getDays() { return days.get(); }
    public void setDays(String value) { days.set(value); }

    public String getTimeSlots() { return timeSlots.get(); }
    public void setTimeSlots(String value) { timeSlots.set(value); }

}