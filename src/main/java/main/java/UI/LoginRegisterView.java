package main.java.UI;

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

    public LoginRegisterView() {

        loginContainerPanel = initializeLoginContainerPanel();

        this.add(loginContainerPanel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Hotel J3");
        ImageIcon mainIcon = new ImageIcon("hotel.png");
        this.setIconImage(mainIcon.getImage());
        this.getContentPane().setBackground(CustomColor.MAIN_PURPLE_THEME);
        this.setLayout(null);
        this.setSize(600, 850);
        this.setResizable(false); //TODO: Remove this and put in showOnScreen once showOnScreen works properly
        //showOnScreen(0, this);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public JPanel initializeLoginContainerPanel(){
        JPanel jpanel = new JPanel();
        jpanel.setBackground(CustomColor.LOGIN_CONTAINER_THEME);
        jpanel.setBounds(90, 100, 400, 550);
        jpanel.setLayout(null);

        jpanel.add(getHeaderPane("Login", "Sign in to your account"));
        jpanel.add(getInputComponent(185, "Email:", false));
        jpanel.add(getInputComponent(275, "Password:", true));

        return jpanel;
    }

    public JPanel getHeaderPane(String titleTxt, String subTitleTxt){
        JPanel wrapper = new JPanel();
        wrapper.setBackground(CustomColor.LOGIN_CONTAINER_THEME);
        wrapper.setBounds(0, 0, 400, 150);
        wrapper.setLayout(null);

        JLabel title = new JLabel(titleTxt);
        title.setBounds(133,-30, 200, 200);
        title.setFont(new Font("serif", Font.PLAIN, 50));
        title.setForeground(CustomColor.PURPLE_THEME_TXT);

        JLabel subTitle = new JLabel(subTitleTxt);
        subTitle.setBounds(123,20, 200, 200);
        subTitle.setFont(new Font("serif", Font.PLAIN, 16));

        JPanel borderBottom = new JPanel();
        borderBottom.setBackground(CustomColor.MAIN_PURPLE_THEME);
        borderBottom.setBounds(10, 146, 380, 4);

        wrapper.add(title);
        wrapper.add(subTitle);
        wrapper.add(borderBottom);

        return wrapper;
    }

    public JPanel getInputComponent(int yPosition, String placeholderTxt, boolean isPassword){
        JPanel panel = new JPanel();
        panel.setBackground(CustomColor.LOGIN_CONTAINER_THEME);
        panel.setBounds(0, yPosition, 400, 75);
        panel.setLayout(null);

        JLabel label = new JLabel(placeholderTxt);
        label.setBounds(40, 10, 200, 16);
        label.setFont(new Font("serif", Font.PLAIN, 16));

        JTextField textField = new JTextField();
        if(isPassword){
            textField = new JPasswordField();
        }
        textField.setBounds(40, 30, 320, 40);
        textField.setBackground(CustomColor.INPUT_BACKGROUND);
        //placeholder text? //TODO: figure out how to do this in swing...swing sucks so bad!!
        textField.setFont(new Font("serif", Font.PLAIN, 20));
        textField.setBorder(BorderFactory.createEmptyBorder(5,10,5,5));
        textField.setMargin(new Insets(0,10,0,0));

        panel.add(label);
        panel.add(textField);
        return panel;
    }

    public void switchToRegisterView() {

    }

    public void switchToLoginView() {

    }


    public static void showOnScreen( int screen, JFrame frame ) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gd = ge.getScreenDevices();
        if( screen > -1 && screen < gd.length ) {
            frame.setLocation(gd[screen].getDefaultConfiguration().getBounds().x, gd[0].getDefaultConfiguration().getBounds().y + frame.getY());
        } else if( gd.length > 0 ) {
            frame.setLocation(gd[0].getDefaultConfiguration().getBounds().x, gd[0].getDefaultConfiguration().getBounds().y + frame.getY());
        } else {
            throw new RuntimeException( "No Screens Found" );
        }
    }


    @Override
    public void actionPerformed(ActionEvent e){

    }

}
