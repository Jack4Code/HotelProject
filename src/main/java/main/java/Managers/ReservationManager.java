package main.java.Managers;

import main.java.DataModels.Room;
import main.java.Utilities.SqlConnection;
import main.java.DataModels.Reservation;
import main.java.DataModels.User;

import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import static java.sql.Date.valueOf;

public class ReservationManager {

    public Reservation activeReservation = null;

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

    public static ArrayList<Room> getAllAvailableRoomsByDateRange(String fromDate, String toDate) {
        //From UI, enter date parameters
        // Pass them in here, to Sqlconnection
        // Which will return an array list of available rooms
        // Todo: Do we want the array list to JUSt return the room Id's, or all it's info?
        //Then move to makeReservation

        //fromDate = 2021-12-02;

        //LocalDate fromDate = LocalDate.parse(fromDateInput);
        //LocalDate toDate = LocalDate.parse(toDateInput);
        //System.out.println(fromDate);
        //System.out.println(toDate);
        //Date fromDate1 = valueOf(fromDate);
        //Date toDate1 = valueOf(toDate);


        //java.sql.Date sfDate = new java.sql.Date(fDate.getTime());
        //java.sql.Date stDate = new java.sql.Date(tDate.getTime());
        System.out.println("test- got to reservation manager");

        ArrayList<Room> availableRooms = SqlConnection.getAllAvailableRoomsByDateRange(fromDate, toDate);

        //if (availableRooms.isEmpty()){
            //System.out.println("No rooms are available within those dates.");
        //}

        return availableRooms;
    }

    public static void makeReservation(String userEmail, LocalDate fromDate, LocalDate toDate, String roomType, int numberOfBeds, String bedType, int isSmoking)
    {
        User userToReserveFor;

        if(toDate.compareTo(fromDate) < 0){
            return;
        }

        userToReserveFor = SqlConnection.getUserByUsername(userEmail);

        if (userToReserveFor.firstName != null) {

            String reservationCode = createReservationCode();

            SqlConnection.createReservation(reservationCode, userToReserveFor.firstName, userToReserveFor.lastName, fromDate, toDate, roomType, numberOfBeds, bedType, isSmoking, userToReserveFor.email);
        }
        else {
            System.out.println("Error, user not authenticated.");
        }
    }

    public static String modifyReservation(LocalDate fromDate, LocalDate toDate, String roomType, int numberOfBeds, String bedType, int isSmoking, String passedInReservationCode)
    {
        if(toDate.compareTo(fromDate) < 0){
            return "";
        }

        SqlConnection.modifyReservation(passedInReservationCode, fromDate, toDate, roomType, numberOfBeds, bedType, isSmoking);

        return passedInReservationCode;
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

    public static ArrayList<Reservation> getAllReservations(User activeUser, String reservationCode, String email){
        return SqlConnection.getAllReservations(activeUser, reservationCode, email);
    }

    public static Object[][] getAvailableRoomCombos(LocalDate fromDate, LocalDate toDate){

        ArrayList<Room> allRoomCombosAvailable;
        ArrayList<Room> roomCombosFromReservations;

        allRoomCombosAvailable = SqlConnection.getRoomCombos();

        Object[][] allRoomCombosData = new Object[allRoomCombosAvailable.size()][5];

        for (int i = 0; i < allRoomCombosAvailable.size(); i++) {
            allRoomCombosData[i][0] = allRoomCombosAvailable.get(i).isAvailable;
            allRoomCombosData[i][1] = allRoomCombosAvailable.get(i).roomType;
            allRoomCombosData[i][2] = allRoomCombosAvailable.get(i).numBeds;
            allRoomCombosData[i][3] = allRoomCombosAvailable.get(i).bedType;
            allRoomCombosData[i][4] = allRoomCombosAvailable.get(i).isSmoking;
        }

        String fromDateString = fromDate.toString();
        String toDateString = toDate.toString();

        roomCombosFromReservations = SqlConnection.getCombosFromReservations(fromDateString , toDateString);

        Object[][] roomCombosFromReservationsData = new Object[roomCombosFromReservations.size()][5];

        for (int i = 0; i < roomCombosFromReservations.size(); i++) {
            roomCombosFromReservationsData[i][0] = roomCombosFromReservations.get(i).isAvailable;
            roomCombosFromReservationsData[i][1] = roomCombosFromReservations.get(i).roomType;
            roomCombosFromReservationsData[i][2] = roomCombosFromReservations.get(i).numBeds;
            roomCombosFromReservationsData[i][3] = roomCombosFromReservations.get(i).bedType;
            roomCombosFromReservationsData[i][4] = roomCombosFromReservations.get(i).isSmoking;
        }

        int deleteCount = -1;

        int roomComboSize = allRoomCombosAvailable.size();
        for(int i = 0; i < roomComboSize; i++) {

            deleteCount++;

            for(int j = 0; j < roomCombosFromReservations.size(); j++) {

                if(allRoomCombosData[i][1].equals(roomCombosFromReservationsData[j][1]) && allRoomCombosData[i][2] == roomCombosFromReservationsData[j][2] && allRoomCombosData[i][3].equals(roomCombosFromReservationsData[j][3]) && allRoomCombosData[i][4] == roomCombosFromReservationsData[j][4]) {

                    if(allRoomCombosData[i][0] == roomCombosFromReservationsData[j][0]) {
                        allRoomCombosAvailable.remove(deleteCount);
                        deleteCount--;
                        break;
                    }
                    else {
                        allRoomCombosAvailable.get(deleteCount).isAvailable = Integer.parseInt(allRoomCombosData[i][0].toString()) - Integer.parseInt(roomCombosFromReservationsData[j][0].toString());
                        break;
                    }
                }
            }
        }

        Object[][] availableRoomCombos = new Object[allRoomCombosAvailable.size()][5];

        for (int i = 0; i < allRoomCombosAvailable.size(); i++) {
            availableRoomCombos[i][0] = allRoomCombosAvailable.get(i).isAvailable;
            availableRoomCombos[i][1] = allRoomCombosAvailable.get(i).roomType.substring(0,1).toUpperCase() + allRoomCombosAvailable.get(i).roomType.substring(1);
            availableRoomCombos[i][2] = allRoomCombosAvailable.get(i).numBeds;
            availableRoomCombos[i][3] = allRoomCombosAvailable.get(i).bedType;
            if (allRoomCombosAvailable.get(i).isSmoking == 0) {
                availableRoomCombos[i][4] = "No";
            }
            else {
                availableRoomCombos[i][4] = "Yes";
            }
        }

        return availableRoomCombos;
    }
    //returns String: reservationCode ... needs coresponding SqlConnection method

    //upateReservation(reservationCode, whatever we can update) ... needs corresponding SqlConnection method

    public static boolean checkInReservation(String reservationCode, int reservationId, double billingAmt){
        String billingCode = createReservationCode();
        return SqlConnection.checkInReservation(reservationCode) && SqlConnection.createBill(billingCode, reservationId, billingAmt);
    }

    public static boolean checkOutReservation(String reservationCode){
        return SqlConnection.checkOutReservation(reservationCode);
    }

    public static boolean cancelReservation(String reservationCode) {
        return SqlConnection.cancelReservation(reservationCode);
    }

    public static Reservation getReservationByReservationCode(String reservationCode) {
        return SqlConnection.getReservationByReservationCode(reservationCode);
    }
}
