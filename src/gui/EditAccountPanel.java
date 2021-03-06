/***
 * This class which is part of the frontend, helps in setting up the GUI panels for editing Accounts
 *
 * @author Team 043
 * @version 12/13/2021
 *
 */
package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Arrays;

import backend.*;

public class EditAccountPanel extends JPanel implements ActionListener {
    JLabel nameLabel, userLabel, passwordLabel, confirmPasswordLabel, teacherLabel;
    JTextField nameText, userText;
    JPasswordField passwordText, confirmPasswordText;
    JButton signUpButton;
    JRadioButton teacherButton;
    JPanel panel, tmpPanel;
    ProgramManager manager;

    public EditAccountPanel() {
        try {
            manager = ProgramManager.get();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        initComponents();
        setPreferredSize(new Dimension(350, 300));
    }

    private void initComponents() {
        nameLabel = new JLabel("Name:");
        userLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        confirmPasswordLabel = new JLabel("Confirm Password:");
        teacherLabel = new JLabel("Are you a teacher?");
        nameText = new JTextField(manager.getCurrUser().getName());
        userText = new JTextField(manager.getCurrUser().getUsername());
        passwordText = new JPasswordField();
        confirmPasswordText = new JPasswordField();
        signUpButton = new JButton("Sign Up");
        teacherButton = new JRadioButton("Yes");
        panel = new JPanel(new GridLayout(6, 1));
        addActionListeners();
        addComponentsToContainer();
        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signUpButton) {
            try {
                manager = ProgramManager.get();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (!Arrays.equals(passwordText.getPassword(), confirmPasswordText.getPassword())) {
                JOptionPane.showMessageDialog(null, "Passwords must match!", "Edit Account",
                        JOptionPane.INFORMATION_MESSAGE);
            } else if (manager.findUser(userText.getText()) != null && userText.getText() !=
                    manager.getCurrUser().getUsername()) {
                JOptionPane.showMessageDialog(null, "Username is already taken!",
                        "Edit Account",
                        JOptionPane.INFORMATION_MESSAGE);
            } else if (nameText.getText().equals("") || userText.getText().equals("")
                    || String.valueOf(passwordText.getPassword()).equals("")) {
                JOptionPane.showMessageDialog(null, "All fields must have a value!",
                        "Edit Account",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                try {
                    manager = ProgramManager.get();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                Network network = new Network();
                try {
                    manager = ProgramManager.get();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                User user = manager.getCurrUser();
                try {
                    manager.modifyUser(user, nameText.getText(), userText.getText(),
                            String.valueOf(passwordText.getPassword()), teacherButton.isSelected());
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                //   manager.addUser(new User(nameText.getText(), userText.getText(),
                //           String.valueOf(passwordText.getPassword()), teacherButton.isSelected()));
                MainFrame.get().switchPanel("Main");
            }
        }
    }

    public void addActionListeners() {
        signUpButton.addActionListener(this);
    }

    public void addComponentsToContainer() {
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
