package gui;

import javax.swing.*;

import backend.Course;
import backend.ProgramManager;

import java.awt.*;
import java.awt.event.*;

public class NewCoursePanel extends JPanel implements ActionListener {
    JLabel nameLabel, createCoursePermissionsLabel;
    JTextField nameText;
    JButton submitButton;
    JRadioButton coursePermissionsButton;
    JPanel panel, tmpPanel;
    ProgramManager manager;

    public NewCoursePanel() {
        try {
            manager = ProgramManager.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        setPreferredSize(new Dimension(500, 150));
    }

    private void initComponents() {
        nameLabel = new JLabel("Course Name:");
        nameText = new JTextField();
        createCoursePermissionsLabel = new JLabel("Allow students to create posts?");
        coursePermissionsButton = new JRadioButton("Yes");
        submitButton = new JButton("Submit");
        panel = new JPanel(new GridLayout(3, 1));
        addActionListeners();
        addComponentsToContainer();
        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            try {
                manager = ProgramManager.get();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            if (manager.findCourse(nameText.getText()) != null) {
                JOptionPane.showMessageDialog(null, "Course Name is already taken!", "New Course",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            manager.addCourse(
                    new Course(nameText.getText(), manager.getCurrUser(), coursePermissionsButton.isSelected()));
            MainFrame.get().switchPanel("Main");
        }
    }

    public void addActionListeners() {
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
