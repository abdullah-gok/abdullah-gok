package de.course.management;

public class Student {

    private Integer id;
    private String fullName;
    private String grade;
    private String parentphone;
    private Integer courseid;

    public Student(Integer id, String fullName, String grade, String parentphone, Integer courseid) {
        this.id = id;
        this.fullName = fullName;
        this.grade = grade;
        this.parentphone = parentphone;
        this.courseid = courseid;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getParentphone() {
        return parentphone;
    }

    public void setParentphone(String parentphone) {
        this.parentphone = parentphone;
    }

    public Integer getCourseid() {
        return courseid;
    }

    public void setCourseid(Integer courseid) {
        this.courseid = courseid;
    }

}
