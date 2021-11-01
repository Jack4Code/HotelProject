package main.java.Managers;

import main.java.DataModels.Room;
import main.java.Utilities.SqlConnection;
import main.java.DataModels.Reservation;

import java.lang.reflect.Array;
import java.security.SecureRandom;
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

    public String makeReservation(Date fromDate, Date toDate)
    {

        String reservationCode = createReservationCode();

        System.out.println(reservationCode);

        return reservationCode;
    }

    public static String createReservationCode()
    {

        String charList = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom sr = new SecureRandom();

        StringBuilder tempString = new StringBuilder(6);
        for(int i = 0; i < 6; i++)
            tempString.append(charList.charAt(sr.nextInt(charList.length())));
        return tempString.toString();

    }

    public static ArrayList<Reservation> getAllReservations(String reservationCode, String email){
        return SqlConnection.getAllReservations(reservationCode, email);
    }

    //returns String: reservationCode ... needs coresponding SqlConnection method

    //upateReservation(reservationCode, whatever we can update) ... needs corresponding SqlConnection method


}
