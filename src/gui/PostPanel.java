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
    JPanel panel, titlePanel, commentPanel, tmpPanel, commentButtons, titleButtons;
    JScrollPane descriptionTextPane, commentTextPane, commentScrollPane;
    JLabel postFrameTitle, commentAuthorTitle;
    JTextArea descriptionTextField, commentText;
    JToggleButton likeButton;
    JButton newCommentButton, viewPollButton, replyCommentButton;
    EmptyBorder replyBorder;
    ArrayList<JToggleButton> likeButtons = new ArrayList<JToggleButton>();
    ArrayList<JButton> replyCommentButtons = new ArrayList<JButton>();
    GridBagConstraints c;
    
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
        titleButtons = new JPanel(new BorderLayout());
        postFrameTitle = new JLabel(post.getTopic());
        descriptionTextField = new JTextArea(2, 20);
        descriptionTextField.setText(post.getContent());
        descriptionTextField.setLineWrap(true);
        descriptionTextField.setWrapStyleWord(true);
        descriptionTextField.setOpaque(false);
        descriptionTextField.setEditable(false);
        descriptionTextPane = new JScrollPane(descriptionTextField);
        viewPollButton = new JButton("View Poll");
        viewPollButton.setPreferredSize(new Dimension(150, 20));
        newCommentButton = new JButton("Add Comment");
        newCommentButton.setPreferredSize(new Dimension(150, 20));
        
        commentPanel = new JPanel(new GridLayout(0 , 1));
        post.getComments().stream().forEach(x -> System.out.print(x +", "));
        for(Comment comment: post.getComments()) {
            tmpPanel = new JPanel(new BorderLayout());
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
            if(comment.getUserUpvotes().contains(manager.getCurrUser()) && likeButton.isSelected()) {
                comment.removeUserUpvote(manager.getCurrUser());
                likeButton.doClick();
            }
            likeButton.addActionListener(new ActionListener(){
                @Override 
                public void actionPerformed(ActionEvent e) {
                    try {
                        manager = ProgramManager.get();
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    if(likeButtons.contains((e.getSource()))) {
                        likeButton = (JToggleButton) e.getSource();
                        if(likeButton.isSelected()) {
                            likeButton.setIcon(new ImageIcon("src/gui/icons/likeFilled.png"));
                            comment.addUserUpvote(manager.getCurrUser());
                            likeButton.setText(Integer.toString(comment.getVotes()));
                          try {
								manager.writeCourseFile();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
                        } else {
                            likeButton.setIcon(new ImageIcon("src/gui/icons/like.png"));
                            comment.removeUserUpvote(manager.getCurrUser());
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
            replyCommentButton = new JButton("Reply");
            replyCommentButton.setSize(new Dimension(5, 15));
            replyCommentButtons.add(replyCommentButton);
            replyCommentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    if(replyCommentButtons.contains(e.getSource())) {
                        replyCommentButton = (JButton) e.getSource();
                        manager.setCurrComment(post.getComments().get(replyCommentButtons.indexOf(replyCommentButton)));
                        MainFrame.get().switchPanel("New Comment", true);
                    }
                }
            });
            tmpPanel.add(commentAuthorTitle, BorderLayout.NORTH);
            tmpPanel.add(commentTextPane, BorderLayout.CENTER);
            commentButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
            commentButtons.add(likeButton);
            commentButtons.add(replyCommentButton);
            tmpPanel.add(commentButtons, BorderLayout.SOUTH);
            commentPanel.add(tmpPanel);
            for(Comment reply: comment.getReplies()) {
                tmpPanel = new JPanel(new BorderLayout());
                tmpPanel.setBorder(new EmptyBorder(0, 20, 0, 0));
                commentAuthorTitle = new JLabel(reply.getOwner().getUsername() + "    " + reply.getTimestamp());
                f = commentAuthorTitle.getFont();
                commentAuthorTitle.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
                commentText = new JTextArea(1, 20);
                commentText.setLineWrap(true);
                commentText.setWrapStyleWord(true);
                commentText.setEditable(false);
                commentText.setOpaque(false);
                commentText.setText(reply.getContent());
                commentTextPane = new JScrollPane(commentText);
                likeButton = new JToggleButton(String.valueOf(reply.getVotes()), new ImageIcon("src/gui/icons/like.png"));
                likeButton.setOpaque(true);
                likeButton.setContentAreaFilled(false);
                likeButton.setBorderPainted(false);
                likeButton.setFocusable(true);
                likeButtons.add(likeButton);
                if(reply.getUserUpvotes().contains(manager.getCurrUser()) && likeButton.isSelected()) {
                    reply.removeUserUpvote(manager.getCurrUser());
                    likeButton.doClick();
                }
                likeButton.addActionListener(new ActionListener(){
                    @Override 
                    public void actionPerformed(ActionEvent e) {
                        try {
                            manager = ProgramManager.get();
                        } catch (Exception e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        if(likeButtons.contains((e.getSource()))) {
                            likeButton = (JToggleButton) e.getSource();
                            if(likeButton.isSelected()) {
                                likeButton.setIcon(new ImageIcon("src/gui/icons/likeFilled.png"));
                                reply.addUserUpvote(manager.getCurrUser());
                                likeButton.setText(Integer.toString(reply.getVotes()));
                              try {
								manager.writeCourseFile();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
                            } else {
                                likeButton.setIcon(new ImageIcon("src/gui/icons/like.png"));
                                reply.removeUserUpvote(manager.getCurrUser());
                                likeButton.setText(Integer.toString(reply.getVotes()));
                            }
                        }
                    }
                });
                tmpPanel.add(commentAuthorTitle, BorderLayout.NORTH);
                tmpPanel.add(commentTextPane, BorderLayout.CENTER);
                commentButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
                commentButtons.add(likeButton);
                tmpPanel.add(commentButtons, BorderLayout.SOUTH);
                commentPanel.add(tmpPanel);
            }
        }
        addComponentsToContainer();
        addActionListeners();
    }

    private void addActionListeners(){
        viewPollButton.addActionListener(this);
        newCommentButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == newCommentButton) {
            MainFrame.get().switchPanel("New Comment", false);
        } else if(e.getSource() == viewPollButton) {
            try {
                manager = ProgramManager.get();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            ArrayList<String> pollOptions = manager.getCurrPost().getPoll().getPollOptions();
            ArrayList<Integer> pollVotes = manager.getCurrPost().getPoll().getPollResults();
            ArrayList<String> pollDisplay = new ArrayList<String>();
            for(int i = 0; i < pollOptions.size(); i++) {
                pollDisplay.add(pollOptions.get(i) + Integer.toString(pollVotes.get(i)));
            }
            String[] poll = new String[pollDisplay.size()];
            for(int i=0; i < poll.length; i++) poll[i] = pollDisplay.get(i);
            int in = (Integer) (JOptionPane.showInputDialog(null, "Poll", "Vote", JOptionPane.QUESTION_MESSAGE, null, poll, poll[0]));
        }   
    }

    public void addComponentsToContainer() {
        panel = new JPanel(new BorderLayout());
        titleButtons.add(newCommentButton, BorderLayout.NORTH);
        titleButtons.add(viewPollButton, BorderLayout.SOUTH);
        titlePanel.add(new Box.Filler(new Dimension(100, 20), new Dimension(100, 20), new Dimension(100, 20)),
        BorderLayout.WEST);
        Post post = manager.getCurrPost();
        if(post.getPoll() != null) titlePanel.add(viewPollButton, BorderLayout.WEST);
        else titlePanel.add(new Box.Filler(new Dimension(150, 20), new Dimension(150, 20), new Dimension(150, 20)),
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
