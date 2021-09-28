package main.java;

import main.java.DataModels.User;
import main.java.Utilities.SqlConnection;
import main.java.Managers.UserManager;

public class Main {

    public static void main(String[] args) {

          UserManager User = new UserManager("hj@gmail.com", "testPassword1!");
          User.loginUser("hj@gmail.com", "testPassword1!");
//        User user = new User("Harry", "Johnson", "hj@gmail.com");
//        user.password = "testPassword1!";
//
//        SqlConnection.createUser(user);
//        SqlConnection.validateUserCredentials(user.email, user.password);
//        SqlConnection.getUserType(user.id);
        System.out.println(User.ActiveUser.id);
    }

}