package main.java;

import main.java.DataModels.Room;
import main.java.DataModels.User;
import main.java.Managers.ReservationManager;
import main.java.UI.LoginRegisterView;
import main.java.UI.PortalView;
import main.java.Utilities.SqlConnection;
import main.java.Managers.UserManager;


import javax.swing.*;
import javax.swing.plaf.basic.BasicLookAndFeel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        //ArrayList<Room> availableRes = ReservationManager.getAllCurrentlyAvailableRooms();
        ArrayList<Room> availableRooms = SqlConnection.getAllAvailableRoomsByDateRange();

        UIDefaults defaults = UIManager.getLookAndFeelDefaults();
        if (defaults.get("Table.alternateRowColor") == null)
            defaults.put("Table.alternateRowColor", new Color(240, 240, 240));
        if (args.length == 2) {
            try {
                runUIFromMainPortal(args[0], args[1]);
            } catch (Exception ex) {
                runUIFromLogin();
            }
        } else {
            runUIFromLogin();
        }
    }

    public static void runUIFromLogin() {
        new LoginRegisterView();
    }

    public static void runUIFromMainPortal(String username, String password) throws Exception {
        UserManager user = new UserManager(username, password);
        if (user.activeUser == null) {
            throw new Exception("Invalid login attempt");
        } else {
            new PortalView(user);
        }
    }

    public static void runConsole() {
        String command = "";
        Scanner scanner = new Scanner(System.in);


        System.out.println("Enter command: ");
        UserManager mainUser = null;
        command = scanner.nextLine();
        while (!command.equals("exit")) {
            System.out.println("input is " + command);

            if (command.equals("login")) {
                System.out.println("Enter username: ");
                String username = scanner.nextLine();
                System.out.println("Enter password: ");
                String password = scanner.nextLine();


                try {
                    mainUser = new UserManager(username, password);
                    System.out.println("Log in successful!");
                } catch (Exception ex) {
                    System.out.println("Invalid login attempt!");
                }
            } else if (command.equals("register")) {
                System.out.println("Please enter firstname: ");
                String firstname = scanner.nextLine();
                System.out.println("Please enter lastname: ");
                String lastname = scanner.nextLine();
                System.out.println("Please enter email: ");
                String email = scanner.nextLine();
                System.out.println("Please enter password: ");
                String password = scanner.nextLine();
                System.out.println(UserManager.registerUser(firstname, lastname, email, password) ? "Register Successful!" : "Registration Failed!");
            } else if (command.equals("logout")) {
                mainUser.logoutUser();
                mainUser = null;
                System.out.println("Logout successful!");
            }


            System.out.println("Enter command: ");
            command = scanner.nextLine();
        }
        System.out.println("finish");
    }

}