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


    /*
    public ArrayList<AvailableRoom> getAvailableRooms(String fromDate, String toDate) {
        try{
            //Parsing a string needs a try catch

            //System.out.println("test-Got to getAvailableRooms");
            ArrayList<AvailableRoom> availableRooms = new ArrayList<>();
            ArrayList<Room> rooms = ReservationManager.getAllAvailableRoomsByDateRange(fromDate, toDate);
            for (int i = 0; i < rooms.size(); i++) {
                AvailableRoom availableRoom = new AvailableRoom();
                availableRoom.roomId = rooms.get(i).id;
                availableRoom.bedType = rooms.get(i).bedType;
                availableRoom.roomType = rooms.get(i).roomType;
                availableRoom.numBeds = rooms.get(i).numBeds;
                availableRoom.checkInDate = timeFormat.parse(fromDate);
                availableRoom.checkOutDate = timeFormat.parse(toDate);
                availableRooms.add(availableRoom);

            }



        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }




        return availableRooms;
    }

    public void displayResultsInTable(){
        //resultsContentArea.Add()





    }
    */

    //left side
    public JPanel generateRoomSearchContentArea() {

        JPanel contentArea = new JPanel();
        contentArea.setLayout(null);
        contentArea.setBounds(0, 0, 600, 1400);
        contentArea.setBackground(CustomColor.LOGIN_CONTAINER_THEME);


        JPanel fromDateArea = new JPanel();
        //JLabel checkInLabel = new JLabel("Check In:");
        fromDateArea.setLayout(null);
        //fromDateArea.setBounds(50, 50, 250, 75);
        fromDateArea.setBounds(25, 25, 225, 50);
        fromDateArea.setBackground(CustomColor.MAIN_PURPLE_THEME);
        //checkInLabel.setBounds(50, 20, 250, 20);
       // checkInLabel.setBackground(CustomColor.LOGIN_CONTAINER_THEME);
        //checkInLabel.setForeground(Color.RED);

        JPanel toDateArea = new JPanel();
        toDateArea.setLayout(null);
        toDateArea.setBounds(300, 25, 250, 50);
        //toDateArea.setBounds(350, 50, 250, 75);

        toDateArea.setBackground(CustomColor.MAIN_PURPLE_THEME);

        //Todo: Automatically adding a check-out date causes issues. Here, just showing a week out from starting date

        contentArea.add(fromDateArea);
        contentArea.add(toDateArea);

        return contentArea;
    }

    //right side
    public static JScrollPane generateRoomSelectionContentArea(LocalDate fromDate, LocalDate toDate){
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
        pane.setBounds(600, 100, 500, 500); //640

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
        searchAvailableRoomsBtn.setBounds(300, 150, 250, 50);
        searchAvailableRoomsBtn.setForeground(CustomColor.LOGIN_CONTAINER_THEME);
        searchAvailableRoomsBtn.setFont(new Font("serif", Font.PLAIN, 20));
        searchAvailableRoomsBtn.setBackground(CustomColor.MAIN_PURPLE_THEME);

        return searchAvailableRoomsBtn;
    }

    public static JTextField addFromDateText()

    {
        JTextField fromDateTxt = new JTextField(localFromDate.toString());

        fromDateTxt.setLayout(null);
        fromDateTxt.setBounds(25, 35, 225, 25);
        fromDateTxt.setBackground(CustomColor.MAIN_PURPLE_THEME);
        fromDateTxt.setForeground(CustomColor.LOGIN_CONTAINER_THEME);
        fromDateTxt.setFont(new Font("serif", Font.PLAIN, 20));
        fromDateTxt.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));

        return fromDateTxt;
    }

    public static JTextField addToDateText() {

        JTextField toDateTxt = new JTextField(localToDate.toString());

        toDateTxt.setLayout(null);
        toDateTxt.setBounds(305, 35, 225, 25);
        toDateTxt.setBackground(CustomColor.MAIN_PURPLE_THEME);
        toDateTxt.setForeground(CustomColor.LOGIN_CONTAINER_THEME);
        toDateTxt.setFont(new Font("serif", Font.PLAIN, 20));
        toDateTxt.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));

        return toDateTxt;
    }

    public static Object[][] selectedRoom()
    {
        return selectedRoomValues;
    }
/*
    public JPanel generateRoomSelectionContentArea() {

        resultsContentArea = new JPanel();
        resultsContentArea.setLayout(null);
        resultsContentArea.setBounds(600, 0, 600, 1400);
        resultsContentArea.setBackground(CustomColor.LOGIN_CONTAINER_THEME);


        for (int i = 0; i < 4; i++) {
            JPanel panel = generateRoomSelectionBox(1, "Executive",
                    new Date(2021, 01, 01),
                    new Date(2020, 02, 01),
                    false,
                    (i * 100) + (i * 20));

            resultsContentArea.add(panel);
        }
        return resultsContentArea;

    }

    public JPanel generateRoomSelectionBox(int bedCnt, String roomType, Date fromDate, Date toDate, boolean isSmoking, int top) {
        JPanel box = new JPanel();
        box.setLayout(null);
        box.setBounds(20, top + 20, 560, 100);
        box.setBackground(CustomColor.PURPLE_THEME_TXT);


        return box;
    }*/




}




/*
For later...maybe

//        JPanel contentArea = new JPanel();
//
//        contentArea.setLayout(new BorderLayout());
//        contentArea.setBounds(600, 0, 600, 700);
//        contentArea.setBackground(Color.BLUE);

//        JPanel contentArea = new JPanel();
//        //contentArea.setPreferredSize(new Dimension( 2000,2000));
//        contentArea.setBounds(600, 0, 600, 1400);
//        //contentArea.setBackground(Color.BLUE);
//
//
////        JPanel someStupidThing = new JPanel();
////        someStupidThing.setBounds(0, 750, 100, 600);
////        someStupidThing.setBackground(Color.GREEN);
//
//        JTextArea textArea = new JTextArea(5,5);
//        textArea.setText("xx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\n");
//        JScrollPane scrollFrame = new JScrollPane(textArea);
//        //someStupidThing.setAutoscrolls(true);
//        scrollFrame.setPreferredSize(new Dimension( 600,700));
//        scrollFrame.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//
//        contentArea.add(scrollFrame, BorderLayout.CENTER);

        JLayeredPane contentArea = new JLayeredPane();
        contentArea.setBounds(600, 0, 600, 1400);


        JPanel scrollContent = new JPanel();
        scrollContent.setLayout(null);
        scrollContent.setBounds(0, 0, 600, 1400);
        //scrollContent.setBackground(Color.PINK);

        JScrollPane scrollFrame = new JScrollPane(scrollContent);
        //scrollFrame.setPreferredSize(new Dimension( 600,300));
        scrollFrame.setBounds(0, 0, 600, 700);
        scrollFrame.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollFrame.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JScrollBar scrollbar = new JScrollBar(JScrollBar.VERTICAL){
            @Override
            public boolean isVisible(){
                return true;
            }
        };

        //scrollbar.setBackground(CustomColor.LOGIN_CONTAINER_THEME);

        scrollFrame.setVerticalScrollBar(scrollbar);

        //contentArea.add(scrollContent);




 */
