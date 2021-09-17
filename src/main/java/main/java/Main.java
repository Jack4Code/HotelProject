package main.java;


import main.java.DataModels.User;

public class Main {

    public static void main(String[] args) {
        boolean validUser = SqlConnection.validateUser("jack@giannini.org", "testPassword1!");
        System.out.println(validUser ? "User found!" : "Not a valid user password combination!");
    }

}



