package main.java.Managers;

import main.java.DataModels.Room;
import main.java.Utilities.SqlConnection;



public class RoomManager {

    //Todo: First check to see if the hotel clerk is the user logged in
    //Todo: Do we want extra functionality to create and delete rooms? Not needed but...

    public static Room lookUpRoomById(int roomId) {

        return SqlConnection.getRoomById(roomId);
    }


    public static boolean updateRoom(Room room){
        boolean isUpdateSuccessfull = true;

        //implement

        return isUpdateSuccessfull;
    }

}
