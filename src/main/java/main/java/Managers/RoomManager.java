package main.java.Managers;

import java.util.Date;

public class RoomManager {

    //Jake

    //First check to see if the proper user is logged in?

    public static boolean changeRoomStatus(int roomId, boolean isAvailable) {

        boolean x = false;

        try
        {
            "".isEmpty(); //Do nothing - temporary
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        //Return a method in SQLConnection => updateRoomStatus() => will put actual query in SqlConnection file
        return true; //or whatever needed...
    }

    //public static boolean changeRoomType(int roomId, String RoomType ) {}
    //public static boolean changeNumBeds(int roomId, int NumBeds ) {}
    //public static boolean changeBedType(int roomId, String BedType ) {} <- Bed type similar to users? Use enum?
    //Method for isSmoking? Is that something you can change for a specific room? Or is it fixed?

}
