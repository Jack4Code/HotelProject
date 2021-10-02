package main.java.Managers;

import main.java.DataModels.Room;
import main.java.Utilities.SqlConnection;



public class RoomManager {

    public static Room lookUpRoomById(int roomId) {
        //Todo: Verifying the credentials of a hotel clerk should be done here first

        return SqlConnection.getRoomById(roomId);
    }

    public static boolean checkRoomCredentials(Room room){
        boolean checkClerkCredentials = true;
       //Todo: Ask Jack about error - "non static method cannot be referenced from a static context"
        //Todo: Trying to use a boolean return type with a Room return type

        return checkClerkCredentials;
    }

    public Room modifyRoom(Room room, boolean newIsAvailable, String newRoomType, int newNumBeds, String newBedType){


        return SqlConnection.modifyRoom(room, newIsAvailable, newRoomType, newNumBeds, newBedType);
    }

}
