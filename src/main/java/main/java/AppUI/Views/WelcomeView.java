package main.java.AppUI.Views;

import main.java.AppUI.Interfaces.ICredentials;
import main.java.AppUI.Interfaces.IWelcomeView;
import main.java.AppUI.Interfaces.IWelcomeViewController;

import javax.swing.*;
import java.awt.*;

public class WelcomeView extends AbstractView<IWelcomeViewController> implements IWelcomeView {

    private JLabel userName;

    public WelcomeView(IWelcomeViewController viewContoller) {
        super(viewContoller);
        viewContoller.setCredentialsListener((ICredentials credentials) -> {
            userName.setText(credentials.getUserName());
            revalidate();
            repaint();
        });

        userName = new JLabel("...");

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(10, 10, 10, 10);

        add(new JLabel("WELCOME!"), gbc);
        add(userName, gbc);

    }

}
