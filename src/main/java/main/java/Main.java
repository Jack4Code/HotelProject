package main.java;

import main.java.AppUI.AppMain;
import main.java.AppUI.TestFrame;
import main.java.DataModels.User;
import main.java.Utilities.SqlConnection;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                TestFrame frame = new TestFrame();
//                frame.setPreferredSize(new Dimension(1500, 1000)); //set prefered size initially
//                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                frame.pack();
//                frame.setLocationRelativeTo(null);
//                frame.setVisible(true);
//                //frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);  //maximize initially
//            }
//        });
        RunApp();

    }

    public static void RunApp() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
//                try {
//                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
//                    ex.printStackTrace();
//                }

                JFrame frame = new JFrame("Hotel Management");
                frame.setPreferredSize(new Dimension(1500, 1000));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new AppMain());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
            }
        });
    }
}



