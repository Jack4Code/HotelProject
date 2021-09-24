package main.java.DataModels;

public class User {

    public String firstName, lastName, email, password;
    public  int id, userTypeId;

    public User(String firstname, String lastname, String email) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.email = email;
    }
}
