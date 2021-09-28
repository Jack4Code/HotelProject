package main.java.Managers;

import main.java.DataModels.User;
import main.java.Utilities.SqlConnection;

import java.util.EmptyStackException;
import java.util.HashMap;


public class UserManager {

    public User activeUser = null;
    public HashMap<String, Integer> userTypeIdMapping = new HashMap<>();

    public UserManager(String userName, String password)
    {
        userTypeIdMapping.put("Guest", 1);
        userTypeIdMapping.put("Hotel Clerk", 2);
        userTypeIdMapping.put("SysAdmin", 3);

        activeUser = loginUser(userName, password);
        if(activeUser == null)
        {
            throw new EmptyStackException();
        }
    }

    //Jacoby
    public User loginUser(String userName, String password)
    {
        return SqlConnection.validateAndGetUser(userName, password);
    }

    public void logoutUser()
    {
        activeUser = null;
    } //take active user and set it to null

    //create a hotel clerk account
    public User createClerkUser(User clerk) //Something only a sysAdmin can do
    {
        if(activeUser != null && activeUser.userTypeId != userTypeIdMapping.get("SysAdmin"))
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
