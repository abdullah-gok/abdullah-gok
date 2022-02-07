package de.course.management;

import de.course.management.db.Sqlite;
import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

public class Teacher {

    String[][] data;
    String[] col = new String[]{
        "TeacherID",
        "TeacherName",
        "Branch",
        "Birthday",
        "MobileNumber"};
    JTable table;
    JPanel mainPanel = new JPanel();
    JPanel controlPanel = new JPanel();
    DataBaseManager databasemanager = new DataBaseManager();

    public JPanel init() {

        BorderLayout layout = new BorderLayout();
        layout.setHgap(10);
        layout.setVgap(10);
        mainPanel.setLayout(layout);
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel label = new JLabel();
        label.setFont(new Font(label.getName(), Font.BOLD, 38));
        label.setForeground(Color.blue);
        JButton deleteteacher = new JButton("Delete Teacher");
        JButton addNewTeacher = new JButton("Add New Teacher");
        JButton updateTeacher = new JButton("Update Teacher");
        controlPanel.add(addNewTeacher);
        controlPanel.add(updateTeacher);
        controlPanel.add(deleteteacher);
        loadteacher();
        addNewTeacher.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                JDialog addNewTeacherDialog = new JDialog();
                addNewTeacherDialog.setTitle("Add New Teacher");
                addNewTeacherDialog.pack();
                addNewTeacherDialog.setVisible(true);
                addNewTeacherDialog.toFront();
                addNewTeacherDialog.setSize(460, 420);
                addNewTeacherDialog.setResizable(false);
                addNewTeacherDialog.setLocation(400, 300);
                addNewTeacherDialog.setLayout(new FlowLayout(FlowLayout.LEFT));
                addNewTeacherDialog.setModal(true);
                addNewTeacherDialog.setAlwaysOnTop(true);

                TextField teacherIdTextField = new TextField("Enter Teacher ID");
                teacherIdTextField.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        char c = e.getKeyChar();
                        if (!((c >= '0') && (c <= '9')
                                || (c == KeyEvent.VK_BACK_SPACE)
                                || (c == KeyEvent.VK_DELETE))) {
                            e.consume();
                        }
                    }
                });
                teacherIdTextField.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        if (teacherIdTextField.getText().length() >= 3) {
                            e.consume();
                        }
                    }
                });
                TextField teacherNameTextField = new TextField("Enter Teacher Name");
                teacherNameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
                    public void keyTyped(java.awt.event.KeyEvent evt) {
                        if (!(Character.isLetter(evt.getKeyChar()))) {
                            evt.consume();
                        }
                    }
                });
                TextField teacherBranchTextField = new TextField("Enter the Branch of  Teacher");
                teacherBranchTextField.addKeyListener(new java.awt.event.KeyAdapter() {
                    public void keyTyped(java.awt.event.KeyEvent evt) {
                        if (!(Character.isLetter(evt.getKeyChar()))) {
                            evt.consume();
                        }
                    }
                });
                TextField teacherbirtdayTextField = new TextField("Enter the  Birthday of Teacher");
                teacherbirtdayTextField.addKeyListener(new KeyAdapter() {
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
                teacherbirtdayTextField.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        if (teacherIdTextField.getText().length() >= 10) {
                            e.consume();
                        }
                    }
                });

                TextField mobileteacherTextField = new TextField("Enter the  Mobile Number  of Teacher");
                mobileteacherTextField.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        char c = e.getKeyChar();
                        if (!((c >= '0') && (c <= '9')
                                || (c == KeyEvent.VK_BACK_SPACE)
                                || (c == KeyEvent.VK_DELETE))) {
                            e.consume();
                        }
                    }
                });

                JLabel teacherIdLabel = new JLabel("Enter Teacher ID");
                JLabel teacherNameLabel = new JLabel("Enter Teacher Name");
                JLabel teacherBranchLabel = new JLabel("Enter the Branch of  Teacher");
                JLabel teacherbirtdayLabel = new JLabel("Enter the  Birthday of Teacher (Day.Month.Year)");
                JLabel mobileteacherLabel = new JLabel("Enter the  Mobile Number  of Teacher");

                JButton saveButton = new JButton("Save  ");
                saveButton.setBackground(Color.LIGHT_GRAY);
                saveButton.setFont(saveButton.getFont().deriveFont(20.0f));
                saveButton.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {

                        if (teacherIdTextField.getText().trim().isEmpty()) {
                            teacherIdTextField.setBorder(new LineBorder(Color.red, 2));
                            saveButton.setBackground(Color.red);
                            teacherIdTextField.addKeyListener(new KeyAdapter() {
                                public void keyPressed(KeyEvent ke) {
                                    if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535)) {
                                        teacherIdTextField.setBorder(new LineBorder(Color.green, 2));
                                        saveButton.setBackground(Color.LIGHT_GRAY);
                                    }
                                }
                            });
                        }

                        if (teacherNameTextField.getText().equals("")) {
                            teacherNameTextField.setBorder(new LineBorder(Color.red, 2));
                            saveButton.setBackground(Color.red);
                            teacherNameTextField.addKeyListener(new KeyAdapter() {
                                public void keyPressed(KeyEvent ke) {
                                    if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535)) {
                                        teacherNameTextField.setBorder(new LineBorder(Color.green, 2));
                                        saveButton.setBackground(Color.LIGHT_GRAY);
                                    }
                                }
                            });
                        }

                        if (teacherBranchTextField.getText().equals("")) {
                            teacherBranchTextField.setBorder(new LineBorder(Color.red, 2));
                            saveButton.setBackground(Color.red);
                            teacherBranchTextField.addKeyListener(new KeyAdapter() {
                                public void keyPressed(KeyEvent ke) {
                                    if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535)) {
                                        teacherBranchTextField.setBorder(new LineBorder(Color.green, 2));
                                        saveButton.setBackground(Color.LIGHT_GRAY);
                                    }
                                }
                            });
                        }

                        if (teacherbirtdayTextField.getText().equals("")) {
                            teacherbirtdayTextField.setBorder(new LineBorder(Color.red, 2));
                            saveButton.setBackground(Color.red);
                            teacherbirtdayTextField.addKeyListener(new KeyAdapter() {
                                public void keyPressed(KeyEvent ke) {
                                    if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535)) {
                                        teacherbirtdayTextField.setBorder(new LineBorder(Color.green, 2));
                                        saveButton.setBackground(Color.LIGHT_GRAY);
                                    }
                                }
                            });
                        }

                        if (mobileteacherTextField.getText().equals("")) {
                            mobileteacherTextField.setBorder(new LineBorder(Color.red, 2));
                            saveButton.setBackground(Color.red);
                            mobileteacherTextField.addKeyListener(new KeyAdapter() {
                                public void keyPressed(KeyEvent ke) {
                                    if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535)) {
                                        mobileteacherTextField.setBorder(new LineBorder(Color.green, 2));
                                        saveButton.setBackground(Color.LIGHT_GRAY);
                                    }
                                }
                            });
                        } else {
                            TeacherView teacher = new TeacherView(
                                    Integer.parseInt(teacherIdTextField.getText()),
                                    teacherNameTextField.getText(),
                                    teacherBranchTextField.getText(),
                                    teacherbirtdayTextField.getText(),
                                    mobileteacherTextField.getText());

                            databasemanager.addteacher(teacher);
                            JOptionPane.showMessageDialog(null, " Saved Successfully to Database");
                            addNewTeacherDialog.setVisible(false);
                            JFrame menu = Menu.init();
                            menu.setVisible(false);
                            loadteacher();
                        }
                    }
                });

                JButton cancelButton = new JButton("Cancel");
                cancelButton.setBackground(Color.LIGHT_GRAY);
                cancelButton.setFont(saveButton.getFont().deriveFont(20.0f));
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        addNewTeacherDialog.setVisible(false);
                        JFrame menu = Menu.init();
                        menu.setVisible(false);
                    }
                });
                addNewTeacherDialog.add(teacherIdLabel);
                addNewTeacherDialog.add(teacherIdTextField);
                addNewTeacherDialog.add(teacherNameLabel);
                addNewTeacherDialog.add(teacherNameTextField);
                addNewTeacherDialog.add(teacherBranchLabel);
                addNewTeacherDialog.add(teacherBranchTextField);
                addNewTeacherDialog.add(teacherbirtdayLabel);
                addNewTeacherDialog.add(teacherbirtdayTextField);
                addNewTeacherDialog.add(mobileteacherLabel);
                addNewTeacherDialog.add(mobileteacherTextField);
                addNewTeacherDialog.add(saveButton);
                addNewTeacherDialog.add(cancelButton);
            }
        });

        updateTeacher.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.setVisible(false);
                JDialog UpdateTeacherDialog = new JDialog();
                UpdateTeacherDialog.setTitle("Update Teacher");
                UpdateTeacherDialog.pack();
                UpdateTeacherDialog.setVisible(true);
                UpdateTeacherDialog.toFront();
                UpdateTeacherDialog.setSize(460, 420);
                UpdateTeacherDialog.setResizable(false);
                UpdateTeacherDialog.setLocation(400, 300);
                UpdateTeacherDialog.setLayout(new FlowLayout(FlowLayout.LEFT));
                UpdateTeacherDialog.setModal(true);
                UpdateTeacherDialog.setAlwaysOnTop(true);
                int selectedRow = table.getSelectedRow();
                TeacherView Teacher = new TeacherView(Integer.parseInt(data[selectedRow][0]), data[selectedRow][1],
                        data[selectedRow][2], data[selectedRow][3], data[selectedRow][4]);

                loadteacher();
                TextField teacherIdTextField = new TextField("Enter Teacher ID");
                teacherIdTextField.setText(String.valueOf(TeacherView.getTeacherId()));
                teacherIdTextField.setEditable(false);
                TextField teacherNameTextField = new TextField("Enter Teacher Name");
                teacherNameTextField.setText(TeacherView.getTeacherName());
                TextField teacherBranchTextField = new TextField("Enter the Branch of  Teacher");
                teacherBranchTextField.setText(TeacherView.getBranch());
                TextField teacherbirtdayTextField = new TextField("Enter the  Birthday of Teacher");
                teacherbirtdayTextField.setText(TeacherView.getBirthday());
                TextField mobileteacherTextField = new TextField("Enter the  Mobile Number  of Teacher");
                mobileteacherTextField.setText(TeacherView.getMobileNumber());

                JLabel teacherIdLabel = new JLabel("Enter Teacher ID");
                JLabel teacherNameLabel = new JLabel("Enter Teacher Name");
                JLabel teacherBranchLabel = new JLabel("Enter the Branch of  Teacher");
                JLabel teacherbirtdayLabel = new JLabel("Enter the  Birthday of Teacher");
                JLabel mobileteacherLabel = new JLabel("Enter the  Mobile Number  of Teacher");

                JButton saveButton = new JButton("Save  ");
                saveButton.setBackground(Color.LIGHT_GRAY);
                saveButton.setFont(saveButton.getFont().deriveFont(20.0f));
                saveButton.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {

                        if (teacherIdTextField.getText().trim().isEmpty()) {
                            teacherIdTextField.setBorder(new LineBorder(Color.red, 2));
                            saveButton.setBackground(Color.red);
                            teacherIdTextField.addKeyListener(new KeyAdapter() {
                                public void keyPressed(KeyEvent ke) {
                                    if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535)) {
                                        teacherIdTextField.setBorder(new LineBorder(Color.green, 2));
                                        saveButton.setBackground(Color.LIGHT_GRAY);
                                    }
                                }
                            });
                        }

                        if (teacherNameTextField.getText().equals("")) {
                            teacherNameTextField.setBorder(new LineBorder(Color.red, 2));
                            saveButton.setBackground(Color.red);
                            teacherNameTextField.addKeyListener(new KeyAdapter() {
                                public void keyPressed(KeyEvent ke) {
                                    if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535)) {
                                        teacherNameTextField.setBorder(new LineBorder(Color.green, 2));
                                        saveButton.setBackground(Color.LIGHT_GRAY);
                                    }
                                }
                            });
                        }

                        if (teacherBranchTextField.getText().equals("")) {
                            teacherBranchTextField.setBorder(new LineBorder(Color.red, 2));
                            saveButton.setBackground(Color.red);
                            teacherBranchTextField.addKeyListener(new KeyAdapter() {
                                public void keyPressed(KeyEvent ke) {
                                    if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535)) {
                                        teacherBranchTextField.setBorder(new LineBorder(Color.green, 2));
                                        saveButton.setBackground(Color.LIGHT_GRAY);
                                    }
                                }
                            });
                        }

                        if (teacherbirtdayTextField.getText().equals("")) {
                            teacherbirtdayTextField.setBorder(new LineBorder(Color.red, 2));
                            saveButton.setBackground(Color.red);
                            teacherbirtdayTextField.addKeyListener(new KeyAdapter() {
                                public void keyPressed(KeyEvent ke) {
                                    if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535)) {
                                        teacherbirtdayTextField.setBorder(new LineBorder(Color.green, 2));
                                        saveButton.setBackground(Color.LIGHT_GRAY);
                                    }
                                }
                            });
                        }

                        if (mobileteacherTextField.getText().equals("")) {
                            mobileteacherTextField.setBorder(new LineBorder(Color.red, 2));
                            saveButton.setBackground(Color.red);
                            mobileteacherTextField.addKeyListener(new KeyAdapter() {
                                public void keyPressed(KeyEvent ke) {
                                    if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535)) {
                                        mobileteacherTextField.setBorder(new LineBorder(Color.green, 2));
                                        saveButton.setBackground(Color.LIGHT_GRAY);
                                    }
                                }
                            });
                        } else {

                            TeacherView teacher = new TeacherView(
                                    Integer.parseInt(teacherIdTextField.getText()),
                                    teacherNameTextField.getText(),
                                    teacherBranchTextField.getText(),
                                    teacherbirtdayTextField.getText(),
                                    mobileteacherTextField.getText());
                            databasemanager.updateteacher(teacher);
                            UpdateTeacherDialog.setVisible(false);
                            loadteacher();
                        }
                    }
                });

                JButton cancelButton = new JButton("Cancel");
                cancelButton.setBackground(Color.LIGHT_GRAY);
                cancelButton.setFont(saveButton.getFont().deriveFont(20.0f));
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        UpdateTeacherDialog.setVisible(false);
                        JFrame menu = Menu.init();
                        menu.setVisible(false);
                    }
                });

                UpdateTeacherDialog.add(teacherIdLabel);
                UpdateTeacherDialog.add(teacherIdTextField);
                UpdateTeacherDialog.add(teacherNameLabel);
                UpdateTeacherDialog.add(teacherNameTextField);
                UpdateTeacherDialog.add(teacherBranchLabel);
                UpdateTeacherDialog.add(teacherBranchTextField);
                UpdateTeacherDialog.add(teacherbirtdayLabel);
                UpdateTeacherDialog.add(teacherbirtdayTextField);
                UpdateTeacherDialog.add(mobileteacherLabel);
                UpdateTeacherDialog.add(mobileteacherTextField);
                UpdateTeacherDialog.add(saveButton);
                UpdateTeacherDialog.add(cancelButton);
            }
        });

        deleteteacher.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                System.out.println("--------");
                System.out.println(data[selectedRow][0]);
                System.out.println("--------");
                try {
                    Connection conn = Sqlite.connect();
                    String querry = "delete from teacher where TeacherId='" + data[selectedRow][0] + "'";
                    PreparedStatement pst = conn.prepareStatement(querry);
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Selected Teacher Record is Deleted");
                    pst.close();
                    loadteacher();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        return mainPanel;
    }

    public void loadteacher() {
        try ( Connection conn = Sqlite.connect()) {
            Statement stmt = conn.createStatement();
            ResultSet rs1 = stmt.executeQuery("SELECT * FROM teacher");

            int rowCount = 0;
            int colCount = 5;

            while (rs1.next()) {
                rowCount++;
            }
            ResultSet rs2 = stmt.executeQuery("SELECT * FROM teacher");
            data = new String[rowCount][colCount];

            int rowNum = 0;
            while (rs2.next()) {
                data[rowNum][0] = rs2.getString("TeacherID");
                data[rowNum][1] = rs2.getString("TeacherName");
                data[rowNum][2] = rs2.getString("Branch");
                data[rowNum][3] = rs2.getString("Birthday");
                data[rowNum][4] = rs2.getString("MobileNumber");
                rowNum++;
            }
            table = new JTable(data, col);
            JScrollPane tablePane = new JScrollPane(table);
            mainPanel.removeAll();
            mainPanel.add(controlPanel, BorderLayout.NORTH);
            mainPanel.add(tablePane, BorderLayout.CENTER);
        } catch (SQLException ex) {
        }
    }

    public void onAddTeacher() {
        loadteacher();
    }

    public void onUpdateTeacher() {
        loadteacher();
    }

    interface NewTeacherListener {

        public void onAddTeacher();
    }

    interface UpdateTeacherListener {

        public void onUpdateTeacher();
    }

}
