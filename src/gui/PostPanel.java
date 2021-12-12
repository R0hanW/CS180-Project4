package gui;

import javax.swing.*;
import javax.swing.border.*;

import backend.Comment;
import backend.Poll;
import backend.Post;
import backend.ProgramManager;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
public class PostPanel extends JPanel implements ActionListener{
    
    ProgramManager manager;
    JPanel panel, titlePanel, commentPanel, tmpCommentPanel, tmpPanel, tmpReplyPanel, southButtons, pollPanel;
    JScrollPane descriptionTextPane, commentTextPane, replyTextPane, commentScrollPane;
    JLabel postFrameTitle, commentAuthorTitle, commentReplyTitle;
    JTextArea descriptionTextField, commentText, replyText;
    JToggleButton likeButton, replyLikeButton;
    EmptyBorder replyBorder;
    JButton newCommentButton, replyCommentButton;
    ArrayList<JToggleButton> likeButtons = new ArrayList<JToggleButton>();

    public PostPanel() {
        setLayout(new BorderLayout());
        try {
            manager = ProgramManager.get();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        initComponents();
        setPreferredSize(new Dimension(350, 750));
    }

    private void initComponents() {
        Post post = manager.getCurrPost();
        titlePanel = new JPanel(new BorderLayout());
        postFrameTitle = new JLabel(post.getTopic());
        descriptionTextField = new JTextArea(2, 20);
        descriptionTextField.setText(post.getContent());
        descriptionTextField.setLineWrap(true);
        descriptionTextField.setWrapStyleWord(true);
        descriptionTextField.setOpaque(false);
        descriptionTextField.setEditable(false);
        descriptionTextPane = new JScrollPane(descriptionTextField);
        
        if(post.getPoll() != null) {
            Poll poll = post.getPoll();
            JPanel postPanel = new JPanel(new GridLayout());
        }
        //find number of panels needed
        int total = post.getComments().size();
        for(Comment comment: post.getComments()) total += comment.getReplies().size();
        commentPanel = new JPanel(new GridLayout(total, 1));
        panel = new JPanel(new BorderLayout());
        post.getComments().stream().forEach(x -> System.out.print(x +", "));
        for(Comment comment: post.getComments()) {
            tmpCommentPanel = new JPanel(new GridLayout(3, 1));
            tmpCommentPanel.setMaximumSize(new Dimension(350, 30));
            tmpCommentPanel.setMinimumSize(new Dimension(350, 30));
            tmpCommentPanel.setPreferredSize(new Dimension(350, 30));
            commentAuthorTitle = new JLabel(comment.getOwner().getUsername() + "    " + comment.getTimestamp());
            Font f = commentAuthorTitle.getFont();
            commentAuthorTitle.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
            commentText = new JTextArea(1, 20);
            commentText.setLineWrap(true);
            commentText.setWrapStyleWord(true);
            commentText.setEditable(false);
            commentText.setOpaque(false);
            commentText.setText(comment.getContent());
            commentTextPane = new JScrollPane(commentText);
            likeButton = new JToggleButton(String.valueOf(comment.getVotes()), new ImageIcon("src/gui/icons/like.png"));
            likeButton.setOpaque(true);
            likeButton.setContentAreaFilled(false);
            likeButton.setBorderPainted(false);
            likeButton.setFocusable(true);
            likeButtons.add(likeButton);
            likeButton.addActionListener(new ActionListener(){
                @Override 
                public void actionPerformed(ActionEvent e) {
                    if(likeButtons.contains((e.getSource()))) {
                        likeButton = (JToggleButton) e.getSource();
                        if(likeButton.isSelected()) {
                            likeButton.setIcon(new ImageIcon("src/gui/icons/likeFilled.png"));
                            comment.addVote();
                            try {
								manager.writeCourseFile();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
                            likeButton.setText(Integer.toString(comment.getVotes()));
                        } else {
                            likeButton.setIcon(new ImageIcon("src/gui/icons/like.png"));
                            comment.removeVote();
                            try {
								manager.writeCourseFile();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
                            likeButton.setText(Integer.toString(comment.getVotes()));
                        }
                    }
                }
            });
            likeButton.setAlignmentX(JButton.LEFT_ALIGNMENT);
            likeButton.setAlignmentY(JButton.TOP_ALIGNMENT);
            replyCommentButton = new JButton("Reply");
            replyCommentButton.setSize(new Dimension(5, 15));
            replyCommentButton.setAlignmentX(JButton.LEFT_ALIGNMENT);
            replyCommentButton.setAlignmentY(JButton.TOP_ALIGNMENT);

            replyCommentButton.addActionListener(new ActionListener() {
                @Override 
                public void actionPerformed(ActionEvent e) {
                    try {
                        ProgramManager.get().setCurrComment(comment);
                        manager.writeCourseFile();
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    MainFrame.get().switchPanel("New Comment", true);
                }
            });
            tmpCommentPanel.add(commentAuthorTitle);
            tmpCommentPanel.add(commentText);
            southButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
            southButtons.add(likeButton);
            southButtons.add(replyCommentButton);
            tmpCommentPanel.add(southButtons);
            //add reply jpanel
            for(Comment reply: comment.getReplies()) {
                tmpReplyPanel = new JPanel(new BorderLayout());
                Border border = new EmptyBorder(0, 10, 0, 0);
                tmpReplyPanel.setBorder(border);
                commentReplyTitle = new JLabel("    " + reply.getOwner().getUsername() + comment.getTimestamp());
                f = commentReplyTitle.getFont();
                commentReplyTitle.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
                replyText = new JTextArea(1, 20);
                replyText.setLineWrap(true);
                replyText.setWrapStyleWord(true);
                replyText.setEditable(false);
                // replyText.setMargin(new Insets(0, 5, 0, 0));
                replyTextPane = new JScrollPane(replyText);
                replyLikeButton = new JToggleButton(String.valueOf(comment.getVotes()), new ImageIcon("src/gui/icons/like.png"));
                replyLikeButton.setOpaque(true);
                replyLikeButton.setContentAreaFilled(false);
                replyLikeButton.setBorderPainted(false);
                replyLikeButton.setFocusable(true);
                likeButtons.add(replyLikeButton);
                replyLikeButton.addActionListener(new ActionListener(){
                    @Override 
                    public void actionPerformed(ActionEvent e) {
                        if(likeButtons.contains((e.getSource()))) {
                            likeButton = (JToggleButton) e.getSource();
                            if(likeButton.isSelected()) {
                                likeButton.setIcon(new ImageIcon("src/gui/icons/likeFilled.png"));
                                comment.addVote();
                                try {
									manager.writeCourseFile();
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
                                likeButton.setText(Integer.toString(comment.getVotes()));
                            } else {
                                likeButton.setIcon(new ImageIcon("src/gui/icons/like.png"));
                                comment.removeVote();
                                likeButton.setText(Integer.toString(comment.getVotes()));
                            }
                        }
                    }
                });
                tmpReplyPanel.add(commentReplyTitle, BorderLayout.NORTH);
                tmpReplyPanel.add(replyText, BorderLayout.CENTER);
                tmpReplyPanel.add(replyLikeButton, BorderLayout.SOUTH);
                commentPanel.add(tmpReplyPanel);
            }
            commentPanel.add(tmpCommentPanel);
        }
        newCommentButton = new JButton("Add Comment");
        newCommentButton.setPreferredSize(new Dimension(100, 20));
        addComponentsToContainer();
        addActionListeners();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == newCommentButton) {
            MainFrame.get().switchPanel("New Comment", false);
        }
        
    }

    private void addActionListeners() {
        newCommentButton.addActionListener(this);
    }

    public void addComponentsToContainer() {
        titlePanel.add(new Box.Filler(new Dimension(100, 20), new Dimension(100, 20), new Dimension(100, 20)),
                    BorderLayout.WEST);
        postFrameTitle.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(postFrameTitle, BorderLayout.CENTER);
        newCommentButton.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(newCommentButton, BorderLayout.EAST);
        titlePanel.add(descriptionTextPane, BorderLayout.SOUTH);
        panel.add(titlePanel, BorderLayout.NORTH);
        JScrollPane commentScrollPane = new JScrollPane(commentPanel);
        panel.add(commentScrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.CENTER);
    }
}
