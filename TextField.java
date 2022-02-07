package de.course.management;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class TextField extends JTextField {

    private String placeholder;

    public TextField(String placeholder) {
        this.placeholder = placeholder;
//        this.setBackground(Color.cyan);
//        this.setText(placeholder);
        Font fieldFont = new Font("Arial", Font.PLAIN, 15);
        this.setFont(fieldFont);
//        this.setBackground(Color.LIGHT_GRAY);
        this.setForeground(Color.DARK_GRAY.brighter());
        this.setColumns(30);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (TextField.this.getText().length() >= 15) {
                    e.consume();
                }
            }
        });
//
//      
//    this.addFocusListener( new FocusListener() {
//            @Override
//        public void focusGained
//        (FocusEvent arg0
//        
//            ) {
//                if (placeholder.equals(TextField.this.getText())) {
//                TextField.this.setText("");
//            }
//        }
//        @Override
//        public void focusLost
//        (FocusEvent arg0
//        
//            ) {
//                if ("".equals(TextField.this.getText())) {
//                TextField.this.setText(placeholder);
//            }
//        }
//    }
//);
}
}



//          teacherIdTextField.addFocusListener( new FocusListener() {
//
//                              
//                                public void focusGained(FocusEvent e) {
//                                    e.getComponent().setBackground(Color.GREEN);
//                                }
//
//                                
//                                public void focusLost(FocusEvent e) {
//                                    e.getComponent().setBackground(UIManager.getColor("TextField.background"));
//                                }
//                            });