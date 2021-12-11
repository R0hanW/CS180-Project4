package gui;

import javax.swing.*;

import backend.*;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;

public class ImportCommentPanel extends JPanel implements ActionListener {
    JLabel nameLabel, createCoursePermissionsLabel;
    JTextField nameText;
    JButton submitButton;
    JRadioButton coursePermissionsButton;
    JPanel panel, tmpPanel;
    ProgramManager manager;

    public ImportCommentPanel() {
        try {
            manager = ProgramManager.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        setPreferredSize(new Dimension(500, 150));
    }

    private void initComponents() {
        nameLabel = new JLabel("File Name:");
        nameText = new JTextField();
        submitButton = new JButton("Submit");
        panel = new JPanel(new GridLayout(2, 1));
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
            // Network network = new Network();
            // network.getCurrCourse().addPost((nameText.getText(), manager.getCurrUser(), coursePermissionsButton.isSelected());
            try {
                manager.readUserFileImport(nameText.getText(), false, manager.getCurrCourse(), manager.getCurrPost(), manager.getCurrUser());
                MainFrame.get().switchPanel("Post");
            } catch (FileNotFoundException e1) {
                JOptionPane.showMessageDialog(null, "Could Not Find File!", "Import Post", JOptionPane.ERROR_MESSAGE);
            }
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
        panel.add(tmpPanel);
        panel.add(submitButton);
    }
}