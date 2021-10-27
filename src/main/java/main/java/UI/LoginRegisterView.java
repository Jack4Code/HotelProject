package main.java.UI;

import main.java.Managers.UserManager;
import main.java.UI.Resources.CustomColor;
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoginRegisterView extends JFrame implements ActionListener {

    JButton loginButton;
    JButton registerButton;
    JButton toggleLoginPage;
    JButton toggleRegisterPage;

    JPanel loginContainerPanel;
    JPanel registerContainerPanel;

    JLabel invalidRegisterAttemptTxt = null;

    JTextField userNameTxtField, passwordTxtField, firstnameTxtField, lastnameTxtField;

    String loginRegisterState;

    public LoginRegisterView() {
        loginRegisterState = "LOGIN";
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
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public JPanel initializeLoginContainerPanel() {
        JPanel jpanel = new JPanel();
        jpanel.setBackground(CustomColor.LOGIN_CONTAINER_THEME);
        jpanel.setBounds(90, 100, 400, 550);
        jpanel.setLayout(null);

        jpanel.add(getHeaderPane("Login", "Sign in to your account"));
        jpanel.add(getInputComponent(185, "Email", false, true));
        jpanel.add(getInputComponent(270, "Password", true, true));

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
        jpanel.add(getInputComponent(165, "First Name", false, false));
        jpanel.add(getInputComponent(250, "Last Name", false, false));
        jpanel.add(getInputComponent(335, "Email", false, false));
        jpanel.add(getInputComponent(420, "Password", true, false));


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

    public JPanel getInputComponent(int yPosition, String placeholderTxt, boolean isPassword, boolean includeIcon) {
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
                if (includeIcon) {
                    JPanel userNameIconContainer = new JPanel();
                    userNameIconContainer.setBounds(1, 4, 30, 75);
                    userNameIconContainer.setBackground(CustomColor.INPUT_BACKGROUND);
                    ImageIcon usernameIcon = new ImageIcon("username_icon_purple.png");
                    Image image = usernameIcon.getImage();
                    Image newImage = image.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
                    usernameIcon = new ImageIcon(newImage);
                    JLabel usernameIconLabel = new JLabel(usernameIcon);
                    userNameIconContainer.add(usernameIconLabel);
                    textField.add(userNameIconContainer);
                    textField.setBorder(BorderFactory.createEmptyBorder(5, 35, 5, 5));
                }
                userNameTxtField = textField;
                break;
            case "Password":
                if (includeIcon) {
                    JPanel passwordIconContainer = new JPanel();
                    passwordIconContainer.setBounds(1, 4, 30, 75);
                    passwordIconContainer.setBackground(CustomColor.INPUT_BACKGROUND);
                    ImageIcon passwordIcon = new ImageIcon("password_icon_purple.png");
                    Image image = passwordIcon.getImage();
                    Image newImage = image.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
                    passwordIcon = new ImageIcon(newImage);
                    JLabel usernameIconLabel = new JLabel(passwordIcon);
                    passwordIconContainer.add(usernameIconLabel);
                    textField.add(passwordIconContainer);
                    textField.setBorder(BorderFactory.createEmptyBorder(5, 35, 5, 5));
                }
                textField.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent evt) {

                    }

                    @Override
                    public void keyPressed(KeyEvent evt) {
                        if (evt.getKeyCode() == 10) {
                            if (loginRegisterState.equals("LOGIN")) {
                                handleLogin();
                            } else if (loginRegisterState.equals("REGISTER")) {
                                handleRegister();
                            }
                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent evt) {

                    }
                });
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

    public void renderInvalidRegisterAttempt() {
        if (invalidRegisterAttemptTxt == null) {

            invalidRegisterAttemptTxt = new JLabel("Invalid register attempt!");
            invalidRegisterAttemptTxt.setBounds(165, 30, 250, 30);
            invalidRegisterAttemptTxt.setFont(new Font("serif", Font.PLAIN, 25));
            invalidRegisterAttemptTxt.setForeground(CustomColor.WARNING_RED);

            this.add(invalidRegisterAttemptTxt);
            this.repaint();
        }
    }

    public void switchToRegisterView() {
        loginRegisterState = "REGISTER";
        this.remove(loginContainerPanel);
        registerContainerPanel = initializeRegisterContainerPanel();
        this.add(registerContainerPanel);
        this.repaint();
        firstnameTxtField.requestFocus();
    }

    public void switchToLoginView() {
        if (invalidRegisterAttemptTxt != null) {
            this.remove(invalidRegisterAttemptTxt);
            invalidRegisterAttemptTxt = null;
        }
        loginRegisterState = "LOGIN";
        this.remove(registerContainerPanel);
        loginContainerPanel = initializeLoginContainerPanel();
        this.add(loginContainerPanel);
        this.repaint();
        //Having to trick it in order to repaint the icons...such a shame
        userNameTxtField.setText("Email");
        passwordTxtField.setText("Password");
        userNameTxtField.setText("");
        passwordTxtField.setText("");
        userNameTxtField.requestFocus();
    }

    public void handleLogin() {
        new Thread(() -> {
            UserManager userManager = new UserManager(userNameTxtField.getText(), passwordTxtField.getText());
            if (userManager.activeUser != null) {
                userManager.activeUser.password = passwordTxtField.getText();
            }
            if (userManager.activeUser != null) {
                new PortalView(userManager);
                this.dispose();
            } else {
                JLabel invalidLoginAttemptTxt = new JLabel("Invalid signin attempt!");
                invalidLoginAttemptTxt.setBounds(40, 152, 250, 30);
                invalidLoginAttemptTxt.setFont(new Font("serif", Font.PLAIN, 25));
                invalidLoginAttemptTxt.setForeground(CustomColor.WARNING_RED);
                loginContainerPanel.add(invalidLoginAttemptTxt);
                this.repaint();
            }
        }).start();
    }

    public void handleRegister() {
        new Thread(() -> {
            try {
                if (!userNameTxtField.getText().equals("")) {
                    if (UserManager.registerUser(firstnameTxtField.getText(), lastnameTxtField.getText(), userNameTxtField.getText(), passwordTxtField.getText())) {
                        this.switchToLoginView();
                    } else {
                        renderInvalidRegisterAttempt();
                    }
                } else {
                    renderInvalidRegisterAttempt();
                }
            } catch (Exception ex) {
                renderInvalidRegisterAttempt();
            }
        }).start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    handleLogin();
                }
            });
        } else if (e.getSource() == registerButton) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    handleRegister();
                }
            });
        } else if (e.getSource() == toggleRegisterPage) {
            System.out.println("toggleRegisterPage button pressed!");
            this.switchToRegisterView();
        } else if (e.getSource() == toggleLoginPage) {
            System.out.println("toggleLoginPage button pressed");
            this.switchToLoginView();
        }
    }
}
