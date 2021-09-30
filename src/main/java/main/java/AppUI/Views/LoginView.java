package main.java.AppUI.Views;

import main.java.AppUI.Interfaces.ICredentials;
import main.java.AppUI.Interfaces.ILoginView;
import main.java.AppUI.Interfaces.ILoginViewController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class LoginView extends AbstractView<ILoginViewController> implements ILoginView {

    private JTextField userName;
    private JPasswordField password;

    private JButton login;
    private JButton cancel;

    //style properties...what a nightmare!
    private JPanel background;

    public LoginView(ILoginViewController controller) {
        super(controller);
        setLayout(new GridBagLayout());

        userName = new JTextField(10);
        password = new JPasswordField(10);

        login = new JButton("Login");
        cancel = new JButton("Cancel");

        login.addActionListener((ActionEvent e) -> {
            // Fake the login process...
            // This might be handed off to another controller...
            String name = userName.getText();
            if (name != null && !name.isEmpty()) {
                Random rnd = new Random();
                if (rnd.nextBoolean()) {
                    getViewController().loginWasSuccessful(new DefaultCredentials(userName.getText()));
                } else {
                    getViewController().loginDidFail();
                }
            }
        });
        cancel.addActionListener((ActionEvent e) -> {
            getViewController().loginWasCancelled();
        });

        //Style Properties
        background = new JPanel();
        background.setBackground(new java.awt.Color(53, 10, 86));
        background.setPreferredSize(new Dimension(1000, 1000));
        //add(background);


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(new JLabel("Login"), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        add(new JLabel("Username:"), gbc);

        gbc.gridy++;
        add(new JLabel("Password:"), gbc);

        gbc.gridx++;
        gbc.gridy = 1;
        add(userName, gbc);

        gbc.gridy++;
        add(password, gbc);

        JPanel controls = new JPanel();
        controls.add(login);
        controls.add(cancel);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(controls, gbc);
    }

    public class DefaultCredentials implements ICredentials {

        private final String userName;

        public DefaultCredentials(String userName) {
            this.userName = userName;
        }

        @Override
        public String getUserName() {
            return userName;
        }

    }

}
