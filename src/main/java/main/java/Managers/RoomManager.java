package main.java.Managers;

import main.java.DataModels.Room;
import main.java.Utilities.SqlConnection;

import java.util.ArrayList;


public class RoomManager {


    public static Room lookUpRoomById(int roomId) {
        //Todo: Is this being used?
        return SqlConnection.getRoomById(roomId);
    }

    public static ArrayList<Room> getAllRooms(){
        return SqlConnection.getAllRooms();
    }

    public static boolean updateRoom(Room room){
        return SqlConnection.updateRoom(room);
    }

}
