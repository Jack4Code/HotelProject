package main.java;

import main.java.DataModels.User;
import main.java.Utilities.SqlConnection;

public class Main {

    public static void main(String[] args) {

        User user = new User("Harry", "Johnson", "hj@gmail.com");
        user.password = "testPassword1!";

        SqlConnection.createUser(user);
        SqlConnection.validateUserCredentials(user);
        SqlConnection.getUserType(user.id);

    }

}



