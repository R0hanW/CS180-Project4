package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import backend.*;

public class MainFrame extends JFrame implements ActionListener {
    JPanel coursePanel, tmpPanel, titlePanel, panel;
    JMenuBar menuBar;
    JMenu courseMenu, userMenu;
    JMenuItem addCourseItem, profileItem, logOutItem;
    JScrollPane scrollPane;
    JLabel courseFrameTitle, courseNameText, courseAuthorText, currentUserText;
    JTextArea courseText;
    JButton invisButton, viewCourseButton, createCourseButton;
    MouseListener mouseListener;
    ProgramManager manager;
    public MainFrame() {
        try {
            manager = ProgramManager.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(350, 750);
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
        mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CourseFrame courseFrame = new CourseFrame();
                courseFrame.setVisible(true);
                dispose();
            }
        };
    }

    private void initComponents(){
        //create components for top menu bar
        menuBar = new JMenuBar();
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
            courseNameText.addMouseListener(mouseListener);
            courseAuthorText = new JLabel("Teacher:" + course.getOwner().getName());
            tmpPanel.add(courseNameText);
            tmpPanel.add(courseAuthorText);
            tmpPanel.setPreferredSize(new Dimension(350, 100));
            coursePanel.add(tmpPanel);
        }
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setJMenuBar(menuBar);
        addComponentsToContainer();
        add(panel, BorderLayout.CENTER);
        addActionListeners();
    }

    @Override 
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == profileItem) {
            // UserProfileFrame userProfile = new UserProfileFrame();
            // userProfile.setVisible(true);
            this.dispose(); 
        } else if(e.getSource() == logOutItem) {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
            this.dispose(); 
        } else if(e.getSource() == createCourseButton) {
            NewCourseFrame newCourseFrame = new NewCourseFrame();
            newCourseFrame.setVisible(true);
            this.dispose();
        }
    }

    private void addActionListeners() {
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
