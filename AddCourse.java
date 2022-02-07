package de.course.management;

import de.course.management.db.Sqlite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

public class AddCourse {

    String[][] data;
    String[] col = new String[]{
        "courseid",
        "CourseName",
        "CourseStartDate",
        "CourseLevel",
        "TeacherId"};
    JTable table;
    JPanel mainPanel = new JPanel();
    JPanel controlPanel = new JPanel();

    public JPanel init() {

        DataBaseManager databasemanager = new DataBaseManager();
        BorderLayout layout = new BorderLayout();
        layout.setHgap(10);
        layout.setVgap(10);
        mainPanel.setLayout(layout);
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton addNewCourse = new JButton("Add New Course");
        JLabel label = new JLabel();
        label.setFont(new Font(label.getName(), Font.BOLD, 38));
        label.setForeground(Color.blue);
        JButton updatecourse = new JButton("Update Course");
        JButton deletecourse = new JButton("Delete Course");

        addNewCourse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JDialog addNewCourseDialog = new JDialog();
                addNewCourseDialog.setTitle("Add New Course");
                addNewCourseDialog.pack();
                addNewCourseDialog.setVisible(true);
                addNewCourseDialog.toFront();
                addNewCourseDialog.setSize(460, 420);
                addNewCourseDialog.setResizable(false);
                addNewCourseDialog.setLocation(400, 300);
                addNewCourseDialog.setLayout(new FlowLayout(FlowLayout.LEFT));
                addNewCourseDialog.setModal(true);
                addNewCourseDialog.setAlwaysOnTop(true);

                TextField courseIdTextField = new TextField("Enter Course ID Number");
                courseIdTextField.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        char c = e.getKeyChar();
                        if (!((c >= '0') && (c <= '9')
                                || (c == KeyEvent.VK_BACK_SPACE)
                                || (c == KeyEvent.VK_DELETE))) {
                            e.consume();
                        }
                    }
                });
                courseIdTextField.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        if (courseIdTextField.getText().length() >= 3) {
                            e.consume();
                        }
                    }
                });
                TextField courseNameTextField = new TextField("Enter Course Name");
                courseNameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
                    public void keyTyped(java.awt.event.KeyEvent evt) {
                        if (!(Character.isLetter(evt.getKeyChar()))) {
                            evt.consume();
                        }
                    }
                });
                TextField startDateOfCourseTextField = new TextField("Enter the Start Date of Course");

                startDateOfCourseTextField.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        char c = e.getKeyChar();
                        if (!((c >= '0') && (c <= '9')
                                || (c == KeyEvent.VK_BACK_SPACE)
                                || (c == KeyEvent.VK_PERIOD)
                                || (c == KeyEvent.VK_DELETE))) {
                            e.consume();
                        }
                    }
                });
                startDateOfCourseTextField.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        if (startDateOfCourseTextField.getText().length() >= 10) {
                            e.consume();
                        }
                    }
                });

                final JComboBox cbTeacher = new JComboBox();
                cbTeacher.setBackground(Color.LIGHT_GRAY);
                cbTeacher.setPreferredSize(new Dimension(430, 40));
                cbTeacher.setBackground(Color.white);

                try ( Connection conn = Sqlite.connect()) {
                    String sql = "select DISTINCT TeacherName  from teacher";
                    System.out.println(conn);
                    PreparedStatement pst = conn.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery();
                    System.out.println(sql);
                    while (rs.next()) {
                        cbTeacher.addItem(rs.getString("TeacherName"));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                final JComboBox cbLevel = new JComboBox();
                cbLevel.setBackground(Color.LIGHT_GRAY);
                cbLevel.setPreferredSize(new Dimension(430, 40));
                cbLevel.setBackground(Color.white);

                try ( Connection conn = Sqlite.connect()) {
                    String sql = "select DISTINCT CourseLevel  from course";
                    System.out.println(conn);
                    PreparedStatement pst = conn.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery();
                    System.out.println(sql);
                    while (rs.next()) {
                        cbLevel.addItem(rs.getString("CourseLevel"));
                    }
                } catch (Exception es) {
                    es.printStackTrace();
                }

                JLabel courseIdLabel = new JLabel("Enter Course ID Number");
                JLabel courseNameLabel = new JLabel("Enter Course Name");
                JLabel startDateOfCourseLabel = new JLabel("Enter the Start Date of Course");
                JLabel courseTeacherLabel = new JLabel("Select Course Teacher                                         ");
                JLabel courseLevelLabel = new JLabel("Select  Course  Level                                         ");

                JButton saveButton = new JButton("Save  ");
                saveButton.setBackground(Color.LIGHT_GRAY);
                saveButton.setFont(saveButton.getFont().deriveFont(20.0f));
                saveButton.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {

                        if (courseIdTextField.getText().trim().isEmpty()) {
                            courseIdTextField.setBorder(new LineBorder(Color.red, 2));
                            saveButton.setBackground(Color.red);
                            courseIdTextField.addKeyListener(new KeyAdapter() {
                                public void keyPressed(KeyEvent ke) {
                                    if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535)) {
                                        courseIdTextField.setBorder(new LineBorder(Color.green, 2));
                                        saveButton.setBackground(Color.LIGHT_GRAY);
                                    }
                                }
                            });
                        }

                        if (courseNameTextField.getText().equals("")) {
                            courseNameTextField.setBorder(new LineBorder(Color.red, 2));
                            saveButton.setBackground(Color.red);
                            courseNameTextField.addKeyListener(new KeyAdapter() {
                                public void keyPressed(KeyEvent ke) {
                                    if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535)) {
                                        courseNameTextField.setBorder(new LineBorder(Color.green, 2));
                                        saveButton.setBackground(Color.LIGHT_GRAY);
                                    }
                                }
                            });
                        }

                        if (startDateOfCourseTextField.getText().equals("")) {
                            startDateOfCourseTextField.setBorder(new LineBorder(Color.red, 2));
                            saveButton.setBackground(Color.red);
                            startDateOfCourseTextField.addKeyListener(new KeyAdapter() {
                                public void keyPressed(KeyEvent ke) {
                                    if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535)) {
                                        startDateOfCourseTextField.setBorder(new LineBorder(Color.green, 2));
                                        saveButton.setBackground(Color.LIGHT_GRAY);
                                    }
                                }
                            });
                        } else {
                            String TeacherName = cbTeacher.getSelectedItem().toString();
                            String level = cbLevel.getSelectedItem().toString();
                            Integer teacherId = null;
                            try ( Connection conn = Sqlite.connect()) {
                                String sql = "select TeacherId  from teacher  WHERE TeacherName = '" + TeacherName + "'";
                                PreparedStatement pst = conn.prepareStatement(sql);
                                ResultSet rs = pst.executeQuery();
                                while (rs.next()) {
                                    teacherId = rs.getInt("TeacherId");
                                    break;
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            Course course = new Course(
                                    Integer.parseInt(courseIdTextField.getText()),
                                    courseNameTextField.getText(),
                                    startDateOfCourseTextField.getText(),
                                    level,
                                    teacherId.toString());
                            databasemanager.addcourse(course);
                            addNewCourseDialog.setVisible(false);
                            loadcourse();
                        }
                    }
                });

                JButton cancelButton = new JButton("Cancel");
                cancelButton.setBackground(Color.LIGHT_GRAY);
                cancelButton.setFont(saveButton.getFont().deriveFont(20.0f));
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        addNewCourseDialog.setVisible(false);
                    }
                });

                addNewCourseDialog.add(courseIdLabel);
                addNewCourseDialog.add(courseIdTextField);
                addNewCourseDialog.add(courseNameLabel);
                addNewCourseDialog.add(courseNameTextField);
                addNewCourseDialog.add(startDateOfCourseLabel);
                addNewCourseDialog.add(startDateOfCourseTextField);
                addNewCourseDialog.add(courseLevelLabel);
                addNewCourseDialog.add(cbLevel);
                addNewCourseDialog.add(courseTeacherLabel);
                addNewCourseDialog.add(cbTeacher);
                addNewCourseDialog.add(saveButton);
                addNewCourseDialog.add(cancelButton);
            }
        });

        updatecourse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainPanel.setVisible(false);

                int selectedRow = table.getSelectedRow();
                Course course = new Course(Integer.parseInt(data[selectedRow][0]), data[selectedRow][1],
                        data[selectedRow][2], data[selectedRow][3], data[selectedRow][4]);

                JDialog updateCourseDialog = new JDialog();
                updateCourseDialog.setTitle("Update Course");
                updateCourseDialog.pack();
                updateCourseDialog.setVisible(true);
                updateCourseDialog.toFront();
                updateCourseDialog.setSize(460, 420);
                updateCourseDialog.setResizable(false);
                updateCourseDialog.setLocation(400, 300);
                updateCourseDialog.setLayout(new FlowLayout(FlowLayout.LEFT));
                updateCourseDialog.setModal(true);
                updateCourseDialog.setAlwaysOnTop(true);

                TextField courseIdTextField = new TextField("Enter Course ID Number");
                courseIdTextField.setText(String.valueOf(course.getCourseid()));
                courseIdTextField.setEditable(false);
                TextField courseNameTextField = new TextField("Enter Course Name");
                courseNameTextField.setText(course.getCourseName());
                TextField startDateOfCourseTextField = new TextField("Enter the Start Date of Course");
                startDateOfCourseTextField.setText(course.getCourseStartDate());

                final JComboBox cbTeacher = new JComboBox();
                cbTeacher.setBackground(Color.LIGHT_GRAY);
                cbTeacher.setPreferredSize(new Dimension(430, 40));
                cbTeacher.setBackground(Color.white);

                try ( Connection conn = Sqlite.connect()) {
                    String sql = "select DISTINCT TeacherName  from teacher";
                    System.out.println(conn);
                    PreparedStatement pst = conn.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery();
                    System.out.println(sql);
                    while (rs.next()) {
                        cbTeacher.addItem(rs.getString("TeacherName"));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                final JComboBox cbLevel = new JComboBox();
                cbLevel.setBackground(Color.LIGHT_GRAY);
                cbLevel.setPreferredSize(new Dimension(430, 40));
                cbLevel.setBackground(Color.white);

                try ( Connection conn = Sqlite.connect()) {
                    String sql = "select DISTINCT CourseLevel  from course";
                    System.out.println(conn);
                    PreparedStatement pst = conn.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery();
                    System.out.println(sql);
                    while (rs.next()) {
                        cbLevel.addItem(rs.getString("CourseLevel"));
                    }
                } catch (Exception es) {
                    es.printStackTrace();
                }

                JLabel courseIdLabel = new JLabel("Enter Course ID Number");
                JLabel courseNameLabel = new JLabel("Enter Course Name");
                JLabel startDateOfCourseLabel = new JLabel("Enter the Start Date of Course");
                JLabel courseTeacherLabel = new JLabel("Select Course Teacher                                         ");
                JLabel courseLevelLabel = new JLabel("Select  Course  Level                                         ");

                JButton saveButton = new JButton("Save  ");
                saveButton.setBackground(Color.LIGHT_GRAY);
                saveButton.setFont(saveButton.getFont().deriveFont(20.0f));
                saveButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        if (courseIdTextField.getText().trim().isEmpty()) {
                            courseIdTextField.setBorder(new LineBorder(Color.red, 2));
                            saveButton.setBackground(Color.red);
                            courseIdTextField.addKeyListener(new KeyAdapter() {
                                public void keyPressed(KeyEvent ke) {
                                    if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535)) {
                                        courseIdTextField.setBorder(new LineBorder(Color.green, 2));
                                        saveButton.setBackground(Color.LIGHT_GRAY);
                                    }
                                }
                            });
                        }

                        if (courseNameTextField.getText().equals("")) {
                            courseNameTextField.setBorder(new LineBorder(Color.red, 2));
                            saveButton.setBackground(Color.red);
                            courseNameTextField.addKeyListener(new KeyAdapter() {
                                public void keyPressed(KeyEvent ke) {
                                    if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535)) {
                                        courseNameTextField.setBorder(new LineBorder(Color.green, 2));
                                        saveButton.setBackground(Color.LIGHT_GRAY);
                                    }
                                }
                            });
                        }

                        if (startDateOfCourseTextField.getText().equals("")) {
                            startDateOfCourseTextField.setBorder(new LineBorder(Color.red, 2));
                            saveButton.setBackground(Color.red);
                            startDateOfCourseTextField.addKeyListener(new KeyAdapter() {
                                public void keyPressed(KeyEvent ke) {
                                    if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535)) {
                                        startDateOfCourseTextField.setBorder(new LineBorder(Color.green, 2));
                                        saveButton.setBackground(Color.LIGHT_GRAY);
                                    }
                                }
                            });
                        } else {
                            String TeacherName = cbTeacher.getSelectedItem().toString();
                            String level = cbLevel.getSelectedItem().toString();
                            Integer teacherId = null;
                            try ( Connection conn = Sqlite.connect()) {
                                String sql = "select TeacherId  from teacher  WHERE TeacherName = '" + TeacherName + "'";
                                PreparedStatement pst = conn.prepareStatement(sql);
                                ResultSet rs = pst.executeQuery();
                                while (rs.next()) {
                                    teacherId = rs.getInt("TeacherId");
                                    break;
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            Course course = new Course(
                                    Integer.parseInt(courseIdTextField.getText()),
                                    courseNameTextField.getText(),
                                    startDateOfCourseTextField.getText(),
                                    level,
                                    teacherId.toString()
                            );

                            databasemanager.updatecourse(course);
                            updateCourseDialog.setVisible(false);
                            loadcourse();

                        }
                    }
                });

                JButton cancelButton = new JButton("Cancel");
                cancelButton.setBackground(Color.LIGHT_GRAY);
                cancelButton.setFont(saveButton.getFont().deriveFont(20.0f));
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        updateCourseDialog.setVisible(false);
                        JFrame menu = Menu.init();
                        menu.setVisible(false);
                    }
                });

                updateCourseDialog.add(courseIdLabel);
                updateCourseDialog.add(courseIdTextField);
                updateCourseDialog.add(courseNameLabel);
                updateCourseDialog.add(courseNameTextField);
                updateCourseDialog.add(startDateOfCourseLabel);
                updateCourseDialog.add(startDateOfCourseTextField);
                updateCourseDialog.add(courseLevelLabel);
                updateCourseDialog.add(cbLevel);
                updateCourseDialog.add(courseTeacherLabel);
                updateCourseDialog.add(cbTeacher);
                updateCourseDialog.add(saveButton);
                updateCourseDialog.add(cancelButton);
            }
        });

        deletecourse.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                try ( Connection conn = Sqlite.connect()) {
                    String querry = "delete from course where courseid='" + data[selectedRow][0] + "'";
                    PreparedStatement pst = conn.prepareStatement(querry);
                    pst.execute();
                    pst.close();
                    loadcourse();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        controlPanel.add(addNewCourse);
        controlPanel.add(updatecourse);
        controlPanel.add(deletecourse);
        loadcourse();
        return mainPanel;
    }

    public void loadcourse() {
        try ( Connection conn = Sqlite.connect()) {
            Statement stmt = conn.createStatement();
            ResultSet rs1 = stmt.executeQuery("SELECT * FROM course");

            int rowCount = 0;
            int colCount = 5;

            while (rs1.next()) {
                rowCount++;
            }

            ResultSet rs2 = stmt.executeQuery("SELECT * FROM course");
            data = new String[rowCount][colCount];
            int rowNum = 0;
            while (rs2.next()) {
                data[rowNum][0] = rs2.getString("courseid");
                data[rowNum][1] = rs2.getString("CourseName");
                data[rowNum][2] = rs2.getString("CourseStartDate");
                data[rowNum][3] = rs2.getString("CourseLevel");
                
                int teacherId = rs2.getInt("TeacherId");

                Statement stmt2 = conn.createStatement();
                ResultSet rs3 = stmt2.executeQuery("SELECT * FROM teacher WHERE TeacherId = " + teacherId + ";");
                while (rs3.next()) {
                data[rowNum][4] = rs3.getString("TeacherName");
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

    public void onAUpdateCourse() {
        loadcourse();
    }

    public void onAddCourse() {
        loadcourse();
    }

    interface NewCourseListener {

        public void onAddCourse();
    }
};
