package main.java.Utilities;

import main.java.DataModels.*;
import main.java.Managers.UserManager;
import main.java.UI.HomeTab.HomePage;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;


//String url ="jdbc:mysql://mysqlclassproject.mysql.database.azure.com:3306/{your_database}?useSSL=true&requireSSL=false"; myDbConn = DriverManager.getConnection(url, "zzsa@mysqlclassproject", {your_password});

public class SqlConnection {

    //String url ="jdbc:mysql://mysqlclassproject.mysql.database.azure.com:3306/{your_database}?useSSL=true&requireSSL=false"; myDbConn = DriverManager.getConnection(url, "zzsa@mysqlclassproject", {your_password});
    //public static String connectionString = "jdbc:mysql://mysqlclassproject.mysql.database.azure.com:3306/hotelmanagement?useSSL=true&requireSSL=false";
    //public static String connectionString = "jdbc:mysql://mysqlclassproject.mysql.database.azure.com:3306/hotelmanagement?useSSL=true&requireSSL=false";
    //public static String connectionUserName = "zzsa@mysqlclassproject";
    //public static String connectionPassword = "Y7*9dkUkl1";
    public static String connectionString = "jdbc:mysql://localhost:3306/hotelmanagement?useSSL=true&requireSSL=false";
    public static String connectionUserName = System.getenv("MYSQL_USERNAME");
    public static String connectionPassword = System.getenv("MYSQL_PASSWORD");

    //Users
    public static boolean createUser(User user) {
        boolean isCreateSuccess = true;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);
            PreparedStatement prepStmt = con.prepareStatement("Insert Into Users (UserTypeId, FirstName, LastName, Email, HashedPassword, CreateDate, ModifiedDate, LastLoginDate) VALUES (" + "?, ?, ?, ?, ?, NOW(), NOW(), null)");
            prepStmt.setInt(1, user.userTypeId);
            prepStmt.setString(2, user.firstName);
            prepStmt.setString(3, user.lastName);
            prepStmt.setString(4, user.email);
            prepStmt.setString(5, user.password);
            prepStmt.execute();

            con.close();


        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            isCreateSuccess = false;
        }
        return isCreateSuccess;
    }

    public static User getUserId(User user) {
        //retrieve the userId and assign it to the user object
        //user.id =
        return user;
    }

    public static boolean validateUserCredentials(String username, String password) {
        boolean isValidUser = false;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);
            PreparedStatement prepStmt = con.prepareStatement("Select Email, HashedPassword From Users" + " WHERE Email = ? AND BINARY HashedPassword = ?");
            prepStmt.setString(1, username);
            prepStmt.setString(2, password);
            ResultSet rs = prepStmt.executeQuery();

            while (rs.next()) {
                isValidUser = true;
            }

            con.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return isValidUser;
    }

    public static UserType getUserType(int userId) { //TODO: Pass User object instead and lookup userId
        int userTypeId = 1;
        UserType userType = UserType.Guest;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);

            //Todo: Prepared Statement-----------------------------------------------------------------
            //Statement stmt = con.createStatement();
            //ResultSet rs = stmt.executeQuery("Select UserTypeId From Users Where UserId = " + userId);
            //Todo: Where is this being used? When they sing in to UI?
            PreparedStatement prepStmt = con.prepareStatement("Select UserTypeId From Users Where UserId =? ");
            prepStmt.setInt(1, userId);
            ResultSet rs = prepStmt.executeQuery();

            while (rs.next()) {
                userTypeId = rs.findColumn("UserTypeId");
            }
            switch (userTypeId) {
                case 1:
                    userType = UserType.Guest;
                    break;
                case 2:
                    userType = UserType.HotelClerk;
                    break;
                case 3:
                    userType = UserType.SysAdmin;
                    break;
                default:
                    userType = UserType.Guest;
                    break;
            }
            con.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return userType;
    }

    public static User getUserByUsername(String userName) {
        User user = new User();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);
            PreparedStatement prepStmt = con.prepareStatement("Select * From Users Where Email =? ");
            prepStmt.setString(1, userName);
            ResultSet rs = prepStmt.executeQuery();

            while (rs.next()) {
                user.id = rs.getInt("Id");
                user.userTypeId = rs.getInt("UserTypeId");
                user.firstName = rs.getString("FirstName");
                user.lastName = rs.getString("LastName");
                user.email = rs.getString("Email");
            }
        } catch (Exception ex) {
            //logging
            return null;
        }
        return user;
    }

    public static User validateAndGetUser(String userName, String password) {
        if (!validateUserCredentials(userName, password)) {
            return null;
        }
        return getUserByUsername(userName);
    }

    public static User modifyUser(User user, String newFirstName, String newLastName, String newUserName, String newPassword) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);

            PreparedStatement prepStmt = con.prepareStatement("UPDATE users SET FirstName = ?, LastName = ?, Email = ?, HashedPassword = ?" + " WHERE Id = ?");
            prepStmt.setString(1, newFirstName);
            prepStmt.setString(2, newLastName);
            prepStmt.setString(3, newUserName);
            prepStmt.setString(4, newPassword);
            prepStmt.setInt(5, user.id);
            prepStmt.executeUpdate();

            PreparedStatement updatedPrepStmt = con.prepareStatement("SELECT * FROM users WHERE Id = ?");
            updatedPrepStmt.setInt(1, user.id);
            ResultSet rs = updatedPrepStmt.executeQuery();

            while (rs.next()) {
                user.firstName = rs.getString("FirstName");
                user.lastName = rs.getString("LastName");
                user.email = rs.getString("Email");
            }
        } catch (Exception ex) {
            //logging
            return null;
        }
        return user;
    }

    public static User modifyUserType(User user, int newUserType) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);

            PreparedStatement prepStmt = con.prepareStatement("UPDATE users SET UserTypeId = ?" + " WHERE Id = ?");
            prepStmt.setInt(1, newUserType);
            prepStmt.setInt(2, user.id);
            prepStmt.executeUpdate();

            PreparedStatement updatedPrepStmt = con.prepareStatement("SELECT * FROM users WHERE Id = ?");
            updatedPrepStmt.setInt(1, user.id);
            ResultSet rs = updatedPrepStmt.executeQuery();

            while (rs.next()) {
                user.userTypeId = rs.getInt("UserTypeId");
            }
        } catch (Exception ex) {
            //logging
            return null;
        }
        return user;
    }

    public static ArrayList<Room> getAllRooms() {
        ArrayList<Room> rooms = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM room");

            while (rs.next()) {
                Room room = new Room();
                room.id = rs.getInt("Id");
                room.isAvailable = rs.getInt("isAvailable");
                room.nextAvailableDate = rs.getDate("NextAvailableDate");
                room.roomType = rs.getString("RoomType");
                room.numBeds = rs.getInt("NumBeds");
                room.bedType = rs.getString("BedType");
                room.isSmoking = rs.getInt("isSmoking");

                rooms.add(room);
            }
            con.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return rooms;
    }

    public static ArrayList<Reservation> getAllReservations(User activeUser, String reservationCode, String email) {
        ArrayList<Reservation> reservations = new ArrayList<>();
        ResultSet rs;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);
            Statement stmt = con.createStatement();

            Date currentDate;
            currentDate = new Date();
            LocalDate localNowDate;
            localNowDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (activeUser.userTypeId == 1) {
                rs = stmt.executeQuery("SELECT * FROM reservation WHERE Email = '" + activeUser.email + "' AND (CheckOutDate > '" + localNowDate + "' AND DateCheckedOut IS NULL) AND DateCancelled IS NULL");
            } else if (reservationCode.equals("") && email.equals("")) {
                rs = stmt.executeQuery("SELECT * FROM reservation  WHERE (CheckOutDate > '" + localNowDate + "' AND DateCheckedOut IS NULL) AND DateCancelled IS NULL");
            } else if (!reservationCode.equals("")) {
                rs = stmt.executeQuery("SELECT * FROM reservation WHERE (CheckOutDate > '" + localNowDate + "' AND DateCheckedOut IS NULL) AND DateCancelled IS NULL");
            } else if (!email.equals("")) {
                rs = stmt.executeQuery("SELECT * FROM reservation  WHERE (CheckOutDate > '" + localNowDate + "' AND DateCheckedOut IS NULL) AND DateCancelled IS NULL");
            } else {
                rs = stmt.executeQuery("SELECT * FROM reservation  WHERE (CheckOutDate > '" + localNowDate + "' AND DateCheckedOut IS NULL) AND DateCancelled IS NULL");
            }

            while (rs.next()) {
                Reservation reservation = new Reservation();
                reservation.ID = rs.getInt("Id");
                reservation.reservationCode = rs.getString("ReservationCode");
                reservation.userEmail = rs.getString("Email");
                reservation.firstName = rs.getString("FirstName");
                reservation.lastName = rs.getString("LastName");
                reservation.checkInDate = rs.getDate("CheckInDate").toLocalDate(); //.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                reservation.checkOutDate = rs.getDate("CheckOutDate").toLocalDate(); //.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                reservation.DateCheckedIn = rs.getDate("DateCheckedIn") != null ? rs.getDate("DateCheckedIn").toLocalDate() : null;
                reservation.roomType = rs.getString("RoomType");
                reservation.numberOfBeds = rs.getInt("NumberOfBeds");
                reservation.bedType = rs.getString("BedType");
                reservation.isSmoking = rs.getInt("isSmoking");

                reservations.add(reservation);
            }
            con.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return reservations;
    }

    public static ArrayList<Room> getAllAvailableRoomsByDateRange(String fromDate, String toDate) {
        ArrayList<Room> rooms = new ArrayList<>();

        System.out.println("test- got to sql connector");


       /* String query = "SELECT r.Id as RoomId, r.RoomType as RoomType, r.NumBeds as NumBeds, r.BedType as BedType, r.isSmoking as isSmoking " +
                "FROM Room r " +
                "LEFT JOIN ReservationRoom rr on r.Id = rr.RoomId " +
                "LEFT JOIN Reservation res on res.Id = rr.ReservationId " +
                "AND res.CheckInDate >= '2021-12-01 00:00:00' AND res.CheckOutDate <= '2021-12-05 00:00:00' " +
                "WHERE res.CheckInDate IS NULL AND res.CheckOutDate IS NULL;";
        */


        String query = "SELECT r.Id as RoomId, r.RoomType as RoomType, r.NumBeds as NumBeds, r.BedType as BedType, r.isSmoking as isSmoking FROM Room r LEFT JOIN ReservationRoom rr on r.Id = rr.RoomId LEFT JOIN Reservation res on res.Id = rr.ReservationId AND res.CheckInDate >= ? AND res.CheckOutDate <= ? WHERE res.CheckInDate IS NULL AND res.CheckOutDate IS NULL;";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);

            //Statement stmt = con.createStatement();
            //ResultSet rs = stmt.executeQuery(query);


            PreparedStatement prepStmt = con.prepareStatement(query);

            prepStmt.setString(1, fromDate);
            prepStmt.setString(2, toDate);

            ResultSet rs = prepStmt.executeQuery();


            while (rs.next()) {
                Room room = new Room();
                room.id = rs.getInt("RoomId");
                //room.isAvailable = rs.getInt("isAvailable");
                //room.nextAvailableDate = rs.getDate("NextAvailableDate");
                room.roomType = rs.getString("RoomType");
                room.numBeds = rs.getInt("NumBeds");
                room.bedType = rs.getString("BedType");
                room.isSmoking = rs.getInt("isSmoking");

                rooms.add(room);
            }
            con.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }


        //System.out.println(Arrays.toString(rooms.toArray()));


        for (int i = 0; i < rooms.size(); i++) {

            System.out.println("Room ID: " + rooms.get(i).id);
            System.out.println("Room Type:" + rooms.get(i).roomType);
        }


        return rooms;
    }

    public static Room getRoomById(int roomId) {
        //Todo: Is this method being used?
        Room room = new Room();
        room.id = roomId;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);
            PreparedStatement prepStmt = con.prepareStatement("Select * From room Where Id = ?");
            prepStmt.setInt(1, roomId);
            ResultSet rs = prepStmt.executeQuery();

            while (rs.next()) {
                room.isAvailable = rs.getInt("isAvailable");
                room.nextAvailableDate = rs.getDate("NextAvailableDate");
                room.roomType = rs.getString("RoomType");
                room.numBeds = rs.getInt("NumBeds");
                room.bedType = rs.getString("BedType");
                room.isSmoking = rs.getInt("isSmoking");
            }
            con.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return room;
    }

    public static boolean updateRoom(Room room) {
        boolean isUpdateSuccessful = true;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);

            java.text.SimpleDateFormat sdf =
                    new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nextAvailableDate = sdf.format(room.nextAvailableDate);

            PreparedStatement prepStmt = con.prepareStatement("UPDATE room SET isAvailable = ?, NextAvailableDate = ?, RoomType = ?, NumBeds = ?, isSmoking = ?, BedType = ?" + " WHERE Id = ?");
            prepStmt.setInt(1, room.isAvailable);
            prepStmt.setString(2, nextAvailableDate);
            prepStmt.setString(3, room.roomType);
            prepStmt.setInt(4, room.numBeds);
            prepStmt.setInt(5, room.isSmoking);
            prepStmt.setString(6, room.bedType);
            prepStmt.setInt(7, room.id);
            prepStmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            isUpdateSuccessful = false;
        }
        return isUpdateSuccessful;
    }

    //Check for repeated email usage
    public static boolean isRepeatUser(String newEmail) {

        int is_repeated = -1;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);
            PreparedStatement pstmt = con.prepareStatement("SELECT COUNT(1) FROM users WHERE Email = ?");
            pstmt.setString(1, newEmail);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                is_repeated = rs.getInt("COUNT(1)");
            }

            if (is_repeated == 1) {
                return true;
            }
            con.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return false;
    }

    public static ArrayList<Room> getRoomCombos() {

        ArrayList<Room> rooms = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*), RoomType, NumBeds, BedType, isSmoking FROM room GROUP BY BedType, RoomType, NumBeds, isSmoking ORDER BY RoomType, NumBeds, isSmoking;");

            while (rs.next()) {
                Room room = new Room();
                room.isAvailable = rs.getInt("COUNT(*)");
                room.roomType = rs.getString("RoomType");
                room.numBeds = rs.getInt("NumBeds");
                room.bedType = rs.getString("BedType");
                room.isSmoking = rs.getInt("isSmoking");

                rooms.add(room);
            }
            con.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return rooms;
    }

    public static ArrayList<Room> getCombosFromReservations(String checkInDate, String checkOutDate) {

        ArrayList<Room> rooms = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*), RoomType, NumberOfBeds, BedType, isSmoking FROM reservation WHERE ((CheckInDate >= '" + checkInDate + "' AND CheckInDate < '" +
                    checkOutDate + "') OR (CheckOutDate > '" + checkInDate + "' AND CheckOutDate <= '" + checkOutDate + "')) AND DateCancelled IS NULL AND DateCheckedOut IS NULL GROUP BY BedType, RoomType, NumberOfBeds, isSmoking ORDER BY RoomType, NumberOfBeds, isSmoking;");

            while (rs.next()) {
                Room room = new Room();
                room.isAvailable = rs.getInt("COUNT(*)");
                room.roomType = rs.getString("RoomType");
                room.numBeds = rs.getInt("NumberOfBeds");
                room.bedType = rs.getString("BedType");
                room.isSmoking = rs.getInt("isSmoking");

                rooms.add(room);
            }
            con.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return rooms;
    }

    public static String createReservation(String reservationCode, String firstName, String lastName, LocalDate checkInDate, LocalDate checkOutDate, String roomType, int numberOfBeds, String bedType, int isSmoking, String email) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);

            PreparedStatement prepStmt = con.prepareStatement("INSERT INTO reservation (ReservationCode, FirstName, LastName, CheckInDate, CheckOutDate, RoomType, NumberOfBeds, BedType, IsSmoking, Email) VALUES (" + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            prepStmt.setString(1, reservationCode);
            prepStmt.setString(2, firstName);
            prepStmt.setString(3, lastName);
            java.sql.Date sqlCheckInDate = new java.sql.Date(Date.from(checkInDate.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
            prepStmt.setDate(4, sqlCheckInDate);
            java.sql.Date sqlCheckOutDate = new java.sql.Date(Date.from(checkOutDate.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
            prepStmt.setDate(5, sqlCheckOutDate);
            prepStmt.setString(6, roomType);
            prepStmt.setInt(7, numberOfBeds);
            prepStmt.setString(8, bedType);
            prepStmt.setInt(9, isSmoking);
            prepStmt.setString(10, email);

            prepStmt.execute();

            con.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return reservationCode;
    }

    public static String modifyReservation(String reservationCode, LocalDate checkInDate, LocalDate checkOutDate, String roomType, int numberOfBeds, String bedType, int isSmoking) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);

            PreparedStatement prepStmt = con.prepareStatement("UPDATE reservation SET CheckInDate = ?, CheckOutDate = ?, RoomType = ?, NumberOfBeds = ?, BedType = ?, IsSmoking = ? WHERE ReservationCode = '" + reservationCode + "'");

            java.sql.Date sqlCheckInDate = new java.sql.Date(Date.from(checkInDate.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
            prepStmt.setDate(1, sqlCheckInDate);
            java.sql.Date sqlCheckOutDate = new java.sql.Date(Date.from(checkOutDate.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
            prepStmt.setDate(2, sqlCheckOutDate);
            prepStmt.setString(3, roomType);
            prepStmt.setInt(4, numberOfBeds);
            prepStmt.setString(5, bedType);
            prepStmt.setInt(6, isSmoking);

            prepStmt.execute();

            con.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return reservationCode;
    }


    public static ArrayList<Billing> getBilling(String email) {
        ArrayList<Billing> billingList = new ArrayList<>();
        ResultSet rs;
        String statement;
        PreparedStatement prepStmt;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);
            //Statement stmt = con.createStatement();

            if (email.equals("")) {
                statement = "SELECT b.BillingCode as BillingCode, r.ReservationCode as ReservationCode, r.FirstName as FirstName, r.LastName as LastName, r.Email as Email, r.CheckInDate, r.DateCheckedOut, b.Amount as Amount FROM Reservation r JOIN Billing b ON r.Id = b.ReservationId WHERE r.DateCheckedOut is not Null or r.DateCancelled is not Null";
                prepStmt = con.prepareStatement(statement);
                rs = prepStmt.executeQuery();
            } else {
                statement = "SELECT b.BillingCode as BillingCode, r.ReservationCode as ReservationCode, r.FirstName as FirstName, r.LastName as LastName, r.Email as Email, r.CheckInDate, r.DateCheckedOut, b.Amount as Amount FROM Reservation r JOIN Billing b ON r.Id = b.ReservationId WHERE r.Email = ? AND (r.DateCheckedOut is not Null or r.DateCancelled is not null)";
                prepStmt = con.prepareStatement(statement);
                prepStmt.setString(1, email);
                rs = prepStmt.executeQuery();
            }

            while (rs.next()) {
                Billing billing = new Billing();

                //billing.ID = rs.getInt("ID");
                billing.billingCode = rs.getString("BillingCode");
                billing.reservationCode = rs.getString("ReservationCode");
                billing.firstName = rs.getString("FirstName");
                billing.lastName = rs.getString("LastName");
                billing.userEmail = rs.getString("Email");
                billing.checkInDate = rs.getDate("CheckInDate").toLocalDate();
                billing.dateCheckedOut = rs.getDate("DateCheckedOut").toLocalDate();
                billing.totalCost = rs.getFloat("Amount");

                billingList.add(billing);

            }
            con.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return billingList;

    }

    public static boolean checkInReservation(String reservationCode) {
        boolean isSuccessfullCheckIn = true;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);
            PreparedStatement prepStmt = con.prepareStatement("UPDATE Reservation SET DateCheckedIn = now() WHERE ReservationCode = " + "?");
            prepStmt.setString(1, reservationCode);
            prepStmt.execute();
            con.close();
        } catch (Exception ex) {
            isSuccessfullCheckIn = false;
        }
        return isSuccessfullCheckIn;
    }

    public static boolean checkOutReservation(String reservationCode) {
        boolean isSuccessfullCheckOut = true;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);
            PreparedStatement prepStmt = con.prepareStatement("UPDATE Reservation SET DateCheckedOut = now() WHERE ReservationCode = " + "?");
            prepStmt.setString(1, reservationCode);
            prepStmt.execute();
            con.close();
        } catch (Exception ex) {
            isSuccessfullCheckOut = false;
        }
        return isSuccessfullCheckOut;
    }

    public static boolean cancelReservation(String reservationCode) {
        boolean isSuccessfullCancel = true;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);
            PreparedStatement prepStmt = con.prepareStatement("UPDATE Reservation SET DateCancelled = now() WHERE ReservationCode = " + "?");
            prepStmt.setString(1, reservationCode);
            prepStmt.execute();
            con.close();
        } catch (Exception ex) {
            isSuccessfullCancel = false;
        }
        return isSuccessfullCancel;
    }

    public static boolean createBill(String billingCode, int reservationId, double billingAmt) {
        boolean isSuccessfullBillCreate = true;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);
            PreparedStatement prepStmt = con.prepareStatement("INSERT INTO Billing (BillingCode, ReservationId, Amount) VALUES (" + "?, ?, ?" + ")");
            prepStmt.setString(1, billingCode);
            prepStmt.setInt(2, reservationId);
            prepStmt.setDouble(3, billingAmt);
            prepStmt.execute();
            con.close();
        } catch (Exception ex) {
            isSuccessfullBillCreate = false;
        }
        return isSuccessfullBillCreate;
    }

    public static Reservation getReservationByReservationCode(String reservationCode) {
        Reservation reservation = new Reservation();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Id, ReservationCode, FirstName, LastName, CheckInDate, CheckOutDate, RoomType, NumberOfBeds, BedType, IsSmoking, Email, DateCheckedIn, DateCheckedOut, DateCancelled FROM reservation WHERE ReservationCode = '" + reservationCode + "'");

            while (rs.next()) {
                reservation.ID = rs.getInt(("Id"));
                reservation.reservationCode = rs.getString(("ReservationCode"));
                reservation.firstName = rs.getString(("FirstName"));
                reservation.lastName = rs.getString(("LastName"));
                reservation.checkInDate = rs.getDate("CheckInDate").toLocalDate();
                reservation.checkOutDate = rs.getDate("CheckOutDate").toLocalDate();
                reservation.roomType = rs.getString("RoomType");
                reservation.numberOfBeds = rs.getInt(("NumberOfBeds"));
                reservation.bedType = rs.getString(("BedType"));
                reservation.isSmoking = rs.getInt(("IsSmoking"));
                reservation.userEmail = rs.getString(("Email"));
                reservation.DateCheckedIn = rs.getDate("DateCheckedIn") != null ? rs.getDate("DateCheckedIn").toLocalDate() : null;
                reservation.DateCheckedOut = rs.getDate("DateCheckedOut") != null ? rs.getDate("DateCheckedOut").toLocalDate() : null;
                reservation.DateCancelled = rs.getDate("DateCancelled") != null ? rs.getDate("DateCancelled").toLocalDate() : null;
            }
            con.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return reservation;
    }

    public static void setupDatabaseIfNotExists() throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);
        } catch (Exception ex) {
            try {
                setupDatabase();
                setupTables();
                setupData();
            } catch (Exception ex2) {
                System.out.println(ex2.getMessage());
                throw new Exception("Error setting up database");
            }
        }
    }

    public static void setupDatabase() throws Exception {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?useSSL=true&requireSSL=false", connectionUserName, connectionPassword);
            Statement stmt = con.createStatement();
            stmt.execute("CREATE DATABASE hotelmanagement");
        }
        catch(Exception ex){
            throw new Exception("Error setting up database");
        }
    }

    public static void setupTables() throws Exception {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);

            String billingTable = "CREATE TABLE `billing` (\n" +
                    "  `Id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  `BillingCode` varchar(32) DEFAULT NULL,\n" +
                    "  `ReservationId` int(11) DEFAULT NULL,\n" +
                    "  `Amount` decimal(10,0) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`Id`)\n" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;";

            String reservationTable = "CREATE TABLE `reservation` (`Id` bigint(20) NOT NULL AUTO_INCREMENT,`ReservationCode` varchar(64) DEFAULT NULL,\n" +
                    "  `FirstName` varchar(64) DEFAULT NULL,\n" +
                    "  `LastName` varchar(64) DEFAULT NULL,\n" +
                    "  `CheckInDate` datetime DEFAULT NULL,\n" +
                    "  `CheckOutDate` datetime DEFAULT NULL,\n" +
                    "  `RoomType` varchar(32) DEFAULT NULL,\n" +
                    "  `NumberOfBeds` int(11) DEFAULT NULL,\n" +
                    "  `BedType` varchar(32) DEFAULT NULL,\n" +
                    "  `IsSmoking` bit(1) DEFAULT NULL,\n" +
                    "  `Email` varchar(32) DEFAULT NULL,\n" +
                    "  `DateCheckedIn` datetime DEFAULT NULL,\n" +
                    "  `DateCheckedOut` datetime DEFAULT NULL,\n" +
                    "  `DateCancelled` datetime DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`Id`),\n" +
                    "  UNIQUE KEY `ReservationCode` (`ReservationCode`)\n" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=latin1;";

            String reservationRoomTable = "CREATE TABLE `reservationroom` (\n" +
                    "  `Id` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
                    "  `ReservationId` bigint(20) DEFAULT NULL,\n" +
                    "  `RoomId` int(11) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`Id`)\n" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;";

            String roomTable = "CREATE TABLE `room` (\n" +
                    "  `Id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  `isAvailable` bit(1) DEFAULT NULL,\n" +
                    "  `NextAvailableDate` datetime DEFAULT NULL,\n" +
                    "  `RoomType` varchar(32) DEFAULT NULL,\n" +
                    "  `NumBeds` int(11) DEFAULT NULL,\n" +
                    "  `BedType` varchar(32) DEFAULT NULL,\n" +
                    "  `isSmoking` bit(1) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`Id`)\n" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=latin1;";

            String usersTable = "CREATE TABLE `users` (\n" +
                    "  `Id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  `UserTypeId` int(11) DEFAULT NULL,\n" +
                    "  `FirstName` varchar(128) DEFAULT NULL,\n" +
                    "  `LastName` varchar(128) DEFAULT NULL,\n" +
                    "  `Email` varchar(128) DEFAULT NULL,\n" +
                    "  `HashedPassword` varchar(2048) DEFAULT NULL,\n" +
                    "  `CreateDate` datetime DEFAULT NULL,\n" +
                    "  `ModifiedDate` datetime DEFAULT NULL,\n" +
                    "  `LastLoginDate` datetime DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`Id`),\n" +
                    "  UNIQUE KEY `Email` (`Email`)\n" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=latin1;";

            String userTypesTable = "CREATE TABLE `usertypes` (\n" +
                    "  `Id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  `UserTypeName` varchar(128) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`Id`)\n" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;";

            ArrayList<String> tableCreateStmts = new ArrayList<>();
            tableCreateStmts.add(billingTable);
            tableCreateStmts.add(reservationTable);
            tableCreateStmts.add(reservationRoomTable);
            tableCreateStmts.add(roomTable);
            tableCreateStmts.add(usersTable);
            tableCreateStmts.add(userTypesTable);

            for(int i=0; i<tableCreateStmts.size(); i++){
                Statement stmt = con.createStatement();
                stmt.execute(tableCreateStmts.get(i));
            }

        } catch (Exception ex) {
            throw new Exception("Error setting up database");
        }
    }

    public static void setupData() throws Exception {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);

            String[] bedTypes = {"Twin", "King", "Queen", "Full" };

            for(int i=1; i<=40; i++){

                Random r = new Random();
                int randomBedPos = r.nextInt(4);
                int numBeds = randomBedPos == 0 || randomBedPos == 4 ? 2 : 1;

                String roomType = i < 11 ? "Comfort" : i < 21 ? "Executive" : i < 31 ? "Business" : "Economy";
                String createRoom = "INSERT INTO Room (isAvailable, nextAvailableDate, RoomType, NumBeds, BedType, isSmoking) VALUES (1, utc_date(), '" + roomType + "', " + numBeds + ", '" + bedTypes[randomBedPos] + "', 0)";

                Statement stmt = con.createStatement();
                stmt.execute(createRoom);
            }

            String sysAdminUser = "INSERT INTO USERS (UserTypeId, FirstName, LastName, Email, HashedPassword, CreateDate, ModifiedDate) VALUES (3, 'sys', 'admin', 'sysadmin@j3.net', 'password', utc_date(), utc_date())";
            Statement stmt = con.createStatement();
            stmt.execute(sysAdminUser);

        } catch (Exception ex) {
            throw new Exception("Error setting up database");
        }
    }
}
