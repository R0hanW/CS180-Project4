/***
 * This class which is part of the frontend, helps in setting up GUI panels for profile
 *
 * @author Team 043
 * @version 12/13/2021
 *
 */
package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import backend.*;

public class ProfilePanel extends JPanel implements ActionListener{

    JLabel nameLabel, userLabel, nameText, userText, teacherLabel, teacherText;
    JPanel panel, tmpPanel;
    ProgramManager manager;


    public ProfilePanel() {
        try {
            manager = ProgramManager.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        setPreferredSize(new Dimension(350, 300));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }

    public void initComponents() {
        nameLabel = new JLabel("Name:");
        userLabel = new JLabel("Username:");
        teacherLabel = new JLabel("Teacher");
        nameText = new JLabel(manager.getCurrUser().getName());
        userText = new JLabel(manager.getCurrUser().getUsername());
        if (manager.getCurrUser().isTeacher()){
            teacherText = new JLabel("Yes");
        } else {
            teacherLabel = new JLabel("No");
        }
        panel = new JPanel(new GridLayout(6, 1));
        addActionListeners();
        addComponentsToContainer();
        add(panel, BorderLayout.CENTER);
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
        tmpPanel.add(teacherLabel);
        tmpPanel.add(teacherText);
        panel.add(tmpPanel);
    }

    private void addActionListeners() {

    }

}
