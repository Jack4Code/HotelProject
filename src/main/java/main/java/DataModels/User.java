package main.java.DataModels;

public class User {

    public String firstName, lastName, email, password;
    public  int id, userTypeId;

    public User(String email, String password) {
        //this.firstName = firstname;
        //this.lastName = lastname;
        this.password = password;
        this.email = email;
        this.userTypeId = 1;
    }
}
