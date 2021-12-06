package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import backend.*;

public class LoginPanel extends JPanel implements ActionListener {
    JLabel userLabel, passwordLabel;
    JTextField userText;
    JPasswordField passwordText;
    JButton loginButton, signUpButton;
    JPanel panel;
    ProgramManager manager;

    public LoginPanel() {
        initComponents();
        setPreferredSize(new Dimension(300, 150));
        try {
            manager = ProgramManager.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        // create login JFrame
        userLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");
        userText = new JTextField();
        passwordText = new JPasswordField();
        loginButton = new JButton("Login");
        signUpButton = new JButton("Sign Up");
        panel = new JPanel(new GridLayout(3, 2));
        addComponentsToContainer();
        addActionListeners();
        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            try {
                manager = ProgramManager.get();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            // check if username exists
            User user = manager.findUser(userText.getText());
            if (user == null) {
                JOptionPane.showMessageDialog(null, "Username does not exist! Click sign up to create an account!",
                        "Login",
                        JOptionPane.INFORMATION_MESSAGE);
            } else if (!user.getPassword().equals(String.valueOf(passwordText.getPassword()))) {
                JOptionPane.showMessageDialog(null, "Incorrect Password!", "Login", JOptionPane.INFORMATION_MESSAGE);
            } else {
                manager.setCurrUser(user);
                MainFrame.get().switchPanel("Main");
            }
        } else if (e.getSource() == signUpButton) {
            MainFrame.get().switchPanel("Sign Up");
        }
    }

    public void addActionListeners() {
        signUpButton.addActionListener(this);
        loginButton.addActionListener(this);
    }

    public void addComponentsToContainer() {
        panel.add(userLabel);
        panel.add(userText);
        panel.add(passwordLabel);
        panel.add(passwordText);
        panel.add(signUpButton);
        panel.add(loginButton);
    }
}