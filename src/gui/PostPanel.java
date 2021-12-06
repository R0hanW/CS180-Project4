package gui;

import javax.swing.*;
import javax.swing.border.*;

import backend.Comment;
import backend.Post;
import backend.ProgramManager;

import java.awt.*;
import java.awt.event.*;
public class PostPanel extends JPanel implements ActionListener{
    
    ProgramManager manager;
    JPanel titlePanel, commentPane, tmpPanel;
    JScrollPane descriptionTextPane, commentTextPane;
    JLabel postFrameTitle, commentAuthorTitle;
    JTextArea descriptionTextField, commentText;
    JToggleButton likeButton;
    JButton newCommentButton;

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
        descriptionTextField = new JTextArea(20, 20);
        descriptionTextField.setText(post.getContent());
        descriptionTextField.setLineWrap(true);
        descriptionTextField.setWrapStyleWord(true);
        descriptionTextField.setOpaque(false);
        descriptionTextField.setEditable(false);
        descriptionTextPane = new JScrollPane(descriptionTextField);

        for(Comment comment: post.getComments()) {
            tmpPanel = new JPanel(new BorderLayout());
            commentAuthorTitle = new JLabel(comment.getOwner().getName() + comment.getTimestamp());
            commentText = new JTextArea(5, 20);
            commentText.setLineWrap(true);
            commentText.setWrapStyleWord(true);
            commentText.setEditable(false);
            commentTextPane = new JScrollPane(commentText);
            likeButton = new JToggleButton(String.valueOf(comment.getVotes()), new ImageIcon("src/gui/icons/like.png"));
            // likeButton.addActionListener()

        }
        newCommentButton = new JButton("Add Comment");
        newCommentButton.setPreferredSize(new Dimension(80, 20));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }
}
