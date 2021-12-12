package gui;

import javax.swing.*;

import backend.*;

import java.awt.*;
import java.awt.event.*;

public class NewCommentPanel extends JPanel implements ActionListener {
    JLabel commentLabel;
    JTextArea commentText;
    JScrollPane scrollPane;
    JPanel panel;
    JButton submitButton, importCommentButton;
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

    public NewCommentPanel() {
        try {
            manager = ProgramManager.get();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.reply = false;
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
        importCommentButton = new JButton("Import Comment");
        submitButton = new JButton("Submit");
        addComponentsToContainer();
        addActionListeners();
    }

    private void addActionListeners() {
       submitButton.addActionListener(this);
       importCommentButton.addActionListener(this);
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
            if(reply == true) {
            	manager.getCurrComment().addReply(new Comment(manager.getCurrUser(), manager.getCurrPost(), commentText.getText()));
            	try {
					manager.writeCourseFile();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
            else if(reply == false) {
            	manager.getCurrPost().addComment(new Comment(manager.getCurrUser(), manager.getCurrPost(), commentText.getText()));
            	try {
					manager.writeCourseFile();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
            Network network = new Network();
            //if (reply){
            //    network.addReply(manager.getCurrCourse(), manager.getCurrPost(),  manager.getCurrComment(), new Comment(manager.getCurrUser(), manager.getCurrPost(), commentText.getText()));
            //
            //} else {
            //    network.addComment(manager.getCurrCourse(), manager.getCurrPost(), new Comment(manager.getCurrUser(), manager.getCurrPost(), commentText.getText()));
            //}
            //network.writeFile();
            MainFrame.get().switchPanel("Post");
        } else if (e.getSource() == importCommentButton) {
            System.out.println("WYA");  
            MainFrame.get().switchPanel("Import Comment");
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
        panel.add(submitButton, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        panel.add(importCommentButton, c);
        add(panel, BorderLayout.CENTER);
    }
}
