package gui;

import javax.swing.*;

import backend.Course;
import backend.ProgramManager;

import java.awt.*;
import java.awt.event.*;

public class NewPostPanel extends JPanel implements ActionListener{
    JLabel topicLabel, descriptionLabel, pollLabel;
    JTextField topicText, userText;
    JTextArea descriptionText;
    JButton submitButton;
    JRadioButton pollButton;
    JPanel panel, tmpPanel, pollPanel;
    ProgramManager manager;
    
    public NewPostPanel() {
        try {
            manager = ProgramManager.get();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        initComponents();
        setPreferredSize(new Dimension(500, 150));
    } 

    private void initComponents() {
        topicLabel = new JLabel("Course Topic:");
        topicText = new JTextField();
        descriptionLabel = new JLabel("Post Description:");
        descriptionText = new JTextArea(5, 20);
        pollLabel = new JLabel("Create Poll?");
        pollButton = new JRadioButton("Yes");
        panel = new JPanel(new GridLayout(3, 1));
        submitButton = new JButton("Submit");
        addActionListeners();
        add(new MenuBar(), BorderLayout.NORTH);
        addComponentsToContainer();
        add(panel, BorderLayout.CENTER);
    }

    private void addActionListeners(){
        submitButton.addActionListener(this);
        pollButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == submitButton) {
            //TODO
        } else if(e.getSource() == pollButton) {
            //TODO
        }
    }

    private void addComponentsToContainer() {
        tmpPanel = new JPanel(new GridLayout(1, 2));
        tmpPanel.add(topicLabel);
        tmpPanel.add(topicText);
        panel.add(tmpPanel);
        tmpPanel = new JPanel(new GridLayout(1, 2));
        tmpPanel.add(descriptionLabel);
        tmpPanel.add(descriptionText);
        panel.add(tmpPanel);
        panel.add(submitButton);
    }
}   

