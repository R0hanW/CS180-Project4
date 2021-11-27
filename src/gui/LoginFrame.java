package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import backend.*;

public class LoginFrame extends JFrame implements ActionListener{
    JLabel userLabel, passwordLabel;
    JTextField userText;
    JPasswordField passwordText;
    JButton loginButton, signUpButton;
    JPanel panel;
    ProgramManager manager; 
    
    
    public LoginFrame() {
        initComponents();
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        try {
            manager = ProgramManager.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                try {
                    manager.writeFile();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                System.exit(0);
            }
        });
    }

    private void initComponents(){
        //create login JFrame
        userLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");
        userText = new JTextField();
        passwordText = new JPasswordField();
        loginButton = new JButton("Login");
        signUpButton = new JButton("Sign Up");
        panel = new JPanel(new GridLayout(3, 2));
        addComponentsToContainer();
        addActionListeners();
        add(panel, BorderLayout.CENTER);
        setTitle("Discussion Board");
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == loginButton) {
            try {
                manager = ProgramManager.get();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            //check if username exists
            User user = manager.findUser(userText.getText());
            if(user == null) {
                JOptionPane.showMessageDialog(null, "Username does not exist! Click sign up to create an account!", "Login", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else if(!user.getPassword().equals(String.valueOf(passwordText.getPassword()))) {
                JOptionPane.showMessageDialog(null, "Incorrect Password!", "Login", JOptionPane.INFORMATION_MESSAGE);
            } else {
                manager.setCurrUser(user);
                this.dispose();
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
            }
        } else if(e.getSource() == signUpButton) {
            JFrame signUpFrame = new SignUpFrame();
            signUpFrame.setVisible(true);
            this.dispose();
        }
    }

    public void addActionListeners() { 
        signUpButton.addActionListener(this);
        loginButton.addActionListener(this);
    }

    public void addComponentsToContainer(){
        panel.add(userLabel);
        panel.add(userText);
        panel.add(passwordLabel);
        panel.add(passwordText);
        panel.add(signUpButton);
        panel.add(loginButton);
    }
}