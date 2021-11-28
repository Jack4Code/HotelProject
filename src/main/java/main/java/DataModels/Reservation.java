package main.java.DataModels;

import java.time.LocalDate;
import java.util.Date;

public class Reservation {
    public int ID, numberOfBeds, isSmoking;
    public String userEmail, reservationCode, roomType, bedType, firstName, lastName;
    public LocalDate checkInDate, checkOutDate, DateCheckedIn, DateCheckedOut, DateCancelled;

    public Reservation(){

    }

    public Reservation(String reservationCode, String userEmail, String firstName, String lastName, LocalDate checkInDate, LocalDate checkOutDate, String RoomType, int NumBeds, String BedType, int isSmoking){
        this.reservationCode = reservationCode;
        this.userEmail = userEmail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.roomType = RoomType;
        this.numberOfBeds = NumBeds;
        this.bedType = BedType;
        this.isSmoking = isSmoking;
    }
}