package main.java.Managers;

import main.java.DataModels.User;
import main.java.Utilities.SqlConnection;

import java.util.HashMap;


public class UserManager {

    public User ActiveUser = null; //TODO: Lookup nameing convention casing: is it pascal or camal
    public HashMap<String, Integer> UserTypeIdMapping = new HashMap<>();

    public UserManager(String userName, String password)
    {
        UserTypeIdMapping.put("Guest", 1);
        UserTypeIdMapping.put("Hotel Clerk", 2);
        UserTypeIdMapping.put("SysAdmin", 3);

        //ActiveUser = loginUser
    }

    //Jacoby
    //public User loginUser(userName, password)   //this should call SqlConnection.validateAndGetUser

    //public void logoutUser() //take active user and set it to null

    //create a hotel clerk account
    public User createClerkUser(User clerk) //Something only a sysAdmin can do
    {
        if(ActiveUser != null && ActiveUser.userTypeId != 3) //TODO: firgure out syntax: UserTypeIdMapping["SysAdmin"]
        {
            return null;
        }
        return SqlConnection.createUser(clerk);
    }

    //Jacoby
    //Modify own user account. Check to make sure permission is granted to do modification.

    /*
        bool available = true;
        RoomManager.changeRoomStatus(roomId, available)

     */




}
