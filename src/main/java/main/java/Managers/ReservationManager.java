package main.java.Managers;

import main.java.DataModels.Room;
import main.java.Utilities.SqlConnection;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ReservationManager {

    public static ArrayList<Room> getAllCurrentlyAvailableRooms()
    {
        //Get all rooms
        ArrayList<Room> allRooms = SqlConnection.getAllRooms();
        ArrayList<Room> availableRooms = new ArrayList<>();

        //Get all available rooms
        for (int i = 0; i < allRooms.size(); i++){
            if (allRooms.get(i).isAvailable == 1){
                availableRooms.add(allRooms.get(i));
            }
        }

        return availableRooms;

    }




}
