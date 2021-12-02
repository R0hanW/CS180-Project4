package gui;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import backend.Post;
import backend.ProgramManager;
public class CoursePanel extends JPanel implements ActionListener{
    JPanel postPanel, tmpPanel, tmpTitlePanel, tmpContentPanel, titlePanel, panel;
    JScrollPane scrollPane;
    JTextArea contentText;
    JLabel courseTitle, postNameText,postFrameTitle, timeStampText;
    JButton newPostButton;
    TitledBorder border;
    MouseListener mouseListener;
    ProgramManager manager;
    public CoursePanel() {
        setLayout(new BorderLayout());
        try {
            manager = ProgramManager.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initMouseListener();
        initComponents();
        setPreferredSize(new Dimension(350, 750));
    }

    private void initComponents(){
        titlePanel = new JPanel(new BorderLayout());
        postFrameTitle = new JLabel("Posts");
        newPostButton = new JButton("Create Post");
        newPostButton.setPreferredSize(new Dimension(100, 20));
        if(!manager.getCurrUser().isTeacher() && !manager.getCurrCourse().studentsCanCreatePosts()) newPostButton.setVisible(false);
        ArrayList<Post> posts = manager.getCurrCourse().getPosts();
        //create Panel for each post
        postPanel = new JPanel(new GridLayout(posts.size(), 1));
        panel = new JPanel();
        scrollPane = new JScrollPane();
        border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black));
        border.setTitleJustification(TitledBorder.CENTER);
        for(Post post: posts) {
            border.setTitle(String.format("%s (%s)", post.getTopic(), post.getTimestamp()));
            tmpPanel = new JPanel();
            tmpPanel.setBorder(border);
            contentText = new JTextArea(post.getContent().substring(0, 250));
            tmpPanel.add(contentText);
            tmpPanel.addMouseListener(mouseListener);
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
        if(e.getSource() == newPostButton) {
            //TODO
        }
    }

    private void addActionListeners() {
        newPostButton.addActionListener(this);
    }

    public void addComponentsToContainer() {
        // if(manager.getCurrUser().isTeacher()) 
        //     titlePanel.add(new Box.Filler(new Dimension(150, 20), new Dimension(150, 20), new Dimension(150, 20)), BorderLayout.WEST);
        postFrameTitle.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(postFrameTitle);
        newPostButton.setHorizontalAlignment(SwingConstants.RIGHT);
        try {
            manager = ProgramManager.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(manager.getCurrUser().isTeacher() || manager.getCurrCourse().studentsCanCreatePosts()) titlePanel.add(newPostButton);
        panel.add(scrollPane);
        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(postPanel, BorderLayout.CENTER);
    }

    public void initMouseListener() {
        mouseListener = new MouseListener (){
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {}

            @Override
            public void mouseExited(MouseEvent arg0) {}

            @Override
            public void mousePressed(MouseEvent arg0) {}

            @Override
            public void mouseReleased(MouseEvent arg0) {}

        };
    }
}
