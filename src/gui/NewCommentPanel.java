package gui;

import javax.swing.*;

import backend.Comment;
import backend.ProgramManager;

import java.awt.*;
import java.awt.event.*;

public class NewCommentPanel extends JPanel implements ActionListener {
    JLabel commentLabel;
    JTextArea commentText;
    JScrollPane scrollPane;
    JPanel panel;
    JButton submitButton;
    ProgramManager manager;
    boolean reply;
    public NewCommentPanel(boolean reply) {
        try {
            manager = ProgramManager.get();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.reply = reply;
        initComponents();
        setPreferredSize(new Dimension(150, 300));
    }

    private void initComponents() {
        panel = new JPanel(new GridBagLayout());
        commentLabel = new JLabel("Comment:");
        commentText = new JTextArea(5, 20);
        commentText.setLineWrap(true);
        commentText.setWrapStyleWord(true);
        scrollPane = new JScrollPane(commentText);
        submitButton = new JButton("Submit");
        addComponentsToContainer();
        addActionListeners();
    }

    private void addActionListeners() {
       submitButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            try {
                manager = ProgramManager.get();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            if(reply == true) manager.getCurrComment().addReply(new Comment(manager.getCurrUser(), manager.getCurrPost(), commentText.getText()));
            else if(reply == false) manager.getCurrPost().addComment(new Comment(manager.getCurrUser(), manager.getCurrPost(), commentText.getText()));
            MainFrame.get().switchPanel("Post");
        }
    }

    private void addComponentsToContainer() {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 20;
        panel.add(commentLabel, c);
        c.gridx = 1;
        c.gridy = 0;
        panel.add(scrollPane, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        panel.add(submitButton, c);
        add(panel, BorderLayout.CENTER);
    }
}
