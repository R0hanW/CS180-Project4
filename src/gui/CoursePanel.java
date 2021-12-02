package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import backend.Post;
import backend.ProgramManager;
public class CoursePanel extends JPanel implements ActionListener{
    JPanel postPanel, tmpPanel, tmpTitlePanel, tmpContentPanel, titlePanel, panel;
    JMenuBar menuBar;
    JMenu userMenu;
    JMenuItem backItem, forwardItem, homeItem, profileItem, logOutItem, editCourseItem;
    JScrollPane scrollPane;
    JTextArea contentText;
    JLabel postNameText, timeStampText;
    JButton newPostButton;
    MouseListener mouseListener;
    ProgramManager manager;
    public CoursePanel() {
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
        //create menu bar at top
        menuBar = new JMenuBar();
        editCourseItem = new JMenuItem("Edit Course");
        if(manager.getCurrUser().isTeacher()) menuBar.add(editCourseItem); 
        menuBar.add(Box.createHorizontalGlue());
        userMenu = new JMenu(manager.getCurrUser().getName());
        profileItem = new JMenuItem("Profile");
        logOutItem = new JMenuItem("Log Out");
        userMenu.add(profileItem);
        userMenu.add(logOutItem);

        titlePanel = new JPanel(new BorderLayout());
        newPostButton = new JButton("Create Post");
        newPostButton.setPreferredSize(new Dimension(100, 20));
        if(!manager.getCurrUser().isTeacher() && !manager.getCurrCourse().studentsCanCreatePosts()) newPostButton.setVisible(false);
        ArrayList<Post> posts = manager.getCurrCourse().getPosts();
        //create Panel for each post
        postPanel = new JPanel(new GridLayout(posts.size(), 1));
        panel = new JPanel();
        scrollPane = new JScrollPane();
        for(Post post: posts) {
            tmpPanel = new JPanel(new BorderLayout());
            postNameText = new JLabel(post.getTopic());
            postNameText.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        ProgramManager.get().setCurrPost(post);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    MainFrame.get().switchPanel("Course");
                }
            });
            timeStampText = new JLabel(post.getTimestamp());
            tmpTitlePanel.add(postNameText);
            tmpTitlePanel.add(Box.createHorizontalGlue());
            tmpTitlePanel.add(timeStampText);
            tmpPanel.add(tmpTitlePanel, BorderLayout.NORTH);

            contentText = new JTextArea(post.getContent().substring(0, 250));
            tmpPanel.add(contentText, BorderLayout.CENTER);

            postPanel.add(tmpPanel);
        }
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(new MenuBar(), BorderLayout.NORTH);
        addComponentsToContainer();
        add(panel, BorderLayout.CENTER);
        addActionListeners();
    }

    @Override 
    public void actionPerformed(ActionEvent e) {

    }

    private void addActionListeners() {

    }

    public void addComponentsToContainer() {

    }
}
