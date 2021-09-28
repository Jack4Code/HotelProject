package main.java.Managers;

public class RoomManager {

    public RoomManager()
    {

    }

    //Jake
    //Methods to modify room attributes such as
    //isAvailable, NextAvailableDate(?..), RoomType, NumBeds, BedType, isSmoking

    public void changeRoomStatus(int roomId, boolean isAvailable) {
        //Shouldn't be void, but...
        try{

        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        //Return a method in SQLConnection => updateRoomStatus() => will put actual query in SqlConnection file

    }

       /*
        changeNextAvailableDate(int roomId, Date nextAvailableDate )
        .
        .
        .
        all of them except for Id
     */




}
