package main.java.Managers;

import main.java.DataModels.Billing;
import main.java.DataModels.User;
import main.java.Utilities.SqlConnection;

import java.security.SecureRandom;
import java.util.ArrayList;

public class BillingManager {

   /* public static ArrayList<Billing> getBillingForOneUser(String email){

        return SqlConnection.getBillingForOneUser(email);

    }*/

    public static ArrayList<Billing> getBillingForAllUsers(String billingCode, String email){

        return SqlConnection.getBillingForAllUsers(billingCode, email);

    }

    public static String createBillingCode(){
        String charList = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom sr = new SecureRandom();
        StringBuilder tempString = new StringBuilder(6);
        for (int i=0; i < 6; i++){
            tempString.append(charList.charAt(sr.nextInt(charList.length())));

        }
        return tempString.toString();
    }



}
