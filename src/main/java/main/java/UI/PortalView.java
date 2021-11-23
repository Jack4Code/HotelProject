package main.java.UI;

import jdk.jshell.spi.ExecutionControlProvider;
import main.java.DataModels.Room;
import main.java.DataModels.AvailableRoom;
import main.java.DataModels.User;
import main.java.Managers.ReservationManager;
import main.java.Managers.RoomManager;
import main.java.Managers.UserManager;
import main.java.UI.HomeTab.HomePage;
import main.java.UI.ReservationTab.ReservationPage;
import main.java.UI.Resources.CustomColor;
import main.java.Utilities.SqlConnection;
import main.java.Utilities.TableCellListener;

import javax.swing.*;
import javax.swing.text.TableView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PortalView extends JFrame implements ActionListener {

    UserManager userManager;

    //Side nav stuff
    JPanel sideNavContainer = null;
    JPanel homeOption, userOption, reservationsOption, settingsOption, roomOption;
    JButton homeOptionButton, userOptionButton, reservationsOptionButton, settingsOptionButton, roomOptionButton;

    //Top bar stuff
    JPanel topBarContainer;
    JButton logoutButton;

    //page content stuff
    JPanel homeContent, userContent, reservationsContent, settingsContent, roomContent = null;
    JTextField firstName, lastName, password, email;
    JButton settingsSubmissionBtn, userCreateBtn;
    JTable roomsTable;
    ArrayList<Room> rooms;
    JLabel invalidLoginAttemptTxt = new JLabel();

    //state
    String currentTab;

    //Reservation Tab
    JButton cancelReservationButton;
    JButton modifyReservationButton;
    JButton searchReservationButton;

    //Home Tab
    JButton createReservationBtn;
    JButton searchAvailableRoomsBtn;
    JScrollPane roomSelectionContentArea;
    JPanel roomSearchArea;
    JTextField fromDateText, toDateText;
    Date currentDate;
    static LocalDate localFromDate, localToDate;
    LocalDate fromDate, toDate;


    HomePage homepage = null;

    public PortalView(UserManager loggedInUser) {
        this.userManager = loggedInUser;

        this.currentTab = "Home";
        //this.toggleHomeView();

        sideNavContainer = initSideNav();
        topBarContainer = initTopBar();

        this.add(sideNavContainer);
        this.add(topBarContainer);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Hotel J3");
        ImageIcon mainIcon = new ImageIcon("hotel2.png");
        this.setIconImage(mainIcon.getImage());
        this.getContentPane().setBackground(CustomColor.MAIN_PURPLE_THEME);
        this.setLayout(null);
        this.setSize(1650, 990);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        System.out.println("Logged in as user: " + userManager.activeUser.email);

        currentDate = new Date(); //Initializes date and sets it to current time

        localFromDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        localToDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(1);
    }

    public JPanel initSideNav() {
        JPanel panel = new JPanel();
        panel.setBackground(CustomColor.PORTAL_SIDE_NAV);
        panel.setBounds(0, 0, 250, 1900);
        panel.setLayout(null);

        homeOption = this.generateSideNavOption("Home", 160);
        reservationsOption = this.generateSideNavOption("Reservations", 220);

        panel.add(homeOption);

        if (userManager.activeUser.userTypeId == 1) {
            settingsOption = this.generateSideNavOption("Settings", 280);
        } else if (userManager.activeUser.userTypeId == 2) {
            roomOption = this.generateSideNavOption("Rooms", 280);
            settingsOption = this.generateSideNavOption("Settings", 340);
            panel.add(roomOption);
        } else if (userManager.activeUser.userTypeId == 3) {
            userOption = this.generateSideNavOption("Users", 280);
            settingsOption = this.generateSideNavOption("Settings", 340);
            panel.add(userOption);
        }

        panel.add(reservationsOption);
        panel.add(settingsOption);

        return panel;
    }

    public JPanel generateSideNavOption(String label, int yPosition) {
        JPanel panel = new JPanel();

        panel.setLayout(null);
        panel.setBounds(0, yPosition, 250, 60);
        panel.setBackground(label.equals(this.currentTab) ? CustomColor.HIGHLIGHTED_OPTION : CustomColor.TRANSPARENT);

        JButton btn = new JButton(label);
        btn.setFocusable(false);
        btn.setBounds(0, 0, 250, 60);
        btn.setForeground(CustomColor.LOGIN_CONTAINER_THEME);
        btn.setBackground(CustomColor.TRANSPARENT);
        btn.setBorder(null);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        btn.setRolloverEnabled(false);
        btn.setContentAreaFilled(false);
        btn.setFont(new Font("serif", Font.PLAIN, 20));

        if (label.equals(this.currentTab)) {
            JPanel selectedBar = new JPanel();
            selectedBar.setLayout(null);
            selectedBar.setBackground(CustomColor.SELECTED_BAR);
            selectedBar.setBounds(0, 0, 5, 60);
            panel.add(selectedBar);
        }

        switch (label) {
            case "Home":
                homeOptionButton = btn;
                homeOptionButton.addActionListener(this);
                panel.add(homeOptionButton);
                break;
            case "Users":
                userOptionButton = btn;
                userOptionButton.addActionListener(this);
                panel.add(userOptionButton);
                break;
            case "Reservations":
                reservationsOptionButton = btn;
                reservationsOptionButton.addActionListener(this);
                panel.add(reservationsOptionButton);
                break;
            case "Settings":
                settingsOptionButton = btn;
                settingsOptionButton.addActionListener(this);
                panel.add(settingsOptionButton);
                break;
            case "Rooms":
                roomOptionButton = btn;
                roomOptionButton.addActionListener(this);
                panel.add(roomOptionButton);
                break;
            default:
                break;
        }

        return panel;
    }

    public JPanel initTopBar() {
        JPanel panel = new JPanel();
        panel.setBackground(CustomColor.PORTAL_TOP_BAR);
        panel.setBounds(250, 0, 2000, 80);
        panel.setLayout(null);

        //TODO: Figure out how to make this right align and reposition with resizing
        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("serif", Font.PLAIN, 30));
        logoutButton.setFocusable(false);
        logoutButton.setBackground(CustomColor.TRANSPARENT);
        logoutButton.setBorder(null);
        logoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutButton.setFocusPainted(false);
        logoutButton.setRolloverEnabled(false);
        logoutButton.setContentAreaFilled(false);
        logoutButton.setForeground(CustomColor.LOGIN_CONTAINER_THEME);
        logoutButton.setBounds(1200, 15, 150, 50);
        logoutButton.addActionListener(this);

        panel.add(logoutButton);

        return panel;
    }

    public void regenerateSideNav() {

        if (settingsContent != null) {
            this.remove(settingsContent);
        }
        if (userContent != null) {
            this.remove(userContent);
        }
        if (reservationsContent !=null) {
            this.remove((reservationsContent));
        }
        if (homeContent != null) {
            this.remove(homeContent);
        }
        if (roomContent != null) {
            this.remove(roomContent);
        }
        this.repaint();

        if (sideNavContainer != null) {
            this.remove(sideNavContainer);
        }

        sideNavContainer = initSideNav();
        this.add(sideNavContainer);
        this.repaint();
    }

    public JPanel generateBlankContentCanvas() {
        JPanel panel = new JPanel();

        panel.setLayout(null);
        panel.setBackground(CustomColor.LOGIN_CONTAINER_THEME);
        panel.setBounds(330, 160, 1200, 700);

        return panel;
    }

    public void toggleHomeView() {
        this.regenerateSideNav();
        this.homeContent = generateBlankContentCanvas();

        homepage = new HomePage();

        roomSelectionContentArea = homepage.generateRoomSelectionContentArea(localFromDate, localToDate);

        searchAvailableRoomsBtn = (homepage.addSearchAvailableRoomsButton());
        searchAvailableRoomsBtn.addActionListener(this);
        homeContent.add(searchAvailableRoomsBtn);

        roomSearchArea = homepage.generateRoomSearchContentArea();

        fromDateText = homepage.addFromDateText();
        toDateText = homepage.addToDateText();
        homeContent.add(fromDateText);
        homeContent.add(toDateText);

        homeContent.add(roomSearchArea); //Searching for rooms

        homeContent.add(roomSelectionContentArea); //Listing and reserving rooms
        homeContent.add(homepage.addTitle());

        createReservationBtn = (homepage.addCreateReservationButton());
        createReservationBtn.addActionListener(this);
        homeContent.add(createReservationBtn);

        this.add(homeContent);
        this.repaint();
    }

    public void toggleUserView() {
        this.regenerateSideNav();

        this.userContent = generateBlankContentCanvas();

        JLabel hotelClerkSignUpHeader = new JLabel("Create new hotel clerk below:");
        hotelClerkSignUpHeader.setForeground(CustomColor.PORTAL_TOP_BAR);
        hotelClerkSignUpHeader.setFont(new Font("serif", Font.PLAIN, 30));
        hotelClerkSignUpHeader.setBounds(40, 35, 700, 35);

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(40, 115, 320, 24);
        firstNameLabel.setFont(new Font("serif", Font.PLAIN, 20));

        firstName = new JTextField("");
        firstName.setBounds(40, 140, 320, 40);
        firstName.setBackground(CustomColor.INPUT_BACKGROUND);
        firstName.setFont(new Font("serif", Font.PLAIN, 20));
        firstName.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        firstName.setMargin(new Insets(0, 10, 0, 0));

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(40, 215, 320, 24);
        lastNameLabel.setFont(new Font("serif", Font.PLAIN, 20));

        lastName = new JTextField("");
        lastName.setBounds(40, 240, 320, 40);
        lastName.setBackground(CustomColor.INPUT_BACKGROUND);
        lastName.setFont(new Font("serif", Font.PLAIN, 20));
        lastName.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        lastName.setMargin(new Insets(0, 10, 0, 0));

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(40, 315, 320, 24);
        emailLabel.setFont(new Font("serif", Font.PLAIN, 20));

        email = new JTextField("");
        email.setBounds(40, 340, 320, 40);
        email.setBackground(CustomColor.INPUT_BACKGROUND);
        email.setFont(new Font("serif", Font.PLAIN, 20));
        email.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        email.setMargin(new Insets(0, 10, 0, 0));

        userCreateBtn = new JButton("Create");
        userCreateBtn.setFocusable(false);
        userCreateBtn.setBounds(40, 440, 320, 40);
        userCreateBtn.setForeground(CustomColor.LOGIN_CONTAINER_THEME);
        userCreateBtn.setFont(new Font("serif", Font.PLAIN, 20));
        userCreateBtn.setBackground(CustomColor.MAIN_PURPLE_THEME);
        userCreateBtn.addActionListener(this);

        userContent.add(hotelClerkSignUpHeader);
        userContent.add(firstNameLabel);
        userContent.add(firstName);
        userContent.add(lastNameLabel);
        userContent.add(lastName);
        userContent.add(emailLabel);
        userContent.add(email);
        userContent.add(userCreateBtn);
        this.add(userContent);
        this.repaint();
    }

    public void toggleReservationsView() {
        this.regenerateSideNav();
        this.reservationsContent = generateBlankContentCanvas();
        reservationsContent.add(ReservationPage.generateTable(this.userManager.activeUser));
        reservationsContent.add(ReservationPage.addTitle());

        modifyReservationButton = (ReservationPage.addModifyButton());
        modifyReservationButton.addActionListener(this);
        reservationsContent.add(modifyReservationButton);

        this.add(reservationsContent);
        this.repaint();
    }

    public void toggleSettingsView() {
        System.out.println("Settings view toggled!");
        this.regenerateSideNav();

        this.settingsContent = generateBlankContentCanvas();

        JLabel modifyAccountMsg = new JLabel("Modify your account below:");
        modifyAccountMsg.setForeground(CustomColor.PORTAL_TOP_BAR);
        modifyAccountMsg.setFont(new Font("serif", Font.PLAIN, 30));
        modifyAccountMsg.setBounds(40, 35, 700, 35);

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(40, 115, 320, 24);
        firstNameLabel.setFont(new Font("serif", Font.PLAIN, 20));

        firstName = new JTextField(userManager.activeUser.firstName);
        firstName.setBounds(40, 140, 320, 40);
        firstName.setBackground(CustomColor.INPUT_BACKGROUND);
        firstName.setFont(new Font("serif", Font.PLAIN, 20));
        firstName.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        firstName.setMargin(new Insets(0, 10, 0, 0));

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(40, 215, 320, 24);
        lastNameLabel.setFont(new Font("serif", Font.PLAIN, 20));

        lastName = new JTextField(userManager.activeUser.lastName);
        lastName.setBounds(40, 240, 320, 40);
        lastName.setBackground(CustomColor.INPUT_BACKGROUND);
        lastName.setFont(new Font("serif", Font.PLAIN, 20));
        lastName.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        lastName.setMargin(new Insets(0, 10, 0, 0));

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(40, 315, 320, 24);
        emailLabel.setFont(new Font("serif", Font.PLAIN, 20));

        email = new JTextField(userManager.activeUser.email);
        email.setBounds(40, 340, 320, 40);
        email.setBackground(CustomColor.INPUT_BACKGROUND);
        email.setFont(new Font("serif", Font.PLAIN, 20));
        email.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        email.setMargin(new Insets(0, 10, 0, 0));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(40, 415, 320, 24);
        passwordLabel.setFont(new Font("serif", Font.PLAIN, 20));

        invalidLoginAttemptTxt.setText("Invalid email!");
        invalidLoginAttemptTxt.setBounds(40, 73, 250, 30);
        invalidLoginAttemptTxt.setFont(new Font("serif", Font.PLAIN, 25));
        invalidLoginAttemptTxt.setForeground(CustomColor.WARNING_RED);

        password = new JPasswordField(userManager.activeUser.password);
        password.setBounds(40, 440, 320, 40);
        password.setBackground(CustomColor.INPUT_BACKGROUND);
        password.setFont(new Font("serif", Font.PLAIN, 20));
        password.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        password.setMargin(new Insets(0, 10, 0, 0));


        settingsSubmissionBtn = new JButton("Modify");
        settingsSubmissionBtn.setFocusable(false);
        settingsSubmissionBtn.setBounds(40, 540, 320, 40);
        settingsSubmissionBtn.setForeground(CustomColor.LOGIN_CONTAINER_THEME);
        settingsSubmissionBtn.setFont(new Font("serif", Font.PLAIN, 20));
        settingsSubmissionBtn.setBackground(CustomColor.MAIN_PURPLE_THEME);
        settingsSubmissionBtn.addActionListener(this);


        settingsContent.add(modifyAccountMsg);
        settingsContent.add(firstNameLabel);
        settingsContent.add(firstName);
        settingsContent.add(lastNameLabel);
        settingsContent.add(lastName);
        settingsContent.add(email);
        settingsContent.add(emailLabel);
        settingsContent.add(passwordLabel);
        settingsContent.add(password);
        settingsContent.add(settingsSubmissionBtn);
        settingsContent.add(invalidLoginAttemptTxt);
        invalidLoginAttemptTxt.setVisible(false);

        this.add(settingsContent);
        this.repaint();
    }

    public void toggleRoomsView() {

        this.regenerateSideNav();
        this.roomContent = generateBlankContentCanvas();

        rooms = RoomManager.getAllRooms();

        String[] columnNames = {
                "Id",
                "Available Now",
                "Next Available Date",
                "Room Type",
                "Bed Count",
                "Bed Type",
                "Smoking Available"
        };

        Object[][] data = new Object[40][7];

        for (int i = 0; i < 40; i++) {
            data[i][0] = rooms.get(i).id;
            data[i][1] = rooms.get(i).isAvailable == 1 ? "Yes" : "No";
            data[i][2] = rooms.get(i).nextAvailableDate;
            data[i][3] = rooms.get(i).roomType.substring(0, 1).toUpperCase() + rooms.get(i).roomType.substring(1).toLowerCase();
            data[i][4] = rooms.get(i).numBeds;
            data[i][5] = rooms.get(i).bedType.substring(0, 1).toUpperCase() + rooms.get(i).bedType.substring(1).toLowerCase();
            data[i][6] = rooms.get(i).isSmoking == 1 ? "Yes" : "No";
        }

        roomsTable = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };

        roomsTable.setBounds(0, 0, 900, 650);
        roomsTable.setFont(new Font("serif", Font.PLAIN, 20));
        roomsTable.setRowHeight(21);

        Action action = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                TableCellListener tcl = (TableCellListener) e.getSource();
                System.out.println("Row   : " + tcl.getRow());
                System.out.println("Column: " + tcl.getColumn());
                System.out.println("Old   : " + tcl.getOldValue());
                System.out.println("New   : " + tcl.getNewValue());

                System.out.println("Updating database!");

                Room roomToUpdate = new Room();
                roomToUpdate = rooms.get(tcl.getRow()); //TODO: Be careful here...what if the order changes? Fix potential bug before it arises

                switch (tcl.getColumn()) {
                    case 1:
                        roomToUpdate.isAvailable = tcl.getNewValue().toString().toLowerCase().equals("yes") ? 1 : 0;
                        break;
                    case 2:
                        try {
                            roomToUpdate.nextAvailableDate = new SimpleDateFormat("yyyy-MM-dd").parse(tcl.getNewValue().toString());
                        } catch (Exception ignore) {
                        }
                        break;
                    case 3:
                        roomToUpdate.roomType = tcl.getNewValue().toString();
                        break;
                    case 4:
                        roomToUpdate.numBeds = Integer.parseInt(tcl.getNewValue().toString());
                        break;
                    case 5:
                        roomToUpdate.bedType = tcl.getNewValue().toString();
                        break;
                    case 6:
                        roomToUpdate.isSmoking = tcl.getNewValue().toString().toLowerCase().equals("yes") ? 1 : 0;
                        break;
                }

                RoomManager.updateRoom(roomToUpdate);
            }
        };

        TableCellListener tcl = new TableCellListener(roomsTable, action);

        JScrollPane pane = new JScrollPane(roomsTable);
        pane.setBounds(150, 120, 900, 483);

        JLabel tableTitle = new JLabel("Modify room information below: ");
        tableTitle.setFont(new Font("serif", Font.PLAIN, 30));
        tableTitle.setForeground(CustomColor.PORTAL_TOP_BAR);
        tableTitle.setBounds(150, 60, 700, 35);

        roomContent.add(tableTitle);
        roomContent.add(pane);
        this.add(roomContent);
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logoutButton) {
            new LoginRegisterView();
            this.dispose();
        } else if (e.getSource() == homeOptionButton) {
            this.currentTab = "Home";
            this.toggleHomeView();
        } else if (e.getSource() == userOptionButton) {
            this.currentTab = "Users";
            this.toggleUserView();
        } else if (e.getSource() == reservationsOptionButton) {
            this.currentTab = "Reservations";
            this.toggleReservationsView();
        } else if (e.getSource() == settingsOptionButton) {
            this.currentTab = "Settings";
            this.toggleSettingsView();
        } else if (e.getSource() == roomOptionButton) {
            this.currentTab = "Rooms";
            this.toggleRoomsView();
        } else if (e.getSource() == createReservationBtn) {
            fromDate = LocalDate.parse(fromDateText.getText());
            toDate = LocalDate.parse(toDateText.getText());
            Object[][] selectedRoomValues;
            selectedRoomValues = HomePage.selectedRoom();
            ReservationManager.makeReservation(userManager.activeUser, fromDate, toDate, selectedRoomValues[0][0].toString(), (Integer) selectedRoomValues[0][1], selectedRoomValues[0][2].toString(), (Integer) selectedRoomValues[0][3]);
            System.out.println("Success?");
        } else if (e.getSource() == modifyReservationButton) {
            ReservationPage.selectedReservation();
            this.currentTab = "Home";
            this.toggleHomeView();
        } else if (e.getSource() == searchAvailableRoomsBtn) {
            this.homeContent.remove(roomSelectionContentArea);
            fromDate = LocalDate.parse(fromDateText.getText());
            toDate = LocalDate.parse(toDateText.getText());
            roomSelectionContentArea = HomePage.generateRoomSelectionContentArea(fromDate, toDate);
            homeContent.add(roomSelectionContentArea);
            this.repaint();
        } else if (e.getSource() == settingsSubmissionBtn) {
            try {
                boolean is_modified = userManager.modifyUser(userManager.activeUser, firstName.getText(), lastName.getText(), email.getText(), password.getText());

                if (is_modified)
                {
                    invalidLoginAttemptTxt.setVisible(false);
                    settingsContent.repaint();

                    JFrame modal = new JFrame(); //TODO: For 2nd release implement true modal functionality

                    JLabel successMessage = new JLabel("Account modified!");
                    successMessage.setFont(new Font("serif", Font.PLAIN, 20));

                    modal.add(successMessage);
                    modal.setTitle("Hotel J3");
                    ImageIcon mainIcon = new ImageIcon("hotel2.png");
                    modal.setIconImage(mainIcon.getImage());
                    modal.setSize(200, 200);
                    modal.setLocationRelativeTo(null);
                    modal.setVisible(true);
                }
                else
                {
                    invalidLoginAttemptTxt.setVisible(true);
                    settingsContent.repaint();
                }
            } catch (Exception ignore) {
            }

        } else if (e.getSource() == userCreateBtn) {

            try {

                if (userManager.createClerkUser(userManager.activeUser, firstName.getText(), lastName.getText(), email.getText(),
                        "password" + firstName.getText().substring(0, 1).toLowerCase() + lastName.getText().substring(0, 1).toLowerCase())) {


                    JFrame modal = new JFrame(); //TODO: For 2nd release implement true modal functionality

                    JLabel successMessage = new JLabel(firstName.getText() + " " + lastName.getText() + " created!");
                    successMessage.setFont(new Font("serif", Font.PLAIN, 20));

                    modal.add(successMessage);
                    modal.setTitle("Hotel J3");
                    ImageIcon mainIcon = new ImageIcon("hotel2.png");
                    modal.setIconImage(mainIcon.getImage());
                    modal.setSize(200, 200);
                    modal.setLocationRelativeTo(null);
                    modal.setVisible(true);

                    firstName.setText("");
                    lastName.setText("");
                    email.setText("");

                    System.out.println("Created hotel clerk account!");
                }

            } catch (Exception ignore) {

            }
        }
    }
}
