package de.course.management;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Menu {

    public static JFrame init() {

        JFrame content = new JFrame();
        content.setTitle("Cambridge Course Management");

        JTabbedPane tabpane = new JTabbedPane(JTabbedPane.LEFT);
        StudentView student = new StudentView();
        JPanel studentTab = student.init();
        AddCourse cources = new AddCourse();
        JPanel courseTab = cources.init();
        Teacher teacher = new Teacher();
        JPanel teacherTab = teacher.init();
        AdminTool admin = new AdminTool();
        JPanel adminTab = admin.init();

        tabpane.addTab("Students", studentTab);
        tabpane.addTab("Courses", courseTab);
        tabpane.addTab("Teachers", teacherTab);
        tabpane.addTab("Admin Tools", adminTab);
        tabpane.setFont(tabpane.getFont().deriveFont(Font.BOLD, 22.0f));
        tabpane.setForeground(Color.blue);
        tabpane.setSize(600, 600);
        content.setSize(1000, 600);
        content.setLocation(300, 200);
        content.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        content.setVisible(false);
        content.add(tabpane);

        return content;

    }

}
