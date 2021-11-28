package main.java.DataModels;

import java.time.LocalDate;

public class Billing {
    public int ID;
    public String userEmail, reservationCode, billingCode, firstName, lastName;
    public LocalDate checkInDate, checkOutDate, dateCheckedOut;
    public float totalCost; //Todo: Do we want to do these additional fees?..

    public Billing(){

    }

    public Billing(String billingCode, String reservationCode, String firstName, String lastName, String userEmail, LocalDate checkInDate, LocalDate checkOutDate, LocalDate dateCheckedOut, float totalCost){
        this.firstName = firstName;
        this.lastName = lastName;
        this.userEmail = userEmail;
        this.billingCode = billingCode;
        this.reservationCode = reservationCode;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.dateCheckedOut = dateCheckedOut; //This is the confirmed date the guest checked out
        this.totalCost = totalCost;



    }


}
