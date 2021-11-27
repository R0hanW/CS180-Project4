package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

import backend.*;

public class SignUpFrame extends JFrame implements ActionListener{
    JLabel nameLabel, userLabel, passwordLabel, confirmPasswordLabel, teacherLabel;
    JTextField nameText, userText;
    JPasswordField passwordText, confirmPasswordText;
    JButton signUpButton;
    JRadioButton teacherButton;
    JPanel panel, tmpPanel;
    ProgramManager manager; 

    public SignUpFrame() {
        initComponents();
        setSize(350, 300);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        try {
            manager = ProgramManager.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                try {
                    manager.writeFile();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                System.exit(0);
            }
        });
    }

    private void initComponents(){
        nameLabel = new JLabel("Name:");
        userLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        confirmPasswordLabel = new JLabel("Confirm Password:");
        teacherLabel = new JLabel("Are you a teacher?");
        nameText = new JTextField();
        userText = new JTextField();
        passwordText = new JPasswordField();
        confirmPasswordText = new JPasswordField();
        signUpButton = new JButton("Sign Up");
        teacherButton = new JRadioButton("Yes");
        panel = new JPanel(new GridLayout(6, 1));
        addActionListeners();
        addComponentsToContainer();
        add(panel, BorderLayout.CENTER);
        setTitle("Discussion Board");
    }   

    @Override 
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == signUpButton) {
            try {
                manager = ProgramManager.get();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if(!Arrays.equals(passwordText.getPassword(), confirmPasswordText.getPassword())) {
                JOptionPane.showMessageDialog(null, "Passwords must match!", "Sign Up", JOptionPane.INFORMATION_MESSAGE);
            } else if(manager.findUser(userText.getText()) != null) {
                JOptionPane.showMessageDialog(null, "Username is already taken!", "Sign Up", JOptionPane.INFORMATION_MESSAGE);
            } else if(nameText.getText().equals("") || userText.getText().equals("") || String.valueOf(passwordText.getPassword()).equals("")) {
                JOptionPane.showMessageDialog(null, "All fields must have a value!", "Sign Up", JOptionPane.INFORMATION_MESSAGE);
            } else {
                try {
                    manager = ProgramManager.get();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                manager.addUser(new User(nameText.getText(), userText.getText(), String.valueOf(passwordText.getPassword()), teacherButton.isSelected()));
                JFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true); 
                this.dispose();
            }
        }
    }

    public void addActionListeners(){
        signUpButton.addActionListener(this);
    }

    public void addComponentsToContainer(){
        tmpPanel = new JPanel(new GridLayout(1, 2));
        tmpPanel.add(nameLabel);
        tmpPanel.add(nameText);
        panel.add(tmpPanel);
        tmpPanel = new JPanel(new GridLayout(1, 2));
        tmpPanel.add(userLabel);
        tmpPanel.add(userText);
        panel.add(tmpPanel);
        tmpPanel = new JPanel(new GridLayout(1, 2));
        tmpPanel.add(passwordLabel);
        tmpPanel.add(passwordText);
        panel.add(tmpPanel);
        tmpPanel = new JPanel(new GridLayout(1, 2));
        tmpPanel.add(confirmPasswordLabel);
        tmpPanel.add(confirmPasswordText);
        panel.add(tmpPanel);
        tmpPanel = new JPanel(new GridLayout(1, 2));
        tmpPanel.add(teacherLabel);
        tmpPanel.add(teacherButton);
        panel.add(tmpPanel);
        panel.add(signUpButton);
    }
}
