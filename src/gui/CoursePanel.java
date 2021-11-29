package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import backend.ProgramManager;
public class CoursePanel extends JPanel implements ActionListener{
    JPanel postPanel, tmpPanel, titlePanel, panel;
    JMenuBar menuBar;
    JMenu userMenu;
    JMenuItem profileItem, logOutItem;
    JScrollPane scrollPane;

    ProgramManager manager;
    public CoursePanel() {
        try {
            manager = ProgramManager.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        setSize(350, 750);
    }

    private void initComponents(){

    }

    @Override 
    public void actionPerformed(ActionEvent e) {

    }

    private void addActionListeners() {

    }

    public void addComponentsToContainer() {

    }
}
