package de.course.management;

import de.course.management.db.Sqlite;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseManager {

//    DataBaseManagerListener listener;

    public DataBaseManager() {
    }

    public void updatestudent(Student student) {
        try ( Connection conn = Sqlite.connect()) {
            Statement stmt = conn.createStatement();
            String sql = "UPDATE student "
                    + "SET fullName='" + student.getFullName() + "', "
                    + "grade='" + student.getGrade() + "', "
                    + "parentphone='" + student.getParentphone() + "',"
                    + "courseid='" + student.getCourseid() + "'"
                    + "WHERE id=" + student.getId();
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(StudentView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addstudent(Student student) {

        try ( Connection conn = Sqlite.connect()) {
            Statement stmt = conn.createStatement();

            String sql = "INSERT INTO student (id,fullName,grade,parentphone, courseid)"
                    + "VALUES('" + student.getId() + "',"
                    + "'" + student.getFullName() + "',"
                    + "'" + student.getGrade() + "',"
                    + "'" + student.getParentphone() + "',"
                    + "'" + student.getCourseid() + "')";

            System.out.println(sql);
            stmt.executeUpdate(sql);
      
//            listener.onAddStudentDatabase();

        } catch (SQLException ex) {
            Logger.getLogger(StudentView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addcourse(Course course) {
        try ( Connection conn = Sqlite.connect()) {
            Statement stmt = conn.createStatement();

            String sql = "INSERT INTO course (courseid, CourseName, CourseStartDate, CourseLevel, TeacherId)"
                    + "VALUES('" + course.getCourseid() + "',"
                    + "'" + course.getCourseName() + "',"
                    + "'" + course.getCourseStartDate() + "',"
                    + "'" + course.getCourseLevel() + "',"
                    + "'" + course.getTeacherId() + "')";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(AddCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updatecourse(Course course) {
        try ( Connection conn = Sqlite.connect()) {
            Statement stmt = conn.createStatement();

            String sql = "UPDATE course "
                    + "SET CourseName='" + course.getCourseName() + "', "
                    + "CourseStartDate='" + course.getCourseStartDate() + "', "
                    + "CourseLevel='" + course.getCourseLevel() + "', "
                    + "TeacherId='" + course.getTeacherId() + "' "
                    + "WHERE courseid=" + course.getCourseid();
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(AddCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addteacher(TeacherView teacher) {
        try ( Connection conn = Sqlite.connect()) {
            Statement stmt = conn.createStatement();

            String sql = "INSERT INTO teacher ( TeacherId, TeacherName, Branch, Birthday, MobileNumber ) "
                    + " VALUES('" + TeacherView.getTeacherId() + "',"
                    + "'" + TeacherView.getTeacherName() + "',"
                    + "'" + TeacherView.getBranch() + "',"
                    + "'" + TeacherView.getBirthday() + "',"
                    + "'" + TeacherView.getMobileNumber() + "')";
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateteacher(TeacherView teacher) {
        try ( Connection conn = Sqlite.connect()) {
            Statement stmt = conn.createStatement();

            String sql = "UPDATE  teacher "
                    + " SET TeacherName ='" + TeacherView.getTeacherName() + "',"
                    + " Branch ='" + TeacherView.getBranch() + "',"
                    + " Birthday ='" + TeacherView.getBirthday() + "',"
                    + " MobileNumber ='" + TeacherView.getMobileNumber() + "'"
                    + "WHERE TeacherId=" + TeacherView.getTeacherId();

            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addadmin(Admin admin) {
        try ( Connection conn = Sqlite.connect()) {
            Statement stmt = conn.createStatement();

            String sql = "INSERT INTO admin (userid, username, password, name, title)"
                    + " VALUES('" + admin.getuserid() + "',"
                    + "'" + admin.getusername() + "',"
                    + "'" + admin.getpassword() + "',"
                    + "'" + admin.getname() + "',"
                    + "'" + admin.gettitle() + "')";

            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateadmin(Admin admin) {
        try ( Connection conn = Sqlite.connect()) {
            Statement stmt = conn.createStatement();

            String sql = "UPDATE admin "
                    + "SET username='" + admin.getusername() + "', "
                    + "password='" + admin.getpassword() + "', "
                    + "name='" + admin.getname() + "', "
                    + "title='" + admin.gettitle() + "' "
                    + "WHERE userid=" + admin.getuserid();
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(AddCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

      
}

//interface DataBaseManagerListener {
//
//    public void onAddStudentDatabase();
//}
