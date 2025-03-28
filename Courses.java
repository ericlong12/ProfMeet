package s25.cs151.application;

import javafx.beans.property.SimpleStringProperty;

public class Courses {
    private final SimpleStringProperty courseCode;
    private final SimpleStringProperty courseName;
    private final SimpleStringProperty courseSection;

    public Courses(String courseCode, String courseName, String courseSection) {
        this.courseCode = new SimpleStringProperty(courseCode);
        this.courseName = new SimpleStringProperty(courseName);
        this.courseSection = new SimpleStringProperty(courseSection);
    }

    public String getCourseCode() { return courseCode.get(); }
    public void setCourseCode(String value) { courseCode.set(value); }

    public String getCourseName() { return courseName.get(); }
    public void setCourseName(String value) { courseName.set(value); }

    public String getCourseSection() {
        return courseSection.get();
    }
    public void setCourseSection(String value) {
        courseSection.set(value);
    }
}