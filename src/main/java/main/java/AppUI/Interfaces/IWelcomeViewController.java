package main.java.AppUI.Interfaces;

public interface IWelcomeViewController extends IViewController {

    public ICredentials getCredentials();
    public void setCredentials(ICredentials credentials);

    public void setCredentialsListener(ICredentialsListener listener);
    public ICredentialsListener getCredentialsListener();

    public interface ICredentialsListener {

        public void credentialsWereUpdated(ICredentials credentials);

    }

}