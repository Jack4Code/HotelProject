package main.java.UI.BillingTab;

import main.java.UI.Resources.CustomColor;

import javax.swing.*;
import java.awt.*;

public class BillingPage {





    public static JLabel addTitle()
    {
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
