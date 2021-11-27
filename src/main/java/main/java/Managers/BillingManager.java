package main.java.Managers;

import main.java.DataModels.Billing;
import main.java.DataModels.User;
import main.java.Utilities.SqlConnection;

import java.util.ArrayList;

public class BillingManager {

    public static ArrayList<Billing> getBillingForAllUsers(String billingCode, String email){
        //Todo: Active User must be clerk
        return SqlConnection.getBillingForAllUsers(billingCode, email);

        //Todo: We have a billing code...
        //Todo: This is just pulling a list. Should we have billing code created at check out, or during payment?
        //Todo: This would generate code everytime page is refreshed
    }



}
