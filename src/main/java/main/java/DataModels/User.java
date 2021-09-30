package main.java.DataModels;

public class User {

    public String firstName, lastName, email, password;
    public  int id, userTypeId;

    public User()
    {

    }

    public User(int userType, String firstname, String lastname, String email, String password) {
        this.userTypeId = userType;
        this.firstName = firstname;
        this.lastName = lastname;
        this.password = password;
        this.email = email;
    }
}
