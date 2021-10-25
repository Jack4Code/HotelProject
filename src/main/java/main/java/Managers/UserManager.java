package main.java.Managers;

import main.java.DataModels.User;
import main.java.Utilities.SqlConnection;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UserManager {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public User activeUser = null;
    public HashMap<String, Integer> userTypeIdMapping = new HashMap<>();

    public UserManager(String userName, String password)
    {
        userTypeIdMapping.put("Guest", 1);
        userTypeIdMapping.put("Hotel Clerk", 2);
        userTypeIdMapping.put("SysAdmin", 3);

        activeUser = loginUser(userName, password);
//        if(activeUser == null)
//        {
//            throw new EmptyStackException();
//        }
    }

    public static boolean isValidEmail(String email){
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    public static boolean registerUser(String firstname, String lastname, String email, String password) {//for sign up as guest
        boolean isRegisterSuccesss = true;

        if(!isValidEmail(email)){
            return false;
        }

        User userToSignup = new User(1, firstname, lastname, email, password);
        try{
            SqlConnection.createUser(userToSignup);
        }
        catch(Exception ex){
            isRegisterSuccesss = false;
        }

        if(userToSignup.id == 0){
            isRegisterSuccesss = false;
        }

        return isRegisterSuccesss;
    }

    public User loginUser(String userName, String password)
    {
        return SqlConnection.validateAndGetUser(userName, password);
    }

    public void logoutUser()
    {
        activeUser = null;
    }

    //create a hotel clerk account
    public boolean createClerkUser(User activeUser, String firstName, String lastName, String email, String password) //TODO: refactor...don't need to pass activeUser
    {
        if(!isValidEmail(email)){
            return false;
        }
        if(activeUser != null && activeUser.userTypeId != userTypeIdMapping.get("SysAdmin"))
        {
            return false;
        }
        User clerk = new User(2, firstName, lastName, email, password);
        return SqlConnection.createUser(clerk);
    }

    public User modifyUser(User activeUser, String newFirstName, String newLastName, String newEmail, String newPassword)
    {
        if(activeUser == null)
        {
            return null;
        }
        return SqlConnection.modifyUser(activeUser, newFirstName, newLastName, newEmail, newPassword);
    }

    public User modifyUserType(User activeUser, String usernameToChange, int newUserType)
    {
        if(activeUser != null && activeUser.userTypeId != userTypeIdMapping.get("SysAdmin")) //TODO: 2nd release, sysAdmin can change userTypes
        {
            return null;
        }
        User userToChange = SqlConnection.getUserByUsername(usernameToChange);
        return SqlConnection.modifyUserType(userToChange, newUserType);
    }



}
