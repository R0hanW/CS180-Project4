package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import backend.ProgramManager;
public class MainFrame {
    private static MainFrame instance = null;
    JPanel panels;
    JFrame frame;
    JPanel panel, loginPanel, signUpPanel, mainPanel, newCoursePanel, coursePanel;
    Box box;
    Dimension dimension;
    ProgramManager manager;
    
    public MainFrame() {
        instance = this;
        initComponents();
        frame.setVisible(true);
        try {
            manager = new ProgramManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized MainFrame get() {
        if(instance == null) {
            instance = new MainFrame();
        }
        return instance;
    }

    private void initComponents() {
        frame = new JFrame();
        frame.setTitle("Discussion Board");
        frame.setSize(500, 450);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLocationRelativeTo(null); 
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    ProgramManager.get().writeFile();
                } catch(Exception e1) {
                    e1.printStackTrace();
                }
                System.exit(0);
            }
        });
        box = new Box(BoxLayout.Y_AXIS);
        panel = new JPanel();
        dimension = new Dimension(500, 175);
        panels = new JPanel(new CardLayout());
        addComponentsToPanel();
        frame.add(box);
    }

    public void addComponentsToPanel() {
        panel = new LoginPanel();
        panel.setPreferredSize(dimension);
        panel.setMaximumSize(dimension);
        panel.setMinimumSize(dimension);
        box.add(Box.createVerticalGlue());
        box.add(panel);
        box.add(Box.createVerticalGlue());
    }

    public void switchPanel(String panelName) {
        if(panelName.equals("Login")) panel = new LoginPanel();
        else if(panelName.equals("Sign Up")) panel = new SignUpPanel();
        else if(panelName.equals("Main")) panel = new MainPanel();
        else if(panelName.equals("New Course")) panel = new NewCoursePanel();
        else if(panelName.equals("Course")) panel = new CoursePanel();
        if(panel.getPreferredSize().getHeight() > 500) {
            frame.getContentPane().removeAll();
            frame.getContentPane().add(panel);
            frame.getContentPane().validate();
            frame.repaint();
            return;
        }
        panel.setPreferredSize(dimension);
        panel.setMaximumSize(dimension);
        panel.setMinimumSize(dimension);
        box = new Box(BoxLayout.Y_AXIS);
        box.add(Box.createVerticalGlue());
        box.add(panel);
        box.add(Box.createVerticalGlue());
        frame.getContentPane().removeAll();
        frame.getContentPane().add(box);
        frame.getContentPane().validate();
        frame.repaint();
    }
}