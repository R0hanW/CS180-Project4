package gui;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import backend.*;

public class MainPanel extends JPanel implements ActionListener {
    JPanel coursePanel, tmpPanel, titlePanel, panel;
    JMenuBar menuBar;
    JMenu userMenu, backItem, forwardItem;
    JMenuItem profileItem, logOutItem;
    JScrollPane scrollPane;
    JLabel courseFrameTitle, courseNameText, courseAuthorText, currentUserText;
    JTextArea courseText;
    JButton invisButton, viewCourseButton, createCourseButton;
    MouseListener mouseListener;
    ProgramManager manager;
    public MainPanel() {
        setLayout(new BorderLayout());
        try {
            manager = ProgramManager.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        setPreferredSize(new Dimension(350, 750));
    }

    private void initComponents(){
        //create components for top menu bar
        menuBar = new JMenuBar();
        backItem = new JMenu();
        backItem.setIcon(new ImageIcon("icons/back.png"));
        backItem.setPopupMenuVisible(false);
        forwardItem = new JMenu();
        forwardItem.setIcon(new ImageIcon("icons/forward.png"));
        forwardItem.setPopupMenuVisible(false);
        menuBar.add(backItem);
        menuBar.add(forwardItem);
        menuBar.add(Box.createHorizontalGlue());
        userMenu = new JMenu(manager.getCurrUser().getName());
        profileItem = new JMenuItem("Profile");
        logOutItem = new JMenuItem("Log Out");
        userMenu.add(profileItem);
        userMenu.add(logOutItem);
        menuBar.add(userMenu);

        titlePanel = new JPanel(new BorderLayout());
        courseFrameTitle = new JLabel("COURSES");
        createCourseButton = new JButton("Create Course");
        createCourseButton.setPreferredSize(new Dimension(100, 20));
        if(!manager.getCurrUser().isTeacher()) createCourseButton.setVisible(false);
        ArrayList<Course> courses = manager.getCourses();
        coursePanel = new JPanel(new GridLayout(courses.size(), 1));
        //create Panel for each course
        panel = new JPanel(new BorderLayout());
        scrollPane = new JScrollPane();
        for(Course course: courses) {
            tmpPanel = new JPanel();
            courseNameText = new JLabel(course.getName());
            courseNameText.setForeground(Color.BLUE.darker());
            courseNameText.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            courseNameText.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        ProgramManager.get().setCurrCourse(course);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    MainFrame.get().switchPanel("Course");
                }
            });
            courseAuthorText = new JLabel("Teacher:" + course.getOwner().getName());
            tmpPanel.add(courseNameText);
            tmpPanel.add(courseAuthorText);
            tmpPanel.setPreferredSize(new Dimension(350, 100));
            coursePanel.add(tmpPanel);
        }
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(menuBar, BorderLayout.NORTH);
        addComponentsToContainer();
        add(panel, BorderLayout.CENTER);
        addActionListeners();
    }

    @Override 
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == profileItem) {
            //TODO
        } else if(e.getSource() == logOutItem) {
            MainFrame.get().switchPanel("Login");
        } else if(e.getSource() == createCourseButton) {
            MainFrame.get().switchPanel("New Course");
        }
    }

    private void addActionListeners() {
        backItem.addMenuListener(new MenuListener() {
            public void menuSelected(MenuEvent e) {
                MainFrame.get().switchPanel("Previous");
            }
            public void menuDeselected(MenuEvent e) {}
            public void menuCanceled(MenuEvent e) {}
        });
        forwardItem.addMenuListener(new MenuListener() {
            public void menuSelected(MenuEvent e) {
                MainFrame.get().switchPanel("Next");
            }
            public void menuDeselected(MenuEvent e) {}
            public void menuCanceled(MenuEvent e) {}
        });
        profileItem.addActionListener(this);
        logOutItem.addActionListener(this);
        createCourseButton.addActionListener(this);
    }

    public void addComponentsToContainer(){
        if(manager.getCurrUser().isTeacher()) 
            titlePanel.add(new Box.Filler(new Dimension(100, 20), new Dimension(100, 20), new Dimension(100, 20)), BorderLayout.WEST);
        courseFrameTitle.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(courseFrameTitle, BorderLayout.CENTER);
        createCourseButton.setHorizontalAlignment(SwingConstants.RIGHT);
        try {
            if(ProgramManager.get().getCurrUser().isTeacher()) titlePanel.add(createCourseButton, BorderLayout.EAST);
        } catch (Exception e) {
            e.printStackTrace();
        }
        panel.add(scrollPane);
        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(coursePanel, BorderLayout.CENTER);

    }

}
