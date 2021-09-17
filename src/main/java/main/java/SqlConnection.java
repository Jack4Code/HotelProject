package main.java;

import main.java.DataModels.User;

import java.sql.*;

public class SqlConnection {

    public static void connectionTest() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelmanagement", "root", "S69GLmCOMGILA");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("Select * From Users");
            while (rs.next()) {
                System.out.println(rs.getString(3));
            }
            con.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static boolean validateUser(String userName, String password) {
        boolean isValidUser = false;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelmanagement", "root", "S69GLmCOMGILA");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("Select Email, HashedPassword From Users WHERE Email = '" + userName + "' AND HashedPassword = '" + password + "'");

            while (rs.next()) {
                isValidUser = true;
            }

            con.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return isValidUser;
    }

    public static void createUser(User user){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelmanagement", "root", "S69GLmCOMGILA");
            Statement stmt = con.createStatement();
            stmt.execute("Insert Into Users (UserTypeId, FirstName, LastName, Email, HashedPassword, CreateDate, ModifiedDate, LastLoginDate) VALUES (" + user.userTypeId + ", '" + user.firstName + "', '" + user.lastName + "', '" + user.email + "', '" + user.password + "', NOW(), NOW(), null)");
            con.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
