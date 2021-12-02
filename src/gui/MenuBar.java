package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import backend.ProgramManager;
public class MenuBar extends JMenuBar implements ActionListener{
    JMenu userMenu;
    JButton backItem, homeItem, forwardItem;
    JMenuItem profileItem, logOutItem;
    ProgramManager manager;

    public MenuBar() {
        try {
            manager = ProgramManager.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setMinimumSize(new Dimension(500, 20));
        setMaximumSize(new Dimension(500, 20));
        setPreferredSize(new Dimension(500, 20));
        initComponents();
    }

    public void initComponents() {
        backItem = new JButton(new ImageIcon("src/gui/icons/back.png"));
        backItem.setOpaque(true);
        backItem.setContentAreaFilled(false);
        backItem.setBorderPainted(false);
        backItem.setFocusable(true);
        backItem.setMinimumSize(new Dimension(20, 20));
        backItem.setMaximumSize(new Dimension(20, 20));
        backItem.setPreferredSize(new Dimension(20, 20));
        homeItem = new JButton(new ImageIcon("src/gui/icons/home.png"));
        homeItem.setOpaque(true);
        homeItem.setContentAreaFilled(false);
        homeItem.setBorderPainted(false);
        homeItem.setFocusable(true);
        homeItem.setMinimumSize(new Dimension(20, 20));
        homeItem.setMaximumSize(new Dimension(20, 20));
        homeItem.setPreferredSize(new Dimension(20, 20));
        forwardItem = new JButton(new ImageIcon("src/gui/icons/forward.png"));
        forwardItem.setOpaque(true);
        forwardItem.setContentAreaFilled(false);
        forwardItem.setBorderPainted(false);
        forwardItem.setFocusable(true);
        forwardItem.setMinimumSize(new Dimension(20, 20));
        forwardItem.setMaximumSize(new Dimension(20, 20));
        forwardItem.setPreferredSize(new Dimension(20, 20));
        userMenu = new JMenu(manager.getCurrUser().getName());
        profileItem = new JMenuItem("Profile");
        logOutItem = new JMenuItem("Log Out");
        userMenu.add(profileItem);
        userMenu.add(logOutItem);
        
        addActionListeners();
        add(backItem);
        add(homeItem);
        add(forwardItem);
        add(Box.createHorizontalGlue());
        add(userMenu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == backItem) {
            MainFrame.get().switchPanel("Previous");
        } else if(e.getSource() == forwardItem) {
            MainFrame.get().switchPanel("Next");
        } else if(e.getSource() == homeItem) {
            MainFrame.get().switchPanel("Main");
        } else if(e.getSource() == profileItem) {
            //TODO
        } else if(e.getSource() == logOutItem) {
            try {
                ProgramManager.get().setCurrUser(null);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            MainFrame.get().switchPanel("Login");
        }
    }

    private void addActionListeners() {
        backItem.addActionListener(this);
        homeItem.addActionListener(this);
        forwardItem.addActionListener(this);
        profileItem.addActionListener(this);
        logOutItem.addActionListener(this);
    }
}  
