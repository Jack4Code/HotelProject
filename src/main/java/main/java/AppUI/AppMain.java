package main.java.AppUI;

import main.java.AppUI.Interfaces.*;
import main.java.AppUI.Views.LoginView;
import main.java.AppUI.Views.WelcomeView;
import main.java.Managers.MainPane;

import javax.swing.*;
import java.awt.*;

public class AppMain extends JPanel {
    protected static final String LOGIN_VIEW = "View.login";
    protected static final String WELCOME_VIEW = "View.welcome";

    private CardLayout cardLayout;

    private ILoginView loginView;
    private IWelcomeView welcomeView;

    public AppMain() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        // This could be established via a factory or builder pattern
        loginView = new LoginView(new LoginViewController());
        welcomeView = new WelcomeView((IWelcomeViewController) new WelcomeViewController());

        add(loginView.getView(), LOGIN_VIEW);
        add(welcomeView.getView(), WELCOME_VIEW);

        cardLayout.show(this, LOGIN_VIEW);
    }

    protected class LoginViewController implements ILoginViewController {

        @Override
        public void loginWasSuccessful(ICredentials credentials) {
            welcomeView.getViewController().setCredentials(credentials);
            cardLayout.show(AppMain.this, WELCOME_VIEW);
        }

        @Override
        public void loginDidFail() {
            JOptionPane.showMessageDialog(AppMain.this, "Login vaild", "Error", JOptionPane.ERROR_MESSAGE);
        }

        @Override
        public void loginWasCancelled() {
            SwingUtilities.windowForComponent(AppMain.this).dispose();
        }

    }

    protected class WelcomeViewController implements IWelcomeViewController {

        private IWelcomeViewController.ICredentialsListener credentialsListener;
        private ICredentials credentials;

        @Override
        public ICredentials getCredentials() {
            return credentials;
        }

        @Override
        public void setCredentials(ICredentials credentials) {
            this.credentials = credentials;
            IWelcomeViewController.ICredentialsListener listener = getCredentialsListener();
            if (listener != null) {
                listener.credentialsWereUpdated(credentials);
            }
        }

        @Override
        public void setCredentialsListener(IWelcomeViewController.ICredentialsListener listener) {
            this.credentialsListener = listener;
        }

        @Override
        public IWelcomeViewController.ICredentialsListener getCredentialsListener() {
            return credentialsListener;
        }

    }

}


