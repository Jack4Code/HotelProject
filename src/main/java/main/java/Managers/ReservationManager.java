package main.java.Managers;

import main.java.DataModels.Room;
import main.java.Utilities.SqlConnection;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

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

    public static ArrayList<Room> getAllAvailableRoomsByDateRange(Date fromDate, Date toDate) {
        //From UI, enter date parameters
        // Pass them in here, to Sqlconnection
        // Which will return an array list of available rooms
        // Todo: Do we want the array list to JUSt return the room Id's, or all it's info?
        //Then move to makeReservation

        //fromDate = 2021-12-02;
        ArrayList<Room> availableRooms = SqlConnection.getAllAvailableRoomsByDateRange();

        if (availableRooms.isEmpty()){
            System.out.println("No rooms are available within those dates.");
        }

        return availableRooms;
    }

    //makeReservation(fromDate, toDate, ) returns String: reservationCode ... needs coresponding SqlConnection method

    //upateReservation(reservationCode, whatever we can update) ... needs corresponding SqlConnection method


}
