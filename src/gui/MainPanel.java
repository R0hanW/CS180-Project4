package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import backend.*;

public class MainPanel extends JPanel implements ActionListener {
    JPanel coursePanel, tmpPanel, titlePanel, panel;
    JLabel courseFrameTitle, courseNameText, courseAuthorText, currentUserText;
    JTextArea courseText;
    JButton viewCourseButton, createCourseButton;
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

    private void initComponents() {
        // create components for top menu bar
        titlePanel = new JPanel(new BorderLayout());
        courseFrameTitle = new JLabel("COURSES");
        createCourseButton = new JButton("Create Course");
        createCourseButton.setPreferredSize(new Dimension(150, 20));
        if (!manager.getCurrUser().isTeacher())
            createCourseButton.setVisible(false);
        ArrayList<Course> courses = manager.getCourses();
        coursePanel = new JPanel(new GridLayout(courses.size(), 1));
        // create Panel for each course
        panel = new JPanel(new BorderLayout());
        for (Course course : courses) {
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
        addComponentsToContainer();
        add(panel, BorderLayout.CENTER);
        addActionListeners();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createCourseButton)
            MainFrame.get().switchPanel("New Course");
    }

    private void addActionListeners() {
        createCourseButton.addActionListener(this);
    }

    public void addComponentsToContainer() {
        if (manager.getCurrUser().isTeacher())
            titlePanel.add(new Box.Filler(new Dimension(150, 20), new Dimension(150, 20), new Dimension(150, 20)),
                    BorderLayout.WEST);
        courseFrameTitle.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(courseFrameTitle, BorderLayout.CENTER);
        createCourseButton.setHorizontalAlignment(SwingConstants.RIGHT);
        try {
            if (ProgramManager.get().getCurrUser().isTeacher())
                titlePanel.add(createCourseButton, BorderLayout.EAST);
        } catch (Exception e) {
            e.printStackTrace();
        }
        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(coursePanel, BorderLayout.CENTER);

    }

}
