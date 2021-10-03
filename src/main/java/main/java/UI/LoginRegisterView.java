package main.java.UI;

import main.java.Managers.UserManager;
import main.java.UI.Resources.CustomColor;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginRegisterView extends JFrame implements ActionListener {

    JButton loginButton;
    JButton registerButton;
    JButton toggleLoginPage;
    JButton toggleRegisterPage;

    JPanel loginContainerPanel;
    JPanel registerContainerPanel;

    JTextField userNameTxtField, passwordTxtField, firstnameTxtField, lastnameTxtField;

    public LoginRegisterView() {


        loginContainerPanel = initializeLoginContainerPanel();

        this.add(loginContainerPanel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Hotel J3");
        ImageIcon mainIcon = new ImageIcon("hotel2.png");
        this.setIconImage(mainIcon.getImage());
        this.getContentPane().setBackground(CustomColor.MAIN_PURPLE_THEME);
        this.setLayout(null);
        this.setSize(600, 850);
        this.setResizable(false);
        //showOnScreen(0, this); //TODO: Remove this and put in showOnScreen once showOnScreen works properly
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public JPanel initializeLoginContainerPanel() {
        JPanel jpanel = new JPanel();
        jpanel.setBackground(CustomColor.LOGIN_CONTAINER_THEME);
        jpanel.setBounds(90, 100, 400, 550);
        jpanel.setLayout(null);

        jpanel.add(getHeaderPane("Login", "Sign in to your account"));
        jpanel.add(getInputComponent(185, "Email", false));
        jpanel.add(getInputComponent(270, "Password", true));

        loginButton = new JButton("Login");
        loginButton.setFocusable(false);
        loginButton.setFont(new Font("serif", Font.PLAIN, 30)); //TODO: figure out why this shifts the font position down
        loginButton.setBounds(40, 365, 320, 60);
        loginButton.setBackground(CustomColor.PURPLE_THEME_TXT);
        loginButton.setForeground(CustomColor.LOGIN_CONTAINER_THEME);
        loginButton.addActionListener(this);

        toggleRegisterPage = new JButton("Register");
        toggleRegisterPage.setFocusable(false);
        toggleRegisterPage.setFont(new Font("serif", Font.PLAIN, 30)); //TODO: figure out why this shifts the font position down
        toggleRegisterPage.setBounds(40, 440, 320, 60);
        toggleRegisterPage.setBackground(CustomColor.PURPLE_THEME_TXT);
        toggleRegisterPage.setForeground(CustomColor.LOGIN_CONTAINER_THEME);
        toggleRegisterPage.addActionListener(this);

        jpanel.add(loginButton);
        jpanel.add(toggleRegisterPage);

        return jpanel;
    }

    public JPanel initializeRegisterContainerPanel() {
        JPanel jpanel = new JPanel();
        jpanel.setBackground(CustomColor.LOGIN_CONTAINER_THEME);
        jpanel.setBounds(90, 100, 400, 685);
        jpanel.setLayout(null);

        jpanel.add(getHeaderPane("Register", "Create new account"));
        jpanel.add(getInputComponent(165, "First Name", false));
        jpanel.add(getInputComponent(250, "Last Name", false));
        jpanel.add(getInputComponent(335, "Email", false));
        jpanel.add(getInputComponent(420, "Password", true));


        registerButton = new JButton("Create Account");
        registerButton.setFocusable(false);
        registerButton.setFont(new Font("serif", Font.PLAIN, 30)); //TODO: figure out why this shifts the font position down
        registerButton.setBounds(40, 510, 320, 60);
        registerButton.setBackground(CustomColor.PURPLE_THEME_TXT);
        registerButton.setForeground(CustomColor.LOGIN_CONTAINER_THEME);
        registerButton.addActionListener(this);

        toggleLoginPage = new JButton("Sign in");
        toggleLoginPage.setFocusable(false);
        toggleLoginPage.setFont(new Font("serif", Font.PLAIN, 30)); //TODO: figure out why this shifts the font position down
        toggleLoginPage.setBounds(40, 585, 320, 60);
        toggleLoginPage.setBackground(CustomColor.PURPLE_THEME_TXT);
        toggleLoginPage.setForeground(CustomColor.LOGIN_CONTAINER_THEME);
        toggleLoginPage.addActionListener(this);


        jpanel.add(registerButton);
        jpanel.add(toggleLoginPage);


        return jpanel;
    }

    public JPanel getHeaderPane(String titleTxt, String subTitleTxt) {
        JPanel wrapper = new JPanel();
        wrapper.setBackground(CustomColor.LOGIN_CONTAINER_THEME);
        wrapper.setBounds(0, 0, 400, 150);
        wrapper.setLayout(null);

        JLabel title = new JLabel(titleTxt);
        if (titleTxt.equals("Register")) {
            title.setBounds(117, -30, 200, 200);
        } else {
            title.setBounds(133, -30, 200, 200);
        }
        title.setFont(new Font("serif", Font.PLAIN, 50));
        title.setForeground(CustomColor.PURPLE_THEME_TXT);

        JLabel subTitle = new JLabel(subTitleTxt);
        if (titleTxt.equals("Register")) {
            subTitle.setBounds(130, 20, 200, 200);
        } else {
            subTitle.setBounds(123, 20, 200, 200);
        }
        subTitle.setFont(new Font("serif", Font.PLAIN, 16));

        JPanel borderBottom = new JPanel();
        borderBottom.setBackground(CustomColor.MAIN_PURPLE_THEME);
        borderBottom.setBounds(10, 146, 380, 4);

        wrapper.add(title);
        wrapper.add(subTitle);
        wrapper.add(borderBottom);

        return wrapper;
    }

    public JPanel getInputComponent(int yPosition, String placeholderTxt, boolean isPassword) {
        JPanel panel = new JPanel();
        panel.setBackground(CustomColor.LOGIN_CONTAINER_THEME);
        panel.setBounds(0, yPosition, 400, 75);
        panel.setLayout(null);

        JLabel label = new JLabel(placeholderTxt + ":");
        label.setBounds(40, 10, 200, 16);
        label.setFont(new Font("serif", Font.PLAIN, 16));

        JTextField textField = new JTextField();
        if (isPassword) {
            textField = new JPasswordField();
        }
        textField.putClientProperty("id", placeholderTxt);
        textField.setBounds(40, 30, 320, 40);
        textField.setBackground(CustomColor.INPUT_BACKGROUND);
        //placeholder text? //TODO: figure out how to do this in swing...swing sucks so bad!!
        textField.setFont(new Font("serif", Font.PLAIN, 20));
        textField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        textField.setMargin(new Insets(0, 10, 0, 0));

        switch (placeholderTxt) {
            case "Email":
                userNameTxtField = textField;
                break;
            case "Password":
                passwordTxtField = textField;
                break;
            case "First Name":
                firstnameTxtField = textField;
                break;
            case "Last Name":
                lastnameTxtField = textField;
                break;
            default:
                break;
        }

        panel.add(label);
        panel.add(textField);
        return panel;
    }

    public void switchToRegisterView() {
        this.remove(loginContainerPanel);
        registerContainerPanel = initializeRegisterContainerPanel();
        this.add(registerContainerPanel);
        this.repaint();
    }

    public void switchToLoginView() {
        this.remove(registerContainerPanel);
        loginContainerPanel = initializeLoginContainerPanel();
        this.add(loginContainerPanel);
        this.repaint();
    }


    public static void showOnScreen(int screen, JFrame frame) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gd = ge.getScreenDevices();
        if (screen > -1 && screen < gd.length) {
            frame.setLocation(gd[screen].getDefaultConfiguration().getBounds().x, gd[0].getDefaultConfiguration().getBounds().y + frame.getY());
        } else if (gd.length > 0) {
            frame.setLocation(gd[0].getDefaultConfiguration().getBounds().x, gd[0].getDefaultConfiguration().getBounds().y + frame.getY());
        } else {
            throw new RuntimeException("No Screens Found");
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            System.out.println("Login button pressed!");
            UserManager userManager = new UserManager(userNameTxtField.getText(), passwordTxtField.getText()); //TODO: Sanitize inputs! Don't allow SQL Injection!
            userManager.activeUser.password = passwordTxtField.getText(); //TODO: Hacky, modify this later
            if (userManager.activeUser != null) {
                new PortalView(userManager);
                this.dispose();
            } else {
                //Invalid login attempt
                JLabel invalidLoginAttemptTxt = new JLabel("Invalid signin attempt!");
                invalidLoginAttemptTxt.setBounds(40, 152, 250, 30);
                invalidLoginAttemptTxt.setFont(new Font("serif", Font.PLAIN, 25));
                invalidLoginAttemptTxt.setForeground(CustomColor.WARNING_RED);

                loginContainerPanel.add(invalidLoginAttemptTxt);
                this.repaint();
            }
        } else if (e.getSource() == registerButton) {
            try {
                UserManager.registerUser(firstnameTxtField.getText(), lastnameTxtField.getText(), userNameTxtField.getText(), passwordTxtField.getText());
                this.switchToLoginView();
            } catch (Exception ex) {
                System.out.println("User already exists or another error!");
            }

        } else if (e.getSource() == toggleRegisterPage) {
            System.out.println("toggleRegisterPage button pressed!");
            this.switchToRegisterView();
        } else if (e.getSource() == toggleLoginPage) {
            System.out.println("toggleLoginPage button pressed");
            this.switchToLoginView();
        }
    }

}
