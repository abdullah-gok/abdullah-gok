package de.course.management;

public class Course {

    private int courseid;
    String CourseName,  CourseStartDate, CourseLevel, TeacherId;

    public Course(int courseid, String CourseName, String CourseStartDate, String CourseLevel,  String CourseTeacher) {
        this.courseid = courseid;
        this.CourseName = CourseName;        
        this.CourseStartDate = CourseStartDate;
        this.CourseLevel = CourseLevel;
        this.TeacherId = CourseTeacher;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String CourseName) {
        this.CourseName = CourseName;
    }

    public String getCourseStartDate() {
        return CourseStartDate;
    }

    public void setCourseStartDate(String CourseStartDate) {
        this.CourseStartDate = CourseStartDate;
    }

    public String getCourseLevel() {
        return CourseLevel;
    }

    public void setCourseLevel(String CourseLevel) {
        this.CourseLevel = CourseLevel;
    }

    public String getTeacherId() {
        return TeacherId;
    }

    public void setTeacherId(String TeacherId) {
        this.TeacherId = TeacherId;
    }

}