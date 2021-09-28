package main.java.Utilities;
import main.java.DataModels.User;
import main.java.DataModels.UserType;

import java.sql.*;

//String url ="jdbc:mysql://mysqlclassproject.mysql.database.azure.com:3306/{your_database}?useSSL=true&requireSSL=false"; myDbConn = DriverManager.getConnection(url, "zzsa@mysqlclassproject", {your_password});

public class SqlConnection {


    //String url ="jdbc:mysql://mysqlclassproject.mysql.database.azure.com:3306/{your_database}?useSSL=true&requireSSL=false"; myDbConn = DriverManager.getConnection(url, "zzsa@mysqlclassproject", {your_password});
    //public static String connectionString = "jdbc:mysql://mysqlclassproject.mysql.database.azure.com:3306/hotelmanagement?useSSL=true&requireSSL=false";
    public static String connectionString = "jdbc:mysql://mysqlclassproject.mysql.database.azure.com:3306/hotelmanagement?useSSL=true&requireSSL=false";
    public static String connectionUserName = "zzsa@mysqlclassproject";
    public static String connectionPassword = "Y7*9dkUkl1";

    //Users
    public static User createUser(User user) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);
            Statement stmt = con.createStatement();
            stmt.execute("Insert Into Users (UserTypeId, FirstName, LastName, Email, HashedPassword, CreateDate, ModifiedDate, LastLoginDate) VALUES (" + user.userTypeId + ", '" + user.firstName + "', '" + user.lastName + "', '" + user.email + "', '" + user.password + "', NOW(), NOW(), null)");
            con.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return getUserId(user);
    }

    //Get User
    public static User getUser(User user) {

        return user;
    }

    public static User getUserId(User user){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionString, connectionUserName, connectionPassword);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Id From Users WHERE Email = '" + user.email + "' AND HashedPassword = '" + user.password + "'");

            while (rs.next()) {
                user.id = rs.findColumn("id");
            }

            con.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
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

    //Jacoby more user methods
    //Modify User account. Takes in a user and returns that same user if succesfull

    //public static User getUserByUsername(string userName)

    //public static User validateAndGetUser(String userName, String password)

    //static function that takes in a User object and runs an Update statment
    //Things you can't modify:
    //




    //Jake
    //Room methods

}
