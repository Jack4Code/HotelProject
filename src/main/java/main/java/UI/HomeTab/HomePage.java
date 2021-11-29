package main.java.UI.HomeTab;

import main.java.DataModels.AvailableRoom;
import main.java.DataModels.Room;
import main.java.Managers.ReservationManager;
import main.java.UI.PortalView;
import main.java.UI.Resources.CustomColor;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class HomePage {


    ArrayList<AvailableRoom> availableRooms;

    Date currentDate;
    SimpleDateFormat timeFormat;
    static LocalDate localFromDate, localToDate;
    static JTable availableRoomTable;

    static ArrayList<Room> availableRoom;
    static Object[][] selectedRoomValues;
    static Object[][] data;
    JPanel resultsContentArea;
    static String[] availableRoomColumnNames = {
            "Rooms Available",
            "Room Type",
            "Bed Count",
            "Bed Type",
            "Smoking Available"
    };

    public HomePage() {
        currentDate = new Date(); //Initializes date and sets it to current time

        localFromDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        localToDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(1);
        //Todo: Eventually change dates to a JCalendar or JDateChooser? Involved important files...

        //Calendar c = Calendar.getInstance();
        //c.add(Calendar.DATE, 1);
        //toDate = c.getTime();
    }

    //left side
    public JPanel generateRoomSearchContentArea(String passedInReservationCode) {

        JLabel dateTitle;

        JPanel contentArea = new JPanel();
        contentArea.setLayout(null);
        contentArea.setBounds(0, 0, 600, 1400);
        contentArea.setBackground(CustomColor.LOGIN_CONTAINER_THEME);


        if (passedInReservationCode.equals("")) {
            dateTitle = new JLabel("Create a Reservation: ");
            dateTitle.setFont(new Font("serif", Font.PLAIN, 30));
            dateTitle.setForeground(CustomColor.PORTAL_TOP_BAR);
            dateTitle.setBounds(25, 10, 400, 75);
        }
        else {
            dateTitle = new JLabel("Modify Reservation " + passedInReservationCode + ": ");
            dateTitle.setFont(new Font("serif", Font.PLAIN, 30));
            dateTitle.setForeground(CustomColor.PORTAL_TOP_BAR);
            dateTitle.setBounds(25, 10, 400, 75);
        }

        JLabel checkInTitle = new JLabel("Check in Date: ");
        checkInTitle.setFont(new Font("serif", Font.PLAIN, 18));
        checkInTitle.setForeground(CustomColor.PORTAL_TOP_BAR);
        checkInTitle.setBounds(30, 80, 200, 75);

        JLabel checkOutTitle = new JLabel("Check out Date: ");
        checkOutTitle.setFont(new Font("serif", Font.PLAIN, 18));
        checkOutTitle.setForeground(CustomColor.PORTAL_TOP_BAR);
        checkOutTitle.setBounds(310, 80, 200, 75);

        contentArea.add(dateTitle);
        contentArea.add(checkInTitle);
        contentArea.add(checkOutTitle);

        return contentArea;
    }

    //right side
    public static JScrollPane generateRoomSelectionContentArea(LocalDate fromDate, LocalDate toDate) {
        JScrollPane pane;

        data = ReservationManager.getAvailableRoomCombos(fromDate, toDate);

        availableRoomTable = new JTable(data, availableRoomColumnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        availableRoomTable.setBounds(600, 0, 600, 900); //900
        availableRoomTable.setFont(new Font("serif", Font.PLAIN, 18));
        availableRoomTable.setRowHeight(25);

        pane = new JScrollPane(availableRoomTable);
        pane.setBounds(600, 100, 500, 398); //640

        availableRoomTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {

                int selectedRow;

                selectedRow = availableRoomTable.getSelectedRow();
                selectedRoomValues = new Object[1][4];
                selectedRoomValues[0][0] = availableRoomTable.getValueAt(selectedRow, 1);
                selectedRoomValues[0][1] = availableRoomTable.getValueAt(selectedRow, 2);
                selectedRoomValues[0][2] = availableRoomTable.getValueAt(selectedRow, 3);
                selectedRoomValues[0][3] = availableRoomTable.getValueAt(selectedRow, 4);
            }
        });

        return pane;
    }

    public static JLabel addTitle()
    {
            JLabel tableTitle = new JLabel("Currently Available Rooms: ");
            tableTitle.setFont(new Font("serif", Font.PLAIN, 18));
            tableTitle.setForeground(CustomColor.PORTAL_TOP_BAR);
            tableTitle.setBounds(600, 50, 300, 75);

        return tableTitle;
    }

    public static JButton addCreateReservationButton()
    {
        JButton makeReservationBtn = new JButton("Reserve");
        makeReservationBtn.setFocusable(false);
        makeReservationBtn.setFont(new Font("serif", Font.PLAIN, 20));
        makeReservationBtn.setBounds(600, 600, 250, 50);
        makeReservationBtn.setBackground(CustomColor.PURPLE_THEME_TXT);
        makeReservationBtn.setForeground(CustomColor.LOGIN_CONTAINER_THEME);

        return makeReservationBtn;
    }

    public static JButton addSearchAvailableRoomsButton() {

        JButton searchAvailableRoomsBtn = new JButton("Search");
        searchAvailableRoomsBtn.setFocusable(false);
        searchAvailableRoomsBtn.setBounds(150, 300, 250, 50);
        searchAvailableRoomsBtn.setForeground(CustomColor.LOGIN_CONTAINER_THEME);
        searchAvailableRoomsBtn.setFont(new Font("serif", Font.PLAIN, 20));
        searchAvailableRoomsBtn.setBackground(CustomColor.MAIN_PURPLE_THEME);

        return searchAvailableRoomsBtn;
    }

    public static JTextField addFromDateText()

    {
        JTextField fromDateTxt = new JTextField(localFromDate.toString());

        fromDateTxt.setLayout(null);
        fromDateTxt.setBounds(25, 140, 200, 50);
        fromDateTxt.setBackground(CustomColor.INPUT_BACKGROUND);
        fromDateTxt.setFont(new Font("serif", Font.PLAIN, 20));
        fromDateTxt.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));

        return fromDateTxt;
    }

    public static JTextField addToDateText() {

        JTextField toDateTxt = new JTextField(localToDate.toString());

        toDateTxt.setLayout(null);
        toDateTxt.setBounds(305, 140, 200, 50);
        toDateTxt.setBackground(CustomColor.INPUT_BACKGROUND);
        toDateTxt.setFont(new Font("serif", Font.PLAIN, 20));
        toDateTxt.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));

        return toDateTxt;
    }

    public static Object[][] selectedRoom()
    {
        return selectedRoomValues;
    }
}