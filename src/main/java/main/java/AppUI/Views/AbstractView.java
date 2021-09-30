package main.java.AppUI.Views;

import main.java.AppUI.Interfaces.IView;
import main.java.AppUI.Interfaces.IViewController;

import javax.swing.*;

public abstract class AbstractView<C extends IViewController> extends JPanel implements IView<C> {

    private C viewController;

    public AbstractView(C viewController) {
        this.viewController = viewController;
    }

    @Override
    public JComponent getView() {
        return this;
    }

    @Override
    public C getViewController() {
        return viewController;
    }

}
