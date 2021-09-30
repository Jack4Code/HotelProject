package main.java.Managers;

import main.java.Utilities.SqlConnection;



public class RoomManager {

    //Jake

    //Todo: First check to see if the proper user is logged in?

    public static boolean lookUpRoom(int roomId) {
        //Todo: Whats the return type for this method?

        //Have option to only show numbers from 1-40
        return SqlConnection.getRoomInfo(roomId);
    }

}
