package main.java.DataModels;

import java.time.LocalDate;

public class Billing {
    public int ID;
    public String userEmail, reservationCode, billingCode, firstName, lastName;
    public LocalDate checkInDate, checkOutDate;
    public float dailyFee, additionalFee, totalCost; //Todo: Do we want to do these additional fees?..

    public Billing(){

    }

    public Billing(String firstName, String lastName, String userEmail, String billingCode, String reservationCode, LocalDate checkInDate, LocalDate checkOutDate, float totalCost){
        this.firstName = firstName;
        this.lastName = lastName;
        this.userEmail = userEmail;
        this.billingCode = billingCode;
        this.reservationCode = reservationCode;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalCost = totalCost;
        //Todo: Add more about room info?..


    }


}
