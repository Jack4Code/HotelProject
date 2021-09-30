package main.java.AppUI.Interfaces;

import javax.swing.*;

public interface IView<C extends IViewController> {
    public JComponent getView();
    public C getViewController();
}






