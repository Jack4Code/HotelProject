package main.java.UI.BillingTab;

import main.java.DataModels.Billing;
import main.java.DataModels.User;
import main.java.Managers.BillingManager;
import main.java.UI.Resources.CustomColor;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;
import java.util.Locale;

public class BillingPage {

    static ArrayList<Billing> billingList;



    public static JScrollPane generateBillingTable(String email){


        JScrollPane pane;
        JTable billingTable;

        //Todo: Think about removing the billing code
        billingList = BillingManager.getBilling(email);

        String[] billingColumnNames = {
                "Billing Code",
                "Reservation Code",
                "First Name",
                "Last Name",
                "Email",
                "Check In Date",
                "Check Out Date",
                "Total Cost"
        };

        Object[][] data = new Object[billingList.size()][8];

        for (int i=0; i < billingList.size(); i++){
            data[i][0] = billingList.get(i).billingCode;
            data[i][1] = billingList.get(i).reservationCode;
            data[i][2] = billingList.get(i).firstName.substring(0,1).toUpperCase() + billingList.get(i).firstName.substring(1).toLowerCase();
            data[i][3] = billingList.get(i).lastName.substring(0,1).toUpperCase() + billingList.get(i).lastName.substring(1).toLowerCase();
            data[i][4] = billingList.get(i).userEmail;
            data[i][5] = billingList.get(i).checkInDate;
            data[i][6] = billingList.get(i).dateCheckedOut;
            data[i][7] = "$" + billingList.get(i).totalCost;
        }

        billingTable = new JTable(data, billingColumnNames){
            @Override
            public boolean isCellEditable(int row, int column){return false;}
        };

        billingTable.setFont(new Font("serif", Font.PLAIN, 18));
        billingTable.setRowHeight(25);

        //First Name
        TableColumn column3 = billingTable.getColumnModel().getColumn(2);
        column3.setPreferredWidth(50);

        //Last Name
        TableColumn column4 = billingTable.getColumnModel().getColumn(3);
        column4.setPreferredWidth(50);

        //Email
        TableColumn column5 = billingTable.getColumnModel().getColumn(4);
        column5.setPreferredWidth(150);

        //Total Cost
        TableColumn column8 = billingTable.getColumnModel().getColumn(7);
        column8.setPreferredWidth(15);


        pane = new JScrollPane(billingTable);
        pane.setBounds(30,250,1150,400);





        return pane;
    }



    public static JLabel addTitle() {
        JLabel tableTitle = new JLabel("Generate Billing For Past Reservations: ");
        tableTitle.setFont(new Font("serif", Font.PLAIN, 24));
        tableTitle.setForeground(CustomColor.PORTAL_TOP_BAR);
        tableTitle.setBounds(30, 30, 700, 35);
        return tableTitle;
    }

    public static JLabel addEmailInputTitle(){
        JLabel emailInputTitle = new JLabel("Email: ");
        emailInputTitle.setFont(new Font("serif", Font.PLAIN, 20));
        emailInputTitle.setForeground(CustomColor.PORTAL_TOP_BAR);
        emailInputTitle.setBounds(415, 110, 350, 35);

        return emailInputTitle;
    }

    public static JTextField addGetEmailInput()
    {
        JTextField emailInput = new JTextField();
        emailInput.setLayout(null);
        //emailInput.setBackground(CustomColor.MAIN_PURPLE_THEME); //Todo: Change these colors
        emailInput.setBackground(Color.lightGray);
        emailInput.setForeground(CustomColor.PURPLE_THEME_TXT);
        //emailInput.setForeground(CustomColor.LOGIN_CONTAINER_THEME);
        emailInput.setFont(new Font("serif", Font.PLAIN, 18));
        emailInput.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));

        emailInput.setBounds(415, 150, 350, 35);

        return emailInput;
    }
    public static JButton addGetUserBillButton()
    {
        JButton getUserBill = new JButton("Get Bill");
        getUserBill.setFocusable(false);
        getUserBill.setFont(new Font("serif", Font.PLAIN, 22));
        getUserBill.setBounds(415, 200, 150, 35);
        getUserBill.setBackground(CustomColor.PURPLE_THEME_TXT);
        getUserBill.setForeground(CustomColor.LOGIN_CONTAINER_THEME);

        return getUserBill;
    }

    public static JButton addGetAllBillsButton()
    {
        JButton getAllBills = new JButton("Get All Bills");
        getAllBills.setFocusable(false);
        getAllBills.setFont(new Font("serif", Font.PLAIN, 22));
        getAllBills.setBounds(615, 200, 150, 35);
        getAllBills.setBackground(CustomColor.PURPLE_THEME_TXT);
        getAllBills.setForeground(CustomColor.LOGIN_CONTAINER_THEME);

        return getAllBills;
    }





}
