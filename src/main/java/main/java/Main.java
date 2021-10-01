package main.java;

import main.java.DataModels.User;
import main.java.Utilities.SqlConnection;
import main.java.Managers.UserManager;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        //UserManager User = new UserManager("hj@gmail.com", "testPassword1!");
//        User.modifyUserType(User.activeUser, "john.smith@gmail.com", 2);
//        User.modifyUser(User.activeUser, "Harry", "Johnson", "hj@gmail.com", "testPassword1!");
//        User.createClerkUser(User.activeUser);
//        User user = new User("Harry", "Johnson", "hj@gmail.com");
//        user.password = "testPassword1!";
//        SqlConnection.createUser(user);
//        SqlConnection.validateUserCredentials(user.email, user.password);
//        SqlConnection.getUserType(user.id);
//        System.out.println(User.activeUser.id);

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
            }
            else if(command.equals("logout")){
                mainUser.logoutUser();
                mainUser = null;
                System.out.println("Logout successful!");
            }
            else if(command.equals("create-clerk")){
                System.out.println("test");
            }


            System.out.println("Enter command: ");
            command = scanner.nextLine();
        }
        System.out.println("finish");
    }

}