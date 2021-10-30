package main.java.Utilities;
import main.java.DataModels.Room;
import main.java.DataModels.User;
import main.java.DataModels.UserType;

import java.sql.*;
import java.util.ArrayList;

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
            Statement stmt = con.createStatement();
            stmt.execute("Insert Into Users (UserTypeId, FirstName, LastName, Email, HashedPassword, CreateDate, ModifiedDate, LastLoginDate) VALUES (" + user.userTypeId + ", '" + user.firstName + "', '" + user.lastName + "', '" + user.email + "', '" + user.password + "', NOW(), NOW(), null)");
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
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("Select Email, HashedPassword From Users WHERE Email = '" + username + "' AND HashedPassword = '" + password + "'");

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
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("Select UserTypeId From Users Where UserId = " + userId);
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
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Users WHERE Email = '" + userName + "'");
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
            Statement stmt = con.createStatement();
            stmt.executeUpdate("UPDATE users SET FirstName = '" + newFirstName + "', LastName = '" + newLastName + "', Email = '" + newUserName + "', HashedPassword = '" + newPassword + "' WHERE Id = '" + user.id + "'");
            ResultSet rs = stmt.executeQuery("SELECT * FROM Users WHERE Id = '" + user.id + "'");
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
            Statement stmt = con.createStatement();
            stmt.executeUpdate("UPDATE users SET UserTypeId = '" + newUserType + "' WHERE Id = '" + user.id + "'");
            ResultSet rs = stmt.executeQuery("SELECT * FROM Users WHERE Id = '" + user.id + "'");
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

    public static Room getRoomById(int roomId){
        Room room = new Room();
        room.id = roomId;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM room WHERE Id = " + roomId);

            while(rs.next()){
                room.isAvailable = rs.getInt("isAvailable"); //Todo: Bit in SQL, so should be boolean here?
                room.nextAvailableDate = rs.getDate("NextAvailableDate"); //Todo: Automatically set after guest checks in/out?
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
            Statement stmt = con.createStatement();
            java.text.SimpleDateFormat sdf =
                    new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nextAvailableDate = sdf.format(room.nextAvailableDate);
            String roomUpdateQuery=  "UPDATE room SET isAvailable = " + room.isAvailable +", NextAvailableDate = '" + nextAvailableDate + "', RoomType = '" + room.roomType +"', NumBeds = " + room.numBeds +", isSmoking = " + room.isSmoking +", BedType = '" + room.bedType + "' WHERE Id = " + room.id;
            stmt.executeUpdate(roomUpdateQuery);
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
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(1) FROM users WHERE Email = '" + newEmail + "'");

            while (rs.next()) {
                is_repeated = rs.getInt("COUNT(1)");
            }

            if (is_repeated == 1)
            {
                System.out.println("Repeated Email");
                return true;
            }
            con.close();
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        return false;
    }
}
