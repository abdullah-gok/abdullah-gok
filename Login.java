package de.course.management;

import de.course.management.db.Sqlite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.border.LineBorder;

public class Login {

    public JFrame init() {

        JFrame content = new JFrame("Login");

        JTextField tfUsername = new JTextField(20);
        tfUsername.setBackground(Color.white);
        tfUsername.setText("");

        JPasswordField tfPassword = new JPasswordField(20);
        tfPassword.setBackground(Color.white);

        JLabel userLabel = new JLabel("USERNAME");

        JLabel passwordLabel = new JLabel("PASSWORD");

        JCheckBox showPassword = new JCheckBox("Show Password");

        tfUsername.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                if ("Enter Username".equals(tfUsername.getText())) {
                    tfUsername.setText("");
                }
            }
            @Override
            public void mousePressed(MouseEvent arg0) {
            }

            @Override
            public void mouseReleased(MouseEvent arg0) {
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
            }
            @Override
            public void mouseExited(MouseEvent arg0) {
            }
        });
        tfUsername.addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent arg0) {
                if ("".equals(tfUsername.getText())) {
                    tfUsername.setText("Enter Username");
                }
            }

            @Override
            public void focusGained(FocusEvent arg0) {

            }
        });
        tfPassword.setText("Enter Password");
        tfPassword.setEchoChar('*');      
        tfPassword.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent arg0) {
                if ("Enter Password".equals(tfPassword.getText())) {
                    tfPassword.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent arg0) {
                if ("".equals(tfPassword.getText())) {
                    tfPassword.setText("Enter Password");
                }
            }
        });
        JButton loginbutton = new JButton("Login");
        loginbutton.setBackground(Color.green);
                     
        showPassword.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {

                      if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                tfPassword.setEchoChar((char) 0);
            } else {
                tfPassword.setEchoChar('*');
            } 
         }}});
                    
        JLabel label = new JLabel();
        label.setText("Welcome to Course Managemet System");
        label.setFont(new Font(label.getName(), Font.BOLD,17));
        label.setForeground(Color.blue);
        JButton cancelbutton = new JButton("Cancel");
        cancelbutton.setBackground(Color.green);
        content.add(label);
        content.add(userLabel);
        content.add(tfUsername);
        content.add(passwordLabel);
        content.add(tfPassword);
        content.add(showPassword);
        content.add(loginbutton);
        content.add(cancelbutton);

        cancelbutton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent arg0) {
                cancelbutton.setBackground(Color.red);
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                cancelbutton.setBackground(Color.green);
            }
        });

        cancelbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                System.out.println(e.getActionCommand());
                System.exit(0);
            }
        });
        loginbutton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent arg0) {
                loginbutton.setBackground(Color.red);
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                loginbutton.setBackground(Color.green);
            }
        }
        );
        loginbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean isloggedin = login(tfUsername.getText(), tfPassword.getText());

                if (isloggedin) {
                    content.setVisible(false);
                    JFrame menu = Menu.init();
                    menu.setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(null, " Username or Userpassword is wrong");
                }
            }
        });
        return content;
    }
    public boolean login(String username, String password) {
        try ( Connection conn = Sqlite.connect()) {
            Statement stmt = conn.createStatement();
            ResultSet rs1 = stmt.executeQuery("SELECT * FROM admin WHERE username='" + username + "' AND password='" + password + "'");

            if (rs1.next() == false) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
