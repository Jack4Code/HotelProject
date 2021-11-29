package main.java.UI.HomeTab;

import main.java.DataModels.AvailableRoom;
import main.java.DataModels.Room;
import main.java.Managers.ReservationManager;
import main.java.UI.PortalView;
import main.java.UI.Resources.CustomColor;

import javax.swing.*;
import javax.swing.border.Border;
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
        contentArea.setBounds(0, 0, 500, 75);
        contentArea.setBackground(CustomColor.LOGIN_CONTAINER_THEME);


        if (passedInReservationCode.equals("")) {
            dateTitle = new JLabel("Create a Reservation: ");
            dateTitle.setFont(new Font("serif", Font.PLAIN, 30));
            dateTitle.setForeground(CustomColor.PORTAL_TOP_BAR);
            dateTitle.setBounds(25, 0, 400, 75);
        }
        else {
            dateTitle = new JLabel("Modify Reservation " + passedInReservationCode + ": ");
            dateTitle.setFont(new Font("serif", Font.PLAIN, 30));
            dateTitle.setForeground(CustomColor.PORTAL_TOP_BAR);
            dateTitle.setBounds(25, 0, 400, 75);
        }

        contentArea.add(dateTitle);

        return contentArea;
    }

    public JPanel generateDateSearchField() {

        Border areaBorder = BorderFactory.createLineBorder(CustomColor.PORTAL_TOP_BAR);

        JPanel searchFieldContentArea = new JPanel();
        searchFieldContentArea.setLayout(null);
        searchFieldContentArea.setBounds(50, 100, 500, 397);
        searchFieldContentArea.setBackground(CustomColor.LOGIN_CONTAINER_THEME);
        searchFieldContentArea.setBorder(areaBorder);

        JLabel checkInTitle = new JLabel("Check in Date: ");
        checkInTitle.setFont(new Font("serif", Font.PLAIN, 18));
        checkInTitle.setForeground(CustomColor.PORTAL_TOP_BAR);
        checkInTitle.setBounds(35, 50, 200, 75);

        JLabel checkOutTitle = new JLabel("Check out Date: ");
        checkOutTitle.setFont(new Font("serif", Font.PLAIN, 18));
        checkOutTitle.setForeground(CustomColor.PORTAL_TOP_BAR);
        checkOutTitle.setBounds(270, 50, 200, 75);

        searchFieldContentArea.add(checkInTitle);
        searchFieldContentArea.add(checkOutTitle);

        return searchFieldContentArea;
    }

    public JPanel generateSearchFieldTitle() {

        JPanel searchFieldTitleArea = new JPanel();
        searchFieldTitleArea.setLayout(null);
        searchFieldTitleArea.setBounds(60, 77, 130, 40);
        searchFieldTitleArea.setBackground(CustomColor.LOGIN_CONTAINER_THEME);

        JLabel searchFieldTitle = new JLabel("Room Filters: ");
        searchFieldTitle.setFont(new Font("serif", Font.PLAIN, 20));
        searchFieldTitle.setForeground(CustomColor.PORTAL_TOP_BAR);
        searchFieldTitle.setBounds(10, 0, 130, 40);

        searchFieldTitleArea.add(searchFieldTitle);

        return searchFieldTitleArea;
    }

    public JPanel generateUserBorder() {

        Border userBorder = BorderFactory.createLineBorder(CustomColor.PORTAL_TOP_BAR);

        JPanel userBorderArea = new JPanel();
        userBorderArea .setLayout(null);
        userBorderArea .setBounds(50, 550, 500, 100);
        userBorderArea .setBackground(CustomColor.LOGIN_CONTAINER_THEME);
        userBorderArea .setBorder(userBorder);

        return userBorderArea ;
    }

    public JPanel generateUserBorderTitle() {

        JPanel userBorderTitleArea = new JPanel();
        userBorderTitleArea.setLayout(null);
        userBorderTitleArea.setBounds(60, 527, 170, 40);
        userBorderTitleArea.setBackground(CustomColor.LOGIN_CONTAINER_THEME);

        JLabel userBorderTitle = new JLabel("Reservation Email: ");
        userBorderTitle.setFont(new Font("serif", Font.PLAIN, 20));
        userBorderTitle.setForeground(CustomColor.PORTAL_TOP_BAR);
        userBorderTitle.setBounds(10, 0, 170, 40);

        userBorderTitleArea.add(userBorderTitle);

        return userBorderTitleArea;
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
        pane.setBounds(600, 100, 560, 398); //640

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

    public static JButton addCreateReservationButton(String passedInReservationCode)
    {
        JButton makeReservationBtn;

        if (passedInReservationCode.equals("")) {
            makeReservationBtn = new JButton("Reserve");
        }
        else {
            makeReservationBtn = new JButton("Modify");
        }

        makeReservationBtn.setFocusable(false);
        makeReservationBtn.setFont(new Font("serif", Font.PLAIN, 20));
        makeReservationBtn.setBounds(710, 575, 350, 50);
        makeReservationBtn.setBackground(CustomColor.PURPLE_THEME_TXT);
        makeReservationBtn.setForeground(CustomColor.LOGIN_CONTAINER_THEME);

        return makeReservationBtn;
    }

    public static JButton addSearchAvailableRoomsButton() {

        JButton searchAvailableRoomsBtn = new JButton("Search");
        searchAvailableRoomsBtn.setFocusable(false);
        searchAvailableRoomsBtn.setBounds(150, 320, 300, 50);
        searchAvailableRoomsBtn.setForeground(CustomColor.LOGIN_CONTAINER_THEME);
        searchAvailableRoomsBtn.setFont(new Font("serif", Font.PLAIN, 20));
        searchAvailableRoomsBtn.setBackground(CustomColor.PURPLE_THEME_TXT);

        return searchAvailableRoomsBtn;
    }

    public static JTextField addFromDateText()
    {
        JTextField fromDateTxt = new JTextField(localFromDate.toString());

        fromDateTxt.setLayout(null);
        fromDateTxt.setBounds(80, 200, 200, 50);
        fromDateTxt.setBackground(CustomColor.INPUT_BACKGROUND);
        fromDateTxt.setFont(new Font("serif", Font.PLAIN, 20));
        fromDateTxt.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));

        return fromDateTxt;
    }

    public static JTextField addToDateText() {
        JTextField toDateTxt = new JTextField(localToDate.toString());

        toDateTxt.setLayout(null);
        toDateTxt.setBounds(315, 200, 200, 50);
        toDateTxt.setBackground(CustomColor.INPUT_BACKGROUND);
        toDateTxt.setFont(new Font("serif", Font.PLAIN, 20));
        toDateTxt.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));

        return toDateTxt;
    }

    public static JTextField addEmailText(String activeUserEmail, int activeUserCode) {
        JTextField emailTxt = new JTextField(activeUserEmail);

        emailTxt.setLayout(null);
        emailTxt.setBounds(70, 575, 460, 50);
        emailTxt.setBackground(CustomColor.INPUT_BACKGROUND);
        emailTxt.setFont(new Font("serif", Font.PLAIN, 20));
        emailTxt.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        if (activeUserCode == 1) {
            emailTxt.setEditable(false);
        }

        return emailTxt;
    }

    public static Object[][] selectedRoom()
    {
        return selectedRoomValues;
    }
}