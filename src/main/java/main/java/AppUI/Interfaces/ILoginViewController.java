package main.java.AppUI.Interfaces;

public interface ILoginViewController extends IViewController {

    public void loginWasSuccessful(ICredentials credentials);

    public void loginDidFail();

    public void loginWasCancelled();

}