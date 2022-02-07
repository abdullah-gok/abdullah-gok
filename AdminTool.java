package de.course.management;

import de.course.management.db.Sqlite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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

public class AdminTool {

    String[][] data;
    String[] col = new String[]{
        "userid",
        "username",
        "password",
        "name",
        "title"};
    JTable table;
    JPanel mainPanel = new JPanel();
    JPanel controlPanel = new JPanel();

    public JPanel init() {
        DataBaseManager databasemanager = new DataBaseManager();
        AdminTool listener = new AdminTool();

        BorderLayout layout = new BorderLayout();
        layout.setHgap(10);
        layout.setVgap(10);
        mainPanel.setLayout(layout);
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton addnewadmin = new JButton("Add New Admin");
        JButton updateadmin = new JButton("Update Admin");
        JButton deleteadmin = new JButton("Delete Admin");

        addnewadmin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JDialog addNewAdminDialog = new JDialog();
                addNewAdminDialog.setTitle("Add New Teacher");
                addNewAdminDialog.pack();
                addNewAdminDialog.setVisible(true);
                addNewAdminDialog.toFront();
                addNewAdminDialog.setSize(460, 420);
                addNewAdminDialog.setResizable(false);
                addNewAdminDialog.setLocation(400, 300);
                addNewAdminDialog.setLayout(new FlowLayout(FlowLayout.LEFT));
                addNewAdminDialog.setModal(true);
                addNewAdminDialog.setAlwaysOnTop(true);

                TextField adminIdTextField = new TextField("Enter User ID");

                adminIdTextField.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        char c = e.getKeyChar();
                        if (!((c >= '0') && (c <= '9')
                                || (c == KeyEvent.VK_BACK_SPACE)
                                || (c == KeyEvent.VK_DELETE))) {
                            e.consume();
                        }
                    }
                });
                adminIdTextField.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        if (adminIdTextField.getText().length() >= 3) {
                            e.consume();
                        }
                    }
                });

                TextField usernameTextField = new TextField("Enter User Name");
                TextField adminPasswordTextField = new TextField("Enter the Password");
                TextField adminNameTextField = new TextField("Enter the Fullname of New Admin");
                adminNameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
                    public void keyTyped(java.awt.event.KeyEvent evt) {
                        if (!(Character.isLetter(evt.getKeyChar()))) {
                            evt.consume();
                        }
                    }
                });
                TextField adminTitleTextField = new TextField("Enter the titel of New Admin");
                adminNameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
                    public void keyTyped(java.awt.event.KeyEvent evt) {
                        if (!(Character.isLetter(evt.getKeyChar()))) {
                            evt.consume();
                        }
                    }
                });

                JLabel adminIdLabel = new JLabel("Enter User ID");
                JLabel usernameLabel = new JLabel("Enter User Name");
                JLabel adminPasswordLabel = new JLabel("Enter the Password");
                JLabel adminNameLabel = new JLabel("Enter the Fullname of New Admin");
                JLabel adminTitleLabel = new JLabel("Enter the titel of New Admin");
                

                JButton saveButton = new JButton("Save  ");
                saveButton.setBackground(Color.LIGHT_GRAY);
                saveButton.setFont(saveButton.getFont().deriveFont(20.0f));
                saveButton.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {

                        if (adminIdTextField.getText().trim().isEmpty()) {
                            adminIdTextField.setBorder(new LineBorder(Color.red, 2));
                            saveButton.setBackground(Color.red);
                            adminIdTextField.addKeyListener(new KeyAdapter() {
                                public void keyPressed(KeyEvent ke) {
                                    if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535)) {
                                        adminIdTextField.setBorder(new LineBorder(Color.green, 2));
                                        saveButton.setBackground(Color.LIGHT_GRAY);
                                    }
                                }
                            });
                        }

                        if (usernameTextField.getText().equals("")) {
                            usernameTextField.setBorder(new LineBorder(Color.red, 2));
                            saveButton.setBackground(Color.red);
                            usernameTextField.addKeyListener(new KeyAdapter() {
                                public void keyPressed(KeyEvent ke) {
                                    if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535)) {
                                        usernameTextField.setBorder(new LineBorder(Color.green, 2));
                                        saveButton.setBackground(Color.LIGHT_GRAY);
                                    }
                                }
                            });
                        }

                        if (adminPasswordTextField.getText().equals("")) {
                            adminPasswordTextField.setBorder(new LineBorder(Color.red, 2));
                            saveButton.setBackground(Color.red);
                            adminPasswordTextField.addKeyListener(new KeyAdapter() {
                                public void keyPressed(KeyEvent ke) {
                                    if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535)) {
                                        adminPasswordTextField.setBorder(new LineBorder(Color.green, 2));
                                        saveButton.setBackground(Color.LIGHT_GRAY);
                                    }
                                }
                            });
                        }

                        if (adminNameTextField.getText().equals("")) {
                            adminNameTextField.setBorder(new LineBorder(Color.red, 2));
                            saveButton.setBackground(Color.red);
                            adminNameTextField.addKeyListener(new KeyAdapter() {
                                public void keyPressed(KeyEvent ke) {
                                    if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535)) {
                                        adminNameTextField.setBorder(new LineBorder(Color.green, 2));
                                        saveButton.setBackground(Color.LIGHT_GRAY);
                                    }
                                }
                            });
                        }

                        if (adminTitleTextField.getText().equals("")) {
                            adminTitleTextField.setBorder(new LineBorder(Color.red, 2));
                            saveButton.setBackground(Color.red);
                            adminTitleTextField.addKeyListener(new KeyAdapter() {
                                public void keyPressed(KeyEvent ke) {
                                    if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535)) {
                                        adminTitleTextField.setBorder(new LineBorder(Color.green, 2));
                                        saveButton.setBackground(Color.LIGHT_GRAY);
                                    }
                                }
                            });
                        } else {
                            Admin admin = new Admin(
                                    Integer.parseInt(adminIdTextField.getText()),
                                    usernameTextField.getText(),
                                    adminPasswordTextField.getText(),
                                    adminNameTextField.getText(),
                                    adminTitleTextField.getText());

                            databasemanager.addadmin(admin);
                            addNewAdminDialog.setVisible(false);
                            loadadmin();
                            listener.onAddAdmin();
                        }
                    }
                });

                JButton cancelButton = new JButton("Cancel");
                cancelButton.setBackground(Color.LIGHT_GRAY);
                cancelButton.setFont(saveButton.getFont().deriveFont(20.0f));
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        addNewAdminDialog.setVisible(false);
                        JFrame menu = Menu.init();
                        menu.setVisible(false);
                    }
                });

                addNewAdminDialog.add(adminIdLabel);
                addNewAdminDialog.add(adminIdTextField);
                addNewAdminDialog.add(usernameLabel);
                addNewAdminDialog.add(usernameTextField);
                addNewAdminDialog.add(adminPasswordLabel);
                addNewAdminDialog.add(adminPasswordTextField);
                addNewAdminDialog.add(adminNameLabel);
                addNewAdminDialog.add(adminNameTextField);
                addNewAdminDialog.add(adminTitleLabel);
                addNewAdminDialog.add(adminTitleTextField);
                addNewAdminDialog.add(saveButton);
                addNewAdminDialog.add(cancelButton);
            }
        });

        updateadmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = table.getSelectedRow();
                Admin admin = new Admin(Integer.parseInt(data[selectedRow][0]), data[selectedRow][1],
                        data[selectedRow][2], data[selectedRow][3], data[selectedRow][4]);

                JDialog updateAdminDialog = new JDialog();
                updateAdminDialog.setTitle("Add New Teacher");
                updateAdminDialog.pack();
                updateAdminDialog.setVisible(true);
                updateAdminDialog.toFront();
                updateAdminDialog.setSize(460, 420);
                updateAdminDialog.setResizable(false);
                updateAdminDialog.setLocation(400, 300);
                updateAdminDialog.setLayout(new FlowLayout(FlowLayout.LEFT));
                updateAdminDialog.setModal(true);
                updateAdminDialog.setAlwaysOnTop(true);

                TextField adminIdTextField = new TextField("Enter User ID");
                adminIdTextField.setText(String.valueOf(admin.getuserid()));
                adminIdTextField.setEditable(false);

                TextField usernameTextField = new TextField("Enter User Name");
                usernameTextField.setText(admin.getusername());

                TextField adminPasswordTextField = new TextField("Enter the Password");
                adminPasswordTextField.setText(admin.getpassword());

                TextField adminNameTextField = new TextField("Enter the Fullname of New Admin");
                adminNameTextField.setText(admin.getpassword());

                TextField adminTitleTextField = new TextField("Enter the titel of New Admin");
                adminTitleTextField.setText(admin.getpassword());

                JLabel adminIdLabel = new JLabel("Enter User ID");
                JLabel usernameLabel = new JLabel("Enter User Name");
                JLabel adminPasswordLabel = new JLabel("Enter the Password");
                JLabel adminNameLabel = new JLabel("Enter the Fullname of New Admin");
                JLabel adminTitleLabel = new JLabel("Enter the titel of New Admin");

                JButton saveButton = new JButton("Save  ");
                saveButton.setBackground(Color.LIGHT_GRAY);
                saveButton.setFont(saveButton.getFont().deriveFont(20.0f));
                saveButton.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent ex) {

                        if (adminIdTextField.getText().trim().isEmpty()) {
                            adminIdTextField.setBorder(new LineBorder(Color.red, 2));
                            saveButton.setBackground(Color.red);
                            adminIdTextField.addKeyListener(new KeyAdapter() {
                                public void keyPressed(KeyEvent ke) {
                                    if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535)) {
                                        adminIdTextField.setBorder(new LineBorder(Color.green, 2));
                                        saveButton.setBackground(Color.LIGHT_GRAY);
                                    }
                                }
                            });
                        }

                        if (usernameTextField.getText().equals("")) {
                            usernameTextField.setBorder(new LineBorder(Color.red, 2));
                            saveButton.setBackground(Color.red);
                            usernameTextField.addKeyListener(new KeyAdapter() {
                                public void keyPressed(KeyEvent ke) {
                                    if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535)) {
                                        usernameTextField.setBorder(new LineBorder(Color.green, 2));
                                        saveButton.setBackground(Color.LIGHT_GRAY);
                                    }
                                }
                            });
                        }

                        if (adminPasswordTextField.getText().equals("")) {
                            adminPasswordTextField.setBorder(new LineBorder(Color.red, 2));
                            saveButton.setBackground(Color.red);
                            adminPasswordTextField.addKeyListener(new KeyAdapter() {
                                public void keyPressed(KeyEvent ke) {
                                    if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535)) {
                                        adminPasswordTextField.setBorder(new LineBorder(Color.green, 2));
                                        saveButton.setBackground(Color.LIGHT_GRAY);
                                    }
                                }
                            });
                        }

                        if (adminNameTextField.getText().equals("")) {
                            adminNameTextField.setBorder(new LineBorder(Color.red, 2));
                            saveButton.setBackground(Color.red);
                            adminNameTextField.addKeyListener(new KeyAdapter() {
                                public void keyPressed(KeyEvent ke) {
                                    if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535)) {
                                        adminNameTextField.setBorder(new LineBorder(Color.green, 2));
                                        saveButton.setBackground(Color.LIGHT_GRAY);
                                    }
                                }
                            });
                        }

                        if (adminTitleTextField.getText().equals("")) {
                            adminTitleTextField.setBorder(new LineBorder(Color.red, 2));
                            saveButton.setBackground(Color.red);
                            adminTitleTextField.addKeyListener(new KeyAdapter() {
                                public void keyPressed(KeyEvent ke) {
                                    if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535)) {
                                        adminTitleTextField.setBorder(new LineBorder(Color.green, 2));
                                        saveButton.setBackground(Color.LIGHT_GRAY);
                                    }
                                }
                            });
                        } else {
                            Admin admin = new Admin(
                                    Integer.parseInt(adminIdTextField.getText()),
                                    usernameTextField.getText(),
                                    adminPasswordTextField.getText(),
                                    adminNameTextField.getText(),
                                    adminTitleTextField.getText());

                            databasemanager.updateadmin(admin);
                            updateAdminDialog.setVisible(false);
                            loadadmin();
                        }
                    }
                });

                JButton cancelButton = new JButton("Cancel");
                cancelButton.setBackground(Color.LIGHT_GRAY);
                cancelButton.setFont(saveButton.getFont().deriveFont(20.0f));
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        updateAdminDialog.setVisible(false);
                        JFrame menu = Menu.init();
                        menu.setVisible(false);
                    }
                });

                updateAdminDialog.add(adminIdLabel);
                updateAdminDialog.add(adminIdTextField);
                updateAdminDialog.add(usernameLabel);
                updateAdminDialog.add(usernameTextField);
                updateAdminDialog.add(adminPasswordLabel);
                updateAdminDialog.add(adminPasswordTextField);
                updateAdminDialog.add(adminNameLabel);
                updateAdminDialog.add(adminNameTextField);
                updateAdminDialog.add(adminTitleLabel);
                updateAdminDialog.add(adminTitleTextField);
                updateAdminDialog.add(saveButton);
                updateAdminDialog.add(cancelButton);
            }
        });

        deleteadmin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                try ( Connection conn = Sqlite.connect()) {
                    String querry = "delete from admin where userid='" + data[selectedRow][0] + "'";
                    PreparedStatement pst = conn.prepareStatement(querry);
                    pst.execute();
                    pst.close();
                    loadadmin();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        controlPanel.add(addnewadmin);
        controlPanel.add(updateadmin);
        controlPanel.add(deleteadmin);
        loadadmin();
        return mainPanel;
    }

    public void loadadmin() {
        try ( Connection conn = Sqlite.connect()) {
            Statement stmt = conn.createStatement();
            ResultSet rs1 = stmt.executeQuery("SELECT * FROM admin");
            int rowCount = 0;
            int colCount = 5;

            while (rs1.next()) {
                rowCount++;
            }

            ResultSet rs2 = stmt.executeQuery("SELECT * FROM admin");
            data = new String[rowCount][colCount];

            int rowNum = 0;
            while (rs2.next()) {
                data[rowNum][0] = rs2.getString("userid");
                data[rowNum][1] = rs2.getString("username");
                data[rowNum][2] = rs2.getString("password");
                data[rowNum][3] = rs2.getString("name");
                data[rowNum][4] = rs2.getString("title");
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

    public void onAddAdmin() {
        loadadmin();
    }

    public void onUpdateAdmin() {
        loadadmin();
    }

    interface AddAdminListener {

        public void onAddAdmin();
    }
}
