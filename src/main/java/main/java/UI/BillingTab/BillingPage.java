package main.java.UI.BillingTab;

import main.java.DataModels.Billing;
import main.java.DataModels.User;
import main.java.Managers.BillingManager;
import main.java.UI.Resources.CustomColor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Locale;

public class BillingPage {

    static ArrayList<Billing> billingList;

    public static JScrollPane generateBillingTable(){
        //Getting all users who have/had a reservation

        JScrollPane pane;
        JTable billingTable;

        //Todo: Think about removing the billing code
        billingList = BillingManager.getBillingForAllUsers("", "");

        String[] billingColumnNames = {
                "Id",
                "First Name",
                "Last Name",
                "Email",
                "Reservation Code",
                "Check In Date",
                "Check Out Date",
                "Billing Code",
                "Total Cost"
        };

        Object[][] data = new Object[billingList.size()][9]; //Todo: Second bracket is size of columns. If you modify columns, change this

        for (int i=0; i < billingList.size(); i++){
            data[i][0] = billingList.get(i).ID;
            data[i][1] = billingList.get(i).firstName.substring(0,1).toUpperCase() + billingList.get(i).firstName.substring(1).toLowerCase();
            data[i][2] = billingList.get(i).lastName.substring(0,1).toUpperCase() + billingList.get(i).lastName.substring(1).toLowerCase();
            data[i][3] = billingList.get(i).userEmail;
            data[i][4] = billingList.get(i).reservationCode;
            data[i][5] = billingList.get(i).checkInDate;
            data[i][6] = billingList.get(i).checkOutDate;
            data[i][7] = billingList.get(i).billingCode;
            data[i][8] = billingList.get(i).totalCost;
        }

        billingTable = new JTable(data, billingColumnNames){
            @Override
            public boolean isCellEditable(int row, int column){return false;} //Todo: Verify this works
        };

        billingTable.setFont(new Font("serif", Font.PLAIN, 18));
        billingTable.setRowHeight(25);

        //Todo: Maybe modify column widths- see ReservationPage if needed

        pane = new JScrollPane(billingTable);
        pane.setBounds(25,160,1150,440); //Todo: Play with these values







        return pane;
    }



    public static JLabel addTitle() {
        JLabel tableTitle = new JLabel("Generate Billing for Guest(s): ");
        tableTitle.setFont(new Font("serif", Font.PLAIN, 24));
        tableTitle.setForeground(CustomColor.PORTAL_TOP_BAR);
        tableTitle.setBounds(30, 60, 700, 35);
        return tableTitle;
    }

    public static JButton addGetUserBillButton()
    {
        JButton getUserBill = new JButton("Get Bill");
        getUserBill.setFocusable(false);
        getUserBill.setFont(new Font("serif", Font.PLAIN, 28));
        getUserBill.setBounds(30, 625, 305, 50);
        getUserBill.setBackground(CustomColor.PURPLE_THEME_TXT);
        getUserBill.setForeground(CustomColor.LOGIN_CONTAINER_THEME);

        return getUserBill;
    }

    public static JButton addGetAllBillsButton()
    {
        JButton getAllBills = new JButton("Get All Bills");
        getAllBills.setFocusable(false);
        getAllBills.setFont(new Font("serif", Font.PLAIN, 28));
        getAllBills.setBounds(365, 625, 305, 50);
        getAllBills.setBackground(CustomColor.PURPLE_THEME_TXT);
        getAllBills.setForeground(CustomColor.LOGIN_CONTAINER_THEME);

        return getAllBills;
    }




}
