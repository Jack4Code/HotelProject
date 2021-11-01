package main.java.UI.ReservationTab;

import main.java.Managers.ReservationManager;
import main.java.DataModels.User;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

import main.java.DataModels.Reservation;
import main.java.UI.Resources.CustomColor;
import main.java.Utilities.TableCellListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class ReservationPage {

    static String reservationCode;
    static ArrayList<Reservation> reservations;

    public static JScrollPane generateTable(User activeUser){
        JScrollPane pane;
        JTable reservationTable;

        reservations = ReservationManager.getAllReservations(activeUser, "", "");

        //Implement the table
        String[] reservationColumnNames = {
                "Id",
                "Reservation Code",
                "Email",
                "First Name",
                "Last Name",
                "Check In Date",
                "Check Out Date",
                "Room Type",
                "Bed Count",
                "Bed Type",
                "Smoking Available"
        };

        Object[][] data = new Object[reservations.size()][11];

        for (int i = 0; i < reservations.size(); i++) {
            data[i][0] = reservations.get(i).ID;
            data[i][1] = reservations.get(i).reservationCode;
            data[i][2] = reservations.get(i).userEmail;
            data[i][3] = reservations.get(i).firstName.substring(0, 1).toUpperCase() + reservations.get(i).firstName.substring(1).toLowerCase();
            data[i][4] = reservations.get(i).lastName.substring(0, 1).toUpperCase() + reservations.get(i).lastName.substring(1).toLowerCase();
            data[i][5] = reservations.get(i).checkInDate;
            data[i][6] = reservations.get(i).checkOutDate;
            data[i][7] = reservations.get(i).roomType.substring(0, 1).toUpperCase() + reservations.get(i).roomType.substring(1).toLowerCase();
            data[i][8] = reservations.get(i).numberOfBeds;
            data[i][9] = reservations.get(i).bedType.substring(0, 1).toUpperCase() + reservations.get(i).bedType.substring(1).toLowerCase();
            data[i][10] = reservations.get(i).isSmoking == 1 ? "Yes" : "No";
        }

        reservationTable = new JTable(data, reservationColumnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        reservationTable.setBounds(25, 160, 1150, 440);
        reservationTable.setFont(new Font("serif", Font.PLAIN, 18));
        reservationTable.setRowHeight(25);

        //Column Widths
        TableColumn column1 = reservationTable.getColumnModel().getColumn(0);
        column1.setPreferredWidth(15);

        TableColumn column8 = reservationTable.getColumnModel().getColumn(7);
        column8.setPreferredWidth(40);

        TableColumn column9 = reservationTable.getColumnModel().getColumn(8);
        column9.setPreferredWidth(30);

        TableColumn column10 = reservationTable.getColumnModel().getColumn(9);
        column10.setPreferredWidth(30);

        TableColumn column3 = reservationTable.getColumnModel().getColumn(2);
        column3.setPreferredWidth(150);

        pane = new JScrollPane(reservationTable);
        pane.setBounds(25, 160, 1150, 440);

        //Selects reservation on click
        reservationTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {

                int selectedRow;

                selectedRow = reservationTable.getSelectedRow();
                reservationCode = reservationTable.getValueAt(selectedRow, 1).toString();
            }
        });

        return pane;
    }

    public static JLabel addTitle()
    {
        JLabel tableTitle = new JLabel("Active Reservations: ");
        tableTitle.setFont(new Font("serif", Font.PLAIN, 24));
        tableTitle.setForeground(CustomColor.PORTAL_TOP_BAR);
        tableTitle.setBounds(30, 120, 700, 35);

        return tableTitle;
    }

    public static JButton addModifyButton()
    {
        JButton modifyReservation = new JButton("Modify");
        modifyReservation.setFocusable(false);
        modifyReservation.setFont(new Font("serif", Font.PLAIN, 30));
        modifyReservation.setBounds(80, 625, 320, 50);
        modifyReservation.setBackground(CustomColor.PURPLE_THEME_TXT);
        modifyReservation.setForeground(CustomColor.LOGIN_CONTAINER_THEME);

        return modifyReservation;
    }

    public static String selectedReservationCode()
    {
        return reservationCode;
    }
}
