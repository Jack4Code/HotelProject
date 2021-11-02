package main.java.UI.HomeTab;

import main.java.DataModels.AvailableRoom;
import main.java.DataModels.Room;
import main.java.Managers.ReservationManager;
import main.java.UI.Resources.CustomColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomePage {


    ArrayList<AvailableRoom> availableRooms;

    Date fromDate, toDate;

    JPanel resultsContentArea;
    JButton searchAvailableRoomsBtn;

    public HomePage() {
        fromDate = new Date();
        Calendar c = Calendar.getInstance();

        c.add(Calendar.DATE, 1);

        toDate = c.getTime();
    }


    public ArrayList<AvailableRoom> getAvailableRooms() {
        ArrayList<AvailableRoom> availableRooms = new ArrayList<>();
        ArrayList<Room> rooms = ReservationManager.getAllAvailableRoomsByDateRange(fromDate, toDate);
        for (int i = 0; i < rooms.size(); i++) {
            AvailableRoom availableRoom = new AvailableRoom();
            availableRoom.roomId = rooms.get(i).id;
            availableRoom.bedType = rooms.get(i).bedType;
            availableRoom.roomType = rooms.get(i).roomType;
            availableRoom.numBeds = rooms.get(i).numBeds;
            availableRoom.checkInDate = fromDate;
            availableRoom.checkOutDate = toDate;
            availableRooms.add(availableRoom);
        }
        return availableRooms;
    }

    public void displayResultsInTable(){
        //resultsContentArea.Add()
    }


    //left side
    public JPanel generateRoomSearchContentArea() {

        JPanel contentArea = new JPanel();
        contentArea.setLayout(null);
        contentArea.setBounds(0, 0, 600, 1400);
        contentArea.setBackground(CustomColor.LOGIN_CONTAINER_THEME);

        JPanel fromDateArea = new JPanel();
        fromDateArea.setLayout(null);
        fromDateArea.setBounds(50, 50, 250, 75);
        fromDateArea.setBackground(CustomColor.MAIN_PURPLE_THEME);

        JTextField fromDateTxt = new JTextField(fromDate.toString());
        fromDateTxt.setLayout(null);
        fromDateTxt.setBounds(0, 12, 250, 50);
        fromDateTxt.setBackground(CustomColor.MAIN_PURPLE_THEME);
        fromDateTxt.setForeground(CustomColor.LOGIN_CONTAINER_THEME);
        fromDateTxt.setFont(new Font("serif", Font.PLAIN, 20));
        fromDateTxt.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        fromDateArea.add(fromDateTxt);

        JPanel toDateArea = new JPanel();
        toDateArea.setLayout(null);
        toDateArea.setBounds(350, 50, 250, 75);
        toDateArea.setBackground(Color.GREEN);


        //search button
        searchAvailableRoomsBtn = new JButton("Search");
        searchAvailableRoomsBtn.setFocusable(false);
        searchAvailableRoomsBtn.setBounds(50, 600, 200, 40);
        searchAvailableRoomsBtn.setForeground(CustomColor.LOGIN_CONTAINER_THEME);
        searchAvailableRoomsBtn.setFont(new Font("serif", Font.PLAIN, 20));
        searchAvailableRoomsBtn.setBackground(CustomColor.MAIN_PURPLE_THEME);
        searchAvailableRoomsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Pressing search them rooms button!");
                //call some method in here => which calls a method in a manger => which calls a method in SqlConnection
                getAvailableRooms();
                displayResultsInTable();
            }
        });

        contentArea.add(fromDateArea);
        contentArea.add(toDateArea);
        contentArea.add(searchAvailableRoomsBtn);

        return contentArea;
    }


    //right side
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
    }


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
