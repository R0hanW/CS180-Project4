package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

import backend.ProgramManager;

public class MainFrame {
    private static MainFrame instance = null;
    JScrollPane scrollPane;
    String panelName = "Login";
    JPanel panels;
    JFrame frame;
    JPanel panel, loginPanel, signUpPanel, mainPanel, newCoursePanel, coursePanel;
    ArrayList<String> prevPanel = new ArrayList<String>(Arrays.asList("Login"));
    String nextPanel;
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
        if (instance == null) {
            instance = new MainFrame();
        }
        return instance;
    }

    private void initComponents() {
        frame = new JFrame();
        frame.setTitle("Discussion Board");
        frame.setSize(500, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    ProgramManager.get().writeFile();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        box = new Box(BoxLayout.Y_AXIS);
        panel = new JPanel();
        dimension = new Dimension(500, 300);
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
    	this.panelName = panelName;
        if (panelName.equals("Login")) {
            panel = new LoginPanel();
        } else if (panelName.equals("Sign Up"))
            panel = new SignUpPanel();
        else if (panelName.equals("Main"))
            panel = new MainPanel();
        else if (panelName.equals("New Course"))
            panel = new NewCoursePanel();
        else if (panelName.equals("Course"))
            panel = new CoursePanel();
        else if (panelName.equals("Post"))
            panel = new PostPanel();
        else if (panelName.equals("New Comment"))
            panel = new NewCommentPanel(false);
        else if (panelName.equals("New Post"))
            panel = new NewPostPanel();
        else if (panelName.equals("Previous")) {
            nextPanel = prevPanel.get(prevPanel.size() - 1);
            switchPanel(prevPanel.get(prevPanel.size() - 2));
        } else if (panelName.equals("Next")) {
            if (nextPanel != null)
                switchPanel(nextPanel);
            else
                return;
        }
        if (!panelName.equals("Previous") && !panelName.equals("Next"))
            prevPanel.add(panelName);
        if (!panelName.equals("Login") && !panelName.equals("Sign Up"))
            frame.setJMenuBar(new MenuBar());
        if (panel.getPreferredSize().getHeight() > 500) {
            frame.getContentPane().removeAll();
            frame.getContentPane().add(panel);
            frame.revalidate();
            frame.getContentPane().validate();
            frame.repaint();
            return;
        }
        panel.setPreferredSize(dimension);
        panel.setMaximumSize(dimension);
        panel.setMinimumSize(dimension);
        box = new Box(BoxLayout.Y_AXIS);
        if (panelName.equals("Login"))
            box.add(Box.createVerticalGlue());
        box.add(panel);
        box.add(Box.createVerticalGlue());
        frame.getContentPane().removeAll();
        frame.getContentPane().add(box);
        frame.revalidate();
        frame.getContentPane().validate();
        frame.repaint();
    }

    public void switchPanel(String panelName, boolean reply) {
    	this.panelName = panelName;
        if (panelName.equals("Login")) {
            panel = new LoginPanel();
        } else if (panelName.equals("Sign Up"))
            panel = new SignUpPanel();
        else if (panelName.equals("Main"))
            panel = new MainPanel();
        else if (panelName.equals("New Course"))
            panel = new NewCoursePanel();
        else if (panelName.equals("Course"))
            panel = new CoursePanel();
        else if (panelName.equals("Post"))
            panel = new PostPanel();
        else if (panelName.equals("New Comment"))
            panel = new NewCommentPanel(reply);
        else if (panelName.equals("New Post"))
            panel = new NewPostPanel();
        else if (panelName.equals("Previous")) {
            nextPanel = prevPanel.get(prevPanel.size() - 1);
            switchPanel(prevPanel.get(prevPanel.size() - 2));
        } else if (panelName.equals("Next")) {
            if (nextPanel != null)
                switchPanel(nextPanel);
            else
                return;
        }
        if (!panelName.equals("Previous") && !panelName.equals("Next"))
            prevPanel.add(panelName);
        if (!panelName.equals("Login") && !panelName.equals("Sign Up"))
            frame.setJMenuBar(new MenuBar());
        if (panel.getPreferredSize().getHeight() > 500) {
            frame.getContentPane().removeAll();
            frame.getContentPane().add(panel);
            frame.revalidate();
            frame.getContentPane().validate();
            frame.repaint();
            return;
        }
        panel.setPreferredSize(dimension);
        panel.setMaximumSize(dimension);
        panel.setMinimumSize(dimension);
        box = new Box(BoxLayout.Y_AXIS);
        if (panelName.equals("Login"))
            box.add(Box.createVerticalGlue());
        box.add(panel);
        box.add(Box.createVerticalGlue());
        frame.getContentPane().removeAll();
        frame.getContentPane().add(box);
        frame.revalidate();
        frame.getContentPane().validate();
        frame.repaint();
    }
    
    public String getPanelName() {
    	return panelName;
    }
    
    public void update() {
    	panel.revalidate();
    }
}