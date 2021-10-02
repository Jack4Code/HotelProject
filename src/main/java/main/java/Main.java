package main.java;

import main.java.DataModels.User;
import main.java.Utilities.SqlConnection;
import main.java.Managers.UserManager;
import main.java.DataModels.Room;
import main.java.Managers.RoomManager;

import java.sql.SQLOutput;
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

                //Todo: Make sure a SysAdmin is logged in and activeUser != null
                System.out.println("Please enter firstname: ");
                String firstname = scanner.nextLine();
                System.out.println("Please enter lastname: ");
                String lastname = scanner.nextLine();
                System.out.println("Please enter email: ");
                String email = scanner.nextLine();
                System.out.println("Please enter password: ");
                String password = scanner.nextLine();
                User clerk = new User(2, firstname, lastname, email, password);
                System.out.println(SqlConnection.createUser(clerk));
            }
            //Todo: Create update room command - Jake
            else if(command.equals("get-room")){
                System.out.println("Room Number: ");
                int roomId = scanner.nextInt();
                System.out.println(RoomManager.lookUpRoomById(roomId));

            }
            else if(command.equals("modify-room")){
                System.out.println("Room Number: ");
                int roomId = scanner.nextInt();
                //System.out.println(RoomManager.lookUpRoomById(roomId));
                Room x = RoomManager.lookUpRoomById(roomId);
                System.out.println("isAvailable (true/false): ");
                boolean newIsAvailable = scanner.nextBoolean();
                System.out.println("RoomType: ");
                String newRoomType = scanner.nextLine();
                System.out.println("Number of Beds: ");
                int newNumBeds = scanner.nextInt();
                System.out.println("Bed Type: ");
                String newBedType = scanner.nextLine();
                System.out.println(SqlConnection.modifyRoom(x, newIsAvailable, newRoomType, newNumBeds, newBedType));
                //Todo: I think my SQL query isn't correct. Everything works except the strings newRoomType, newBedType
            }




            System.out.println("Enter command: ");
            command = scanner.nextLine();
        }
        System.out.println("finish");
    }

}