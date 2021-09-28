package main.java;

import main.java.AppUI.TestFrame;
import main.java.DataModels.User;
import main.java.Utilities.SqlConnection;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                TestFrame frame = new TestFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);;
            }
        });
    }
}



