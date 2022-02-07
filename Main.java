package de.course.management;

import java.awt.Container;
import java.awt.FlowLayout;
import java.sql.SQLException;
import javax.swing.JFrame;

public class Main {

    public static Container loginContainer = new Login().init();
    public static Container menuContainer = new Menu().init();

    public static void changeToLogin() {
        loginContainer.setVisible(false);
        menuContainer.setVisible(true);
    }

    public static void main(String[] args) throws SQLException {
        JFrame loginFrame = new Login().init();
        loginFrame.setTitle("Cambridge Course Management");
        loginFrame.setVisible(true);
        loginFrame.setSize(380, 260);
        loginFrame.setLocation(300, 200);
        loginFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setVisible(true);
        loginFrame.setResizable(false);
    }
}
