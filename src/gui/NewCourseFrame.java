package gui;

import javax.swing.*;

import backend.Course;
import backend.ProgramManager;

import java.awt.*;
import java.awt.event.*;

public class NewCourseFrame extends JFrame implements ActionListener {
    JLabel nameLabel, createCoursePermissionsLabel;
    JTextField nameText;
    JButton submitButton;
    JRadioButton coursePermissionsButton;
    JPanel panel, tmpPanel;
    ProgramManager manager;
    public NewCourseFrame() {
        try {
            manager = ProgramManager.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(500, 150);
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

    private void initComponents() {
        nameLabel = new JLabel("Course Name:");
        nameText = new JTextField();
        createCoursePermissionsLabel = new JLabel("Allow students to create posts?");
        coursePermissionsButton = new JRadioButton("Yes");
        submitButton = new JButton("Submit");
        panel = new JPanel(new GridLayout(3 , 1));
        addActionListeners();
        addComponentsToContainer();
        add(panel, BorderLayout.CENTER);
        setTitle("Discussion Board");
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == submitButton) {
            manager.addCourse(new Course(nameText.getText(), manager.getCurrUser(), coursePermissionsButton.isSelected()));
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
            this.dispose();
        }
    }

    public void addActionListeners(){
        submitButton.addActionListener(this);
    }
    
    public void addComponentsToContainer() {
        tmpPanel = new JPanel(new GridLayout(1, 2));
        tmpPanel.add(nameLabel);
        tmpPanel.add(nameText);
        panel.add(tmpPanel);
        tmpPanel = new JPanel(new GridLayout(1, 2));
        tmpPanel.add(createCoursePermissionsLabel);
        tmpPanel.add(coursePermissionsButton);
        panel.add(tmpPanel);
        panel.add(submitButton);
    }
}
