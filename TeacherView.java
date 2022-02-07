package de.course.management;

public class TeacherView {

    private static int TeacherId;
    private static String TeacherName, Branch, Birthday, MobileNumber;

    public TeacherView(int TeacherId, String TeacherName, String Branch, String Birthday, String MobileNumber) {

        this.TeacherId = TeacherId;
        this.TeacherName = TeacherName;
        this.Branch = Branch;
        this.Birthday = Birthday;
        this.MobileNumber = MobileNumber;
    }

    public static int getTeacherId() {
        return TeacherId;
    }

    public void setTeacherId(int TeacherId) {
        this.TeacherId = TeacherId;
    }

    public static String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String TeacherName) {
        this.TeacherName = TeacherName;
    }

    public static String getBranch() {
        return Branch;
    }

    public void setBranch(String Branch) {
        this.Branch = Branch;
    }

    public static String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String Birthday) {
        this.Birthday = Birthday;
    }

    public static String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String MobileNumber) {
        this.MobileNumber = MobileNumber;
    }

}
