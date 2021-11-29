package main.java.Managers;

import main.java.DataModels.Billing;
import main.java.DataModels.User;
import main.java.Utilities.SqlConnection;

import java.security.SecureRandom;
import java.util.ArrayList;

public class BillingManager {



    public static ArrayList<Billing> getBilling(String email){

        return SqlConnection.getBilling(email);

    }


}
