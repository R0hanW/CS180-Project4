package gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import backend.Post;
import backend.ProgramManager;

public class CoursePanel extends JPanel implements ActionListener {
    JPanel postPanel, tmpPanel, tmpTitlePanel, tmpContentPanel, titlePanel, panel;
    JTextArea contentText;
    JScrollPane contentTextPane;
    JLabel postNameText, postFrameTitle, timeStampText;
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

    private void initComponents() {
        titlePanel = new JPanel(new BorderLayout());
        postFrameTitle = new JLabel("POSTS");
        newPostButton = new JButton("Create Post");
        newPostButton.setPreferredSize(new Dimension(120, 20));
        if (!manager.getCurrUser().isTeacher() && !manager.getCurrCourse().studentsCanCreatePosts())
            newPostButton.setVisible(false);
        ArrayList<Post> posts = manager.getCurrCourse().getPosts();
        // create Panel for each post
        postPanel = new JPanel(new GridLayout(posts.size(), 1));
        panel = new JPanel(new BorderLayout());
        border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black));
        border.setTitleJustification(TitledBorder.DEFAULT_POSITION);
        for (Post post : posts) {
            border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black));
            border.setTitleJustification(TitledBorder.DEFAULT_POSITION);
            border.setTitle(String.format("%s (%s)", post.getTopic(), post.getTimestamp()));
            tmpPanel = new JPanel(new BorderLayout());
            tmpPanel.setBorder(border);
            tmpPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            if (post.getContent().length() > 250)
                contentText = new JTextArea(post.getContent().substring(0, 250));
            else
                contentText = new JTextArea(post.getContent());
            contentText.setLineWrap(true);
            contentText.setWrapStyleWord(true);
            contentText.setOpaque(false);
            contentText.setEditable(false);
            contentTextPane = new JScrollPane(contentText);
            tmpPanel.add(contentTextPane, BorderLayout.CENTER);
            tmpPanel.addMouseListener(mouseListener);
            postPanel.add(tmpPanel);
        }
        addComponentsToContainer();
        add(panel, BorderLayout.CENTER);
        addActionListeners();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newPostButton) {
            MainFrame.get().switchPanel("New Post");
        }
    }

    private void addActionListeners() {
        newPostButton.addActionListener(this);
    }

    public void addComponentsToContainer() {
        if (manager.getCurrUser().isTeacher() || manager.getCurrCourse().studentsCanCreatePosts())
            titlePanel.add(new Box.Filler(new Dimension(100, 20), new Dimension(100, 20), new Dimension(100, 20)),
                    BorderLayout.WEST);
        postFrameTitle.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(postFrameTitle, BorderLayout.CENTER);
        newPostButton.setHorizontalAlignment(SwingConstants.RIGHT);
        if (manager.getCurrUser().isTeacher() || manager.getCurrCourse().studentsCanCreatePosts())
            titlePanel.add(newPostButton, BorderLayout.EAST);
        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(postPanel, BorderLayout.CENTER);
    }

    public void initMouseListener() {
        mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
            }

            @Override
            public void mousePressed(MouseEvent arg0) {
            }

            @Override
            public void mouseReleased(MouseEvent arg0) {
            }

        };
    }
}
