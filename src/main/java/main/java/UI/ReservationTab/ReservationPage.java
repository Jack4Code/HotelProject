package main.java.UI.ReservationTab;

import main.java.Managers.ReservationManager;
import main.java.DataModels.Reservation;
import main.java.DataModels.User;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

import main.java.UI.Resources.CustomColor;
import main.java.Utilities.TableCellListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class ReservationPage {

    static ArrayList<Reservation> reservations;
    static ArrayList<String> reservationDetailsString;
    static ArrayList<Integer> reservationDetailsInt;
    static ArrayList<LocalDate> reservationDetailsDate;


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

        //reservationTable.setBounds(25, 160, 1150, 440);
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
        //TODO Why is this code repeated twice??
        reservationTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {

                int selectedRow;
                reservationDetailsString = new ArrayList<String>(6);
                reservationDetailsDate = new ArrayList<LocalDate>(2);
                reservationDetailsInt = new ArrayList<Integer>(2);

                selectedRow = reservationTable.getSelectedRow();
                reservationDetailsString.add(reservationTable.getValueAt(selectedRow, 1).toString());
                reservationDetailsString.add(reservationTable.getValueAt(selectedRow, 2).toString());
                reservationDetailsString.add(reservationTable.getValueAt(selectedRow, 3).toString());
                reservationDetailsString.add(reservationTable.getValueAt(selectedRow, 4).toString());
                LocalDate tempDateIn = (LocalDate) reservationTable.getValueAt(selectedRow, 5);
                reservationDetailsDate.add(tempDateIn);
                LocalDate tempDateOut = (LocalDate) reservationTable.getValueAt(selectedRow, 6);
                reservationDetailsDate.add(tempDateOut);
                reservationDetailsString.add(reservationTable.getValueAt(selectedRow, 7).toString());
                int tempInt1 = (int) reservationTable.getValueAt(selectedRow, 8);
                reservationDetailsInt.add(tempInt1);
                reservationDetailsString.add(reservationTable.getValueAt(selectedRow, 9).toString());
                String smoking = reservationTable.getValueAt(selectedRow, 10).toString();
                if (smoking.equals("Yes"))
                {
                    int tempInt2 = 1;
                    reservationDetailsInt.add(tempInt2);
                }
                else {
                    int tempInt2 = 0;
                    reservationDetailsInt.add(tempInt2);
                }
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
        modifyReservation.setBounds(512, 625, 220, 50);
        modifyReservation.setBackground(CustomColor.PURPLE_THEME_TXT);
        modifyReservation.setForeground(CustomColor.LOGIN_CONTAINER_THEME);

        return modifyReservation;
    }

    public static JButton checkInReservationBtn(){
        JButton checkIn = new JButton("Check In");

        checkIn.setFocusable(false);
        checkIn.setFont(new Font("serif", Font.PLAIN, 30));
        checkIn.setBounds(25, 625, 220, 50);
        checkIn.setBackground(CustomColor.PURPLE_THEME_TXT);
        checkIn.setForeground(CustomColor.LOGIN_CONTAINER_THEME);

        return checkIn;
    }

    public static JButton checkOutReservationBtn(){
        JButton checkOut = new JButton("Check Out");

        checkOut.setFocusable(false);
        checkOut.setFont(new Font("serif", Font.PLAIN, 30));
        checkOut.setBounds(270, 625, 220, 50);
        checkOut.setBackground(CustomColor.PURPLE_THEME_TXT);
        checkOut.setForeground(CustomColor.LOGIN_CONTAINER_THEME);

        return checkOut;
    }

    public static JButton cancelReservationBtn()
    {
        JButton cancelReservation = new JButton("Cancel");
        cancelReservation.setFocusable(false);
        cancelReservation.setFont(new Font("serif", Font.PLAIN, 30));
        cancelReservation.setBounds(757, 625, 220, 50);
        cancelReservation.setBackground(CustomColor.PURPLE_THEME_TXT);
        cancelReservation.setForeground(CustomColor.LOGIN_CONTAINER_THEME);

        return cancelReservation;
    }

    public static Reservation selectedReservation()
    {
        Reservation activeReservation = new Reservation(reservationDetailsString.get(0), reservationDetailsString.get(1), reservationDetailsString.get(2), reservationDetailsString.get(3), reservationDetailsDate.get(0), reservationDetailsDate.get(1), reservationDetailsString.get(4), reservationDetailsInt.get(0), reservationDetailsString.get(5), reservationDetailsInt.get(1));

        return activeReservation;
    }
}
