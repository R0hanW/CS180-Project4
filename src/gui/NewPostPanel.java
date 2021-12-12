package gui;

import javax.swing.*;

import backend.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class NewPostPanel extends JPanel implements ActionListener {
    JLabel topicLabel, descriptionLabel, pollLabel, pollOptionLabel;
    JTextField topicText, userText, pollOption;
    JTextArea descriptionText;
    JButton submitButton, importPostButton;
    JButton addPollOptionButton, removePollOptionButton;
    ArrayList<JLabel> pollOptionLabels = new ArrayList<JLabel>();
    ArrayList<JTextField> pollOptions = new ArrayList<JTextField>();
    int pollOptionCounter;
    JRadioButton pollButton;
    JScrollPane scrollPane;
    JPanel panel, tmpPanel, pollPanel;
    Post post;
    GridBagConstraints c;
    ProgramManager manager;

    public NewPostPanel() {
        try {
            manager = ProgramManager.get();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        initComponents();
        setPreferredSize(new Dimension(500, 750));
    }

    private void initComponents() {
        topicLabel = new JLabel("Course Topic:");
        topicText = new JTextField();
        descriptionLabel = new JLabel("Post Description:");
        descriptionText = new JTextArea(5, 20);
        descriptionText.setLineWrap(true);
        descriptionText.setWrapStyleWord(true);
        scrollPane = new JScrollPane(descriptionText);
        pollLabel = new JLabel("Create Poll?");
        pollButton = new JRadioButton("Yes");
        addPollOptionButton = new JButton("Add Poll Option");
        removePollOptionButton = new JButton("Remove Poll Option");
        panel = new JPanel(new GridBagLayout());
        importPostButton = new JButton("Import Post");
        submitButton = new JButton("Submit");
        addActionListeners();
        addComponentsToContainer();
        add(panel, BorderLayout.CENTER);

    }

    private void addActionListeners() {
        addPollOptionButton.addActionListener(this);
        removePollOptionButton.addActionListener(this);
        submitButton.addActionListener(this);
        pollButton.addActionListener(this);
        importPostButton.addActionListener(this);
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
            Network network = new Network();
            post = new Post(manager.getCurrUser(), manager.getCurrCourse(), descriptionText.getText(),
                    topicText.getText());
            //network.addPost(manager.getCurrUser(), manager.getCurrCourse(), descriptionText.getText(),
            //        topicText.getText());
            if (pollOptions.size() > 1) {
                Poll poll = new Poll();
                pollOptions.stream().forEach(pollOption -> poll.addPollOption(pollOption.getText()));
                post.addPoll(poll);
            }
            try {
				manager.getCurrCourse().addPost(post);
				manager.writeCourseFile();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

            //network.addPost(manager.getCurrCourse(), post);



            MainFrame.get().switchPanel("Course");
        } else if (e.getSource() == pollButton) {
            if (pollButton.isSelected()) {
                panel.remove(submitButton);
                c = new GridBagConstraints();
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 0;
                c.gridy = 3;
                panel.add(addPollOptionButton, c);
                c.gridx = 1;
                c.gridy = 3;
                panel.add(removePollOptionButton, c);
                // move submit button back to bottom
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 0;
                c.gridy = 4;
                c.gridwidth = 2;
                panel.add(submitButton, c);
                revalidate();
            } else {
                panel.remove(addPollOptionButton);
                panel.remove(removePollOptionButton);
                pollOptionLabels.stream().forEach(x -> panel.remove(x));
                pollOptions.stream().forEach(x -> panel.remove(x));
                pollOptionLabels.removeAll(pollOptionLabels);
                pollOptions.removeAll(pollOptions);
                addComponentsToContainer();
                revalidate();
            }
        } else if (e.getSource() == addPollOptionButton) {
            panel.remove(submitButton);
            c = new GridBagConstraints();
            pollOptionCounter = pollOptions.size() + 1;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = 3 + pollOptionCounter;
            pollOptionLabels.add(new JLabel(String.format("Option %d", pollOptionCounter)));
            panel.add(pollOptionLabels.get(pollOptionLabels.size() - 1), c);
            pollOptions.add(new JTextField());
            c.gridx = 1;
            c.gridy = 3 + pollOptionCounter;
            panel.add(pollOptions.get(pollOptions.size() - 1), c);
            // move submit button back to bottom
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = 4 + pollOptionCounter;
            c.gridwidth = 2;
            panel.add(submitButton, c);
            revalidate();
        } else if (e.getSource() == removePollOptionButton) {
            panel.remove(pollOptionLabels.get(pollOptionLabels.size() - 1));
            panel.remove(pollOptions.get(pollOptions.size() - 1));
            pollOptionLabels.remove(pollOptionLabels.size() - 1);
            pollOptions.remove(pollOptions.size() - 1);
            panel.remove(submitButton);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = 4 + pollOptions.size();
            c.gridwidth = 2;
            panel.add(submitButton, c);
            revalidate();
        } else if(e.getSource() == importPostButton) {
            MainFrame.get().switchPanel("Import Post");
        }
    }

    private void addComponentsToContainer() {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(topicLabel, c);
        c.gridx = 1;
        c.gridy = 0;
        panel.add(topicText, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.ipady = 20;
        panel.add(descriptionLabel, c);
        c.gridx = 1;
        c.gridy = 1;
        panel.add(scrollPane, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        panel.add(pollLabel, c);
        c.gridx = 1;
        c.gridy = 2;
        panel.add(pollButton, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        panel.add(submitButton, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
        panel.add(importPostButton, c);
    }
}
