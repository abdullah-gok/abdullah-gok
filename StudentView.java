package de.course.management;

import de.course.management.db.Sqlite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

public class StudentView {

    DataBaseManager databasemanager = new DataBaseManager();
    JPanel mainPanel = new JPanel();
    JPanel controlPanel = new JPanel();
    JTable table;
    String[][] data;
    String[] col = new String[]{
        "ID Number",
        "Full Name",
        "Grade",
        "Parent Phone Number",
        "Course Id"};

    public JPanel init() {
        DataBaseManager databasemanager = new DataBaseManager();
        BorderLayout layout = new BorderLayout();
        layout.setHgap(10);
        layout.setVgap(10);

        mainPanel.setLayout(layout);
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton addNewStudent = new JButton("Add New Student");
        JButton updateStudent = new JButton("Update Student");
        JButton deleteStudent = new JButton("Delete Student");

        controlPanel.add(addNewStudent);
        controlPanel.add(updateStudent);
        controlPanel.add(deleteStudent);

        loadstudents();

        addNewStudent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JDialog addNewStudentDialog = new JDialog();
                addNewStudentDialog.setTitle("Add New Teacher");
                addNewStudentDialog.pack();
                addNewStudentDialog.setVisible(true);
                addNewStudentDialog.toFront();
                addNewStudentDialog.setSize(460, 500);
                addNewStudentDialog.setResizable(false);
                addNewStudentDialog.setLocation(400, 300);
                addNewStudentDialog.setLayout(new FlowLayout(FlowLayout.LEFT));
                addNewStudentDialog.setModal(true);
                addNewStudentDialog.setAlwaysOnTop(true);

                TextField studentIdTextField = new TextField("Enter Student ID Number");
                studentIdTextField.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        char c = e.getKeyChar();
                        if (!((c >= '0') && (c <= '9')
                                || (c == KeyEvent.VK_BACK_SPACE)
                                || (c == KeyEvent.VK_DELETE))) {
                            e.consume();
                        }
                    }
                });
                studentIdTextField.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        if (studentIdTextField.getText().length() >= 3) {
                            e.consume();
                        }
                    }
                });
                TextField fullNameTextField = new TextField("Enter Full Name of Student");
                fullNameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
                    public void keyTyped(java.awt.event.KeyEvent evt) {
                        if (!(Character.isLetter(evt.getKeyChar()))) {
                            evt.consume();
                        }
                    }
                });
                TextField parentPhoneTextField = new TextField("Enter Parent Phone Number");
                parentPhoneTextField.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        char c = e.getKeyChar();
                        if (!((c >= '0') && (c <= '9')
                                || (c == KeyEvent.VK_BACK_SPACE)
                                || (c == KeyEvent.VK_DELETE))) {
                            e.consume();
                        }
                    }
                });

                final JComboBox cbCourseName = new JComboBox();
                cbCourseName.setPreferredSize(new Dimension(430, 40));
                cbCourseName.setBackground(Color.white);

                final JComboBox cbCourseLevel = new JComboBox();
                cbCourseLevel.setPreferredSize(new Dimension(430, 40));
                cbCourseLevel.setBackground(Color.white);

                final JComboBox cbGrade = new JComboBox();
                cbGrade.setPreferredSize(new Dimension(430, 40));
                cbGrade.setBackground(Color.white);

                try ( Connection conn = Sqlite.connect()) {
                    String sql = "select DISTINCT grade  from student";
                    System.out.println(conn);
                    PreparedStatement pst = conn.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery();
                    System.out.println(sql);
                    while (rs.next()) {
                        cbGrade.addItem(rs.getString("grade"));
                    }
                } catch (Exception em) {
                    em.printStackTrace();
                }

                try ( Connection conn = Sqlite.connect()) {
                    String sql = "select DISTINCT CourseName  from course";
                    System.out.println(conn);
                    PreparedStatement pst = conn.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery();
                    System.out.println(sql);
                    while (rs.next()) {
                        cbCourseName.addItem(rs.getString("CourseName"));
                    }
                } catch (Exception en) {
                    en.printStackTrace();
                }

                cbCourseName.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent itemEvent) {
                        if (itemEvent.getStateChange() == 1) {
                            String courseName = itemEvent.getItem().toString();
                            System.out.println("--");
                            System.out.println(courseName);
                            System.out.println("--");
                            try ( Connection conn = Sqlite.connect()) {
                                System.out.println("alles funktioniert");
                                String sql = "select DISTINCT CourseLevel from course  WHERE CourseName = '" + courseName + "'";
                                System.out.println(conn);
                                PreparedStatement pst = conn.prepareStatement(sql);
                                ResultSet rs = pst.executeQuery();
                                System.out.println(sql);
                                cbCourseLevel.removeAllItems();
                                while (rs.next()) {
                                    cbCourseLevel.addItem(rs.getString("CourseLevel"));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });

                JLabel studentIdLabel = new JLabel("Enter Student ID Number");
                JLabel fullNameLabel = new JLabel("Enter Full Name of Student");
                JLabel studentGradeLabel = new JLabel("Select the Grade of Student");
                JLabel parentPhoneLabel = new JLabel("Enter the Parent Mobile Number");
                JLabel courseNameLabel = new JLabel("Select the Course From the List");
                JLabel courseGradeLabel = new JLabel("Select the Course Grade From the List");

                JButton saveButton = new JButton("Save  ");
                saveButton.setBackground(Color.LIGHT_GRAY);
                saveButton.setFont(saveButton.getFont().deriveFont(20.0f));
                saveButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (studentIdTextField.getText().trim().isEmpty()) {
                            studentIdTextField.setBorder(new LineBorder(Color.red, 2));
                            saveButton.setBackground(Color.red);
                            studentIdTextField.addKeyListener(new KeyAdapter() {
                                public void keyPressed(KeyEvent ke) {
                                    if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535)) {
                                        studentIdTextField.setBorder(new LineBorder(Color.green, 2));
                                        saveButton.setBackground(Color.LIGHT_GRAY);
                                    }
                                }
                            });
                        }

                        if (fullNameTextField.getText().equals("")) {
                            fullNameTextField.setBorder(new LineBorder(Color.red, 2));
                            saveButton.setBackground(Color.red);
                            fullNameTextField.addKeyListener(new KeyAdapter() {
                                public void keyPressed(KeyEvent ke) {
                                    if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535)) {
                                        fullNameTextField.setBorder(new LineBorder(Color.green, 2));
                                        saveButton.setBackground(Color.LIGHT_GRAY);
                                    }
                                }
                            });
                        }

                        if (parentPhoneTextField.getText().equals("")) {
                            parentPhoneTextField.setBorder(new LineBorder(Color.red, 2));
                            saveButton.setBackground(Color.red);
                            parentPhoneTextField.addKeyListener(new KeyAdapter() {
                                public void keyPressed(KeyEvent ke) {
                                    if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535)) {
                                        parentPhoneTextField.setBorder(new LineBorder(Color.green, 2));
                                        saveButton.setBackground(Color.LIGHT_GRAY);
                                    }
                                }
                            });
                        } else {

                            String grade = cbGrade.getSelectedItem().toString();
                            String courseName = cbCourseName.getSelectedItem().toString();
                            String courseLevel = cbCourseLevel.getSelectedItem().toString();

                            Integer courseId = null;
                            try ( Connection conn = Sqlite.connect()) {
                                String sql = "select courseid from course  WHERE CourseName = '" + courseName + "' and CourseLevel= '" + courseLevel + "'";
                                PreparedStatement pst = conn.prepareStatement(sql);
                                ResultSet rs = pst.executeQuery();
                                while (rs.next()) {
                                    courseId = rs.getInt("courseid");
                                    break;
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                            Student student = new Student(
                                    Integer.parseInt(studentIdTextField.getText()),
                                    fullNameTextField.getText(),
                                    grade,
                                    parentPhoneTextField.getText(),
                                    courseId);

                            databasemanager.addstudent(student);
                            addNewStudentDialog.setVisible(false);
                            loadstudents();
                        }
                    }
                });

                JButton cancelButton = new JButton("Cancel");
                cancelButton.setBackground(Color.LIGHT_GRAY);
                cancelButton.setFont(saveButton.getFont().deriveFont(20.0f));
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        addNewStudentDialog.setVisible(false);
                        JFrame menu = Menu.init();
                        menu.setVisible(false);
                    }
                });

                addNewStudentDialog.add(studentIdLabel);
                addNewStudentDialog.add(studentIdTextField);

                addNewStudentDialog.add(fullNameLabel);
                addNewStudentDialog.add(fullNameTextField);

                addNewStudentDialog.add(studentGradeLabel);
                addNewStudentDialog.add(cbGrade);

                addNewStudentDialog.add(parentPhoneLabel);
                addNewStudentDialog.add(parentPhoneTextField);

                addNewStudentDialog.add(courseNameLabel);
                addNewStudentDialog.add(cbCourseName);

                addNewStudentDialog.add(courseGradeLabel);
                addNewStudentDialog.add(cbCourseLevel);

                addNewStudentDialog.add(saveButton);
                addNewStudentDialog.add(cancelButton);

            }
        });

        updateStudent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int selectedRow = table.getSelectedRow();
                Student student = new Student(Integer.parseInt(data[selectedRow][0]), data[selectedRow][1],
                        data[selectedRow][2], data[selectedRow][3], Integer.parseInt(data[selectedRow][4]));

                JDialog updateStudentDialog = new JDialog();
                updateStudentDialog.setTitle("Add New Teacher");
                updateStudentDialog.pack();
                updateStudentDialog.setVisible(true);
                updateStudentDialog.toFront();
                updateStudentDialog.setSize(460, 500);
                updateStudentDialog.setResizable(false);
                updateStudentDialog.setLocation(400, 300);
                updateStudentDialog.setLayout(new FlowLayout(FlowLayout.LEFT));
                updateStudentDialog.setModal(true);
                updateStudentDialog.setAlwaysOnTop(true);

                TextField studentIdTextField = new TextField("Enter Student ID Number");
                studentIdTextField.setText(String.valueOf(student.getId()));
                studentIdTextField.setEditable(false);
                studentIdTextField.setBackground(Color.white);
                TextField fullNameTextField = new TextField("Enter Full Name of Student");
                fullNameTextField.setText(student.getFullName());
                TextField parentPhoneTextField = new TextField("Enter Parent Phone Number");
                parentPhoneTextField.setText(student.getParentphone());

                final JComboBox cbCourseName = new JComboBox();
                cbCourseName.setPreferredSize(new Dimension(430, 40));
                cbCourseName.setBackground(Color.white);

                final JComboBox cbCourseLevel = new JComboBox();
                cbCourseLevel.setPreferredSize(new Dimension(430, 40));
                cbCourseLevel.setBackground(Color.white);

                final JComboBox cbGrade = new JComboBox();
                cbGrade.setPreferredSize(new Dimension(430, 40));
                cbGrade.setBackground(Color.white);

                try ( Connection conn = Sqlite.connect()) {
                    String sql = "select DISTINCT grade  from student";
                    System.out.println(conn);
                    PreparedStatement pst = conn.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery();
                    System.out.println(sql);
                    while (rs.next()) {
                        cbGrade.addItem(rs.getString("grade"));
                    }
                } catch (Exception em) {
                    em.printStackTrace();
                }

                try ( Connection conn = Sqlite.connect()) {
                    String sql = "select DISTINCT CourseName  from course";
                    System.out.println(conn);
                    PreparedStatement pst = conn.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery();
                    System.out.println(sql);
                    while (rs.next()) {
                        cbCourseName.addItem(rs.getString("CourseName"));
                    }
                } catch (Exception en) {
                    en.printStackTrace();
                }

                cbCourseName.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent itemEvent) {
                        if (itemEvent.getStateChange() == 1) {
                            String courseName = itemEvent.getItem().toString();
                            System.out.println("--");
                            System.out.println(courseName);
                            System.out.println("--");
                            try ( Connection conn = Sqlite.connect()) {
                                System.out.println("alles funktioniert");
                                String sql = "select DISTINCT CourseLevel from course  WHERE CourseName = '" + courseName + "'";
                                System.out.println(conn);
                                PreparedStatement pst = conn.prepareStatement(sql);
                                ResultSet rs = pst.executeQuery();
                                System.out.println(sql);
                                cbCourseLevel.removeAllItems();
                                while (rs.next()) {
                                    cbCourseLevel.addItem(rs.getString("CourseLevel"));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });

                JLabel studentIdLabel = new JLabel("Enter Student ID Number");
                JLabel fullNameLabel = new JLabel("Enter Full Name of Student");
                JLabel studentGradeLabel = new JLabel("Select the Grade of Student");
                JLabel parentPhoneLabel = new JLabel("Enter the Parent Mobile Number");
                JLabel courseNameLabel = new JLabel("Select the Course From the List");
                JLabel courseGradeLabel = new JLabel("Select the Course Grade From the List");

                JButton saveButton = new JButton("Save  ");
                saveButton.setBackground(Color.LIGHT_GRAY);
                saveButton.setFont(saveButton.getFont().deriveFont(20.0f));
                saveButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (studentIdTextField.getText().trim().isEmpty()) {
                            studentIdTextField.setBorder(new LineBorder(Color.red, 2));
                            saveButton.setBackground(Color.red);
                            studentIdTextField.addKeyListener(new KeyAdapter() {
                                public void keyPressed(KeyEvent ke) {
                                    if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535)) {
                                        studentIdTextField.setBorder(new LineBorder(Color.green, 2));
                                        saveButton.setBackground(Color.LIGHT_GRAY);
                                    }
                                }
                            });
                        }

                        if (fullNameTextField.getText().equals("")) {
                            fullNameTextField.setBorder(new LineBorder(Color.red, 2));
                            saveButton.setBackground(Color.red);
                            fullNameTextField.addKeyListener(new KeyAdapter() {
                                public void keyPressed(KeyEvent ke) {
                                    if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535)) {
                                        fullNameTextField.setBorder(new LineBorder(Color.green, 2));
                                        saveButton.setBackground(Color.LIGHT_GRAY);
                                    }
                                }
                            });
                        }

                        if (parentPhoneTextField.getText().equals("")) {
                            parentPhoneTextField.setBorder(new LineBorder(Color.red, 2));
                            saveButton.setBackground(Color.red);
                            parentPhoneTextField.addKeyListener(new KeyAdapter() {
                                public void keyPressed(KeyEvent ke) {
                                    if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535)) {
                                        parentPhoneTextField.setBorder(new LineBorder(Color.green, 2));
                                        saveButton.setBackground(Color.LIGHT_GRAY);
                                    }
                                }
                            });
                        } else {

                            String grade = cbGrade.getSelectedItem().toString();
                            String courseName = cbCourseName.getSelectedItem().toString();
                            String courseLevel = cbCourseLevel.getSelectedItem().toString();

                            Integer courseId = null;
                            try ( Connection conn = Sqlite.connect()) {
                                String sql = "select courseid from course  WHERE CourseName = '" + courseName + "' and CourseLevel= '" + courseLevel + "'";
                                PreparedStatement pst = conn.prepareStatement(sql);
                                ResultSet rs = pst.executeQuery();
                                while (rs.next()) {
                                    courseId = rs.getInt("courseid");
                                    break;
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                            Student student = new Student(
                                    Integer.parseInt(studentIdTextField.getText()),
                                    fullNameTextField.getText(),
                                    grade,
                                    parentPhoneTextField.getText(),
                                    courseId);

                            databasemanager.updatestudent(student);
                            updateStudentDialog.setVisible(false);;

                        }
                    }
                });

                JButton cancelButton = new JButton("Cancel");
                cancelButton.setBackground(Color.LIGHT_GRAY);
                cancelButton.setFont(saveButton.getFont().deriveFont(20.0f));
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        updateStudentDialog.setVisible(false);
                        JFrame menu = Menu.init();
                        menu.setVisible(false);
                    }
                });

                updateStudentDialog.add(studentIdLabel);
                updateStudentDialog.add(studentIdTextField);

                updateStudentDialog.add(fullNameLabel);
                updateStudentDialog.add(fullNameTextField);

                updateStudentDialog.add(studentGradeLabel);
                updateStudentDialog.add(cbGrade);

                updateStudentDialog.add(parentPhoneLabel);
                updateStudentDialog.add(parentPhoneTextField);

                updateStudentDialog.add(courseNameLabel);
                updateStudentDialog.add(cbCourseName);

                updateStudentDialog.add(courseGradeLabel);
                updateStudentDialog.add(cbCourseLevel);

                updateStudentDialog.add(saveButton);
                updateStudentDialog.add(cancelButton);

            }
        });

        deleteStudent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                try ( Connection conn = Sqlite.connect()) {
                    String querry = "delete from student where id=" + data[selectedRow][0];
                    PreparedStatement pst = conn.prepareStatement(querry);

                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Selected Student Record is Deleted");
                    pst.close();
                    loadstudents();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        return mainPanel;
    }

    public void onAddStudent() {
        loadstudents();

        System.out.println("refresh");
    }

    public void loadstudents() {
        try ( Connection conn = Sqlite.connect()) {
            Statement stmt = conn.createStatement();
            ResultSet rs1 = stmt.executeQuery("SELECT * FROM student");
            int rowCount = 0;
            int colCount = 8;
            while (rs1.next()) {
                rowCount++;
            }
            ResultSet rs2 = stmt.executeQuery("SELECT * FROM student");
            data = new String[rowCount][colCount];
            int rowNum = 0;
            while (rs2.next()) {
                data[rowNum][0] = rs2.getString("id");
                data[rowNum][1] = rs2.getString("fullName");
                data[rowNum][2] = rs2.getString("grade");
                data[rowNum][3] = rs2.getString("parentphone");
              

                int courseId = rs2.getInt("courseid");

                Statement stmt2 = conn.createStatement();
                ResultSet rs3 = stmt2.executeQuery("SELECT * FROM course WHERE courseid = " + courseId + ";");
                while (rs3.next()) {
                    data[rowNum][4] = rs3.getString("CourseName");
                }
                rowNum++;
                          }

            table = new JTable(data, col);
            JScrollPane tablePane = new JScrollPane(table);
            mainPanel.removeAll();
            mainPanel.add(controlPanel, BorderLayout.NORTH);
            mainPanel.add(tablePane, BorderLayout.CENTER);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void onAUpdateStudent() {
        loadstudents();
    }

    interface NewStudentListener {

        public void onAddStudent();
    }

}
