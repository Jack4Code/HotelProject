package main.java.Managers;

import main.java.DataModels.Room;
import main.java.Utilities.SqlConnection;

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

    //getAllAvailableRoomsByDateRange(DateTime fromDate, DateTime toDate) { //returns list of available rooms available at that time. It will call SqlConnection method }

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

    //returns String: reservationCode ... needs coresponding SqlConnection method

    //upateReservation(reservationCode, whatever we can update) ... needs corresponding SqlConnection method


}
