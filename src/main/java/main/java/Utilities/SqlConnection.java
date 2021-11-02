package main.java.Utilities;
import main.java.DataModels.*;
import main.java.Managers.UserManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

//String url ="jdbc:mysql://mysqlclassproject.mysql.database.azure.com:3306/{your_database}?useSSL=true&requireSSL=false"; myDbConn = DriverManager.getConnection(url, "zzsa@mysqlclassproject", {your_password});

public class SqlConnection {
    
    //String url ="jdbc:mysql://mysqlclassproject.mysql.database.azure.com:3306/{your_database}?useSSL=true&requireSSL=false"; myDbConn = DriverManager.getConnection(url, "zzsa@mysqlclassproject", {your_password});
    //public static String connectionString = "jdbc:mysql://mysqlclassproject.mysql.database.azure.com:3306/hotelmanagement?useSSL=true&requireSSL=false";
    public static String connectionString = "jdbc:mysql://mysqlclassproject.mysql.database.azure.com:3306/hotelmanagement?useSSL=true&requireSSL=false";
    public static String connectionUserName = "zzsa@mysqlclassproject";
    public static String connectionPassword = "Y7*9dkUkl1";

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

    public static User getUserId(User user){
        //retrieve the userId and assign it to the user object
        //user.id =
        return user;
    }

    public static boolean validateUserCredentials(String username, String password) {
        boolean isValidUser = false;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);
            PreparedStatement prepStmt = con.prepareStatement("Select Email, HashedPassword From Users" + " WHERE Email = ? AND HashedPassword = ?" );
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

    public static UserType getUserType(int userId){ //TODO: Pass User object instead and lookup userId
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
            switch(userTypeId){
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

    public static User getUserByUsername(String userName)
    {
        User user = new User();

        try{
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
        }
        catch(Exception ex){
            //logging
            return null;
        }
        return user;
    }

    public static User validateAndGetUser(String userName, String password){
        if(!validateUserCredentials(userName, password))
        {
            return null;
        }
        return getUserByUsername(userName);
    }

    public static User modifyUser(User user, String newFirstName, String newLastName, String newUserName, String newPassword)
    {

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);

            PreparedStatement prepStmt = con.prepareStatement("UPDATE users SET FirstName = ?, LastName = ?, Email = ?, HashedPassword = ?" + " WHERE Id = ?" );
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
        }
        catch(Exception ex){
            //logging
            return null;
        }
        return user;
    }

    public static User modifyUserType(User user, int newUserType)
    {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);

            PreparedStatement prepStmt = con.prepareStatement("UPDATE users SET UserTypeId = ?" + " WHERE Id = ?" );
            prepStmt.setInt(1, newUserType);
            prepStmt.setInt(2, user.id);
            prepStmt.executeUpdate();

            PreparedStatement updatedPrepStmt = con.prepareStatement("SELECT * FROM users WHERE Id = ?");
            updatedPrepStmt.setInt(1, user.id);
            ResultSet rs = updatedPrepStmt.executeQuery();

            while (rs.next()) {
                user.userTypeId = rs.getInt("UserTypeId");
            }
        }
        catch(Exception ex){
            //logging
            return null;
        }
        return user;
    }





    public static ArrayList<Room> getAllRooms(){
        ArrayList<Room> rooms = new ArrayList<>();

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM room");

            while(rs.next()){
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
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        return rooms;
    }

    public static ArrayList<Reservation> getAllReservations(User activeUser, String reservationCode, String email){
        ArrayList<Reservation> reservations = new ArrayList<>();
        ResultSet rs;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);
            Statement stmt = con.createStatement();


            if (activeUser.userTypeId == 1){
                rs = stmt.executeQuery("SELECT * FROM reservation WHERE Email = '" + activeUser.email + "'");
            } else if (reservationCode.equals("") && email.equals(""))
            {
                rs = stmt.executeQuery("SELECT * FROM reservation");
            } else if (!reservationCode.equals(""))
            {
                rs = stmt.executeQuery("SELECT * FROM reservation WHERE ");
            } else if(!email.equals(""))
            {
                rs = stmt.executeQuery("SELECT * FROM reservation");
            }
            else {
                rs = stmt.executeQuery("SELECT * FROM reservation");
            }

            while(rs.next()){
                Reservation reservation = new Reservation();
                reservation.ID = rs.getInt("Id");
                reservation.reservationCode = rs.getString("ReservationCode");
                reservation.userEmail = rs.getString("Email");
                reservation.firstName = rs.getString("FirstName");
                reservation.lastName = rs.getString("LastName");
                reservation.checkInDate = rs.getDate("CheckInDate");
                reservation.checkOutDate = rs.getDate("CheckOutDate");
                reservation.roomType = rs.getString("RoomType");
                reservation.numberOfBeds = rs.getInt("NumberOfBeds");
                reservation.bedType = rs.getString("BedType");
                reservation.isSmoking = rs.getInt("isSmoking");

                reservations.add(reservation);
            }
            con.close();
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        return reservations;
    }


    public static ArrayList<AvailableRoom> getAllAvailableRoomsByDateRange(String fromDate, String toDate){
        ArrayList<AvailableRoom> rooms = new ArrayList<>();
       // System.out.println("test- got to sql connector");


       /* String query = "SELECT r.Id as RoomId, r.RoomType as RoomType, r.NumBeds as NumBeds, r.BedType as BedType, r.isSmoking as isSmoking " +
                "FROM Room r " +
                "LEFT JOIN ReservationRoom rr on r.Id = rr.RoomId " +
                "LEFT JOIN Reservation res on res.Id = rr.ReservationId " +
                "AND res.CheckInDate >= '2021-12-01 00:00:00' AND res.CheckOutDate <= '2021-12-05 00:00:00' " +
                "WHERE res.CheckInDate IS NULL AND res.CheckOutDate IS NULL;";
        */


        String query = "SELECT r.Id as RoomId, r.RoomType as RoomType, r.NumBeds as NumBeds, r.BedType as BedType, r.isSmoking as isSmoking FROM Room r LEFT JOIN ReservationRoom rr on r.Id = rr.RoomId LEFT JOIN Reservation res on res.Id = rr.ReservationId AND res.CheckInDate >= ? AND res.CheckOutDate <= ? WHERE res.CheckInDate IS NULL AND res.CheckOutDate IS NULL;";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);

            //Statement stmt = con.createStatement();
            //ResultSet rs = stmt.executeQuery(query);


            PreparedStatement prepStmt = con.prepareStatement(query);

            prepStmt.setString(1, fromDate);
            prepStmt.setString(2, toDate);

            ResultSet rs = prepStmt.executeQuery();


            while(rs.next()){

                AvailableRoom room = new AvailableRoom();
                room.roomId = rs.getInt("RoomId");
                //room.isAvailable = rs.getInt("isAvailable");
                //room.nextAvailableDate = rs.getDate("NextAvailableDate");
                room.roomType = rs.getString("RoomType");
                room.numBeds = rs.getInt("NumBeds");
                room.bedType = rs.getString("BedType");
                room.isSmoking = rs.getInt("isSmoking");
                rooms.add(room);




            }
            con.close();

        }



        catch(Exception ex){
            System.out.println(ex.getMessage());
        }


        //System.out.println(Arrays.toString(rooms.toArray()));

        /*
        for (int i = 0; i < rooms.size(); i++){

            System.out.println("Room ID: " + rooms.get(i).id);
            System.out.println("Room Type:" + rooms.get(i).roomType);
        }
        */


        return rooms;
    }

    public static Room getRoomById(int roomId){
        //Todo: Is this method being used?
        Room room = new Room();
        room.id = roomId;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);
            PreparedStatement prepStmt = con.prepareStatement("Select * From room Where Id = ?");
            prepStmt.setInt(1, roomId);
            ResultSet rs = prepStmt.executeQuery();

            while(rs.next()){
                room.isAvailable = rs.getInt("isAvailable");
                room.nextAvailableDate = rs.getDate("NextAvailableDate");
                room.roomType = rs.getString("RoomType");
                room.numBeds = rs.getInt("NumBeds");
                room.bedType = rs.getString("BedType");
                room.isSmoking = rs.getInt("isSmoking");
            }
            con.close();
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return room;
    }

    public static boolean updateRoom(Room room){
        boolean isUpdateSuccessful = true;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);

            java.text.SimpleDateFormat sdf =
                    new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nextAvailableDate = sdf.format(room.nextAvailableDate);

            PreparedStatement prepStmt = con.prepareStatement("UPDATE room SET isAvailable = ?, NextAvailableDate = ?, RoomType = ?, NumBeds = ?, isSmoking = ?, BedType = ?" + " WHERE Id = ?" );
            prepStmt.setInt(1, room.isAvailable);
            prepStmt.setString(2, nextAvailableDate);
            prepStmt.setString(3, room.roomType);
            prepStmt.setInt(4, room.numBeds);
            prepStmt.setInt(5, room.isSmoking);
            prepStmt.setString(6, room.bedType);
            prepStmt.setInt(7, room.id);
            prepStmt.executeUpdate();

        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            isUpdateSuccessful = false;
        }
        return isUpdateSuccessful;
    }

    //Check for repeated email usage
    public static boolean isRepeatUser(String newEmail){

        int is_repeated = -1;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);
            PreparedStatement pstmt = con.prepareStatement("SELECT COUNT(1) FROM users WHERE Email = ?");
            pstmt.setString(1, newEmail);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                is_repeated = rs.getInt("COUNT(1)");
            }

            if (is_repeated == 1)
            {
                return true;
            }
            con.close();
        }
        catch(Exception ex){
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




}
