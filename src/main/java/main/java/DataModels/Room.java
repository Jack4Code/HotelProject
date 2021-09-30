package main.java.DataModels;

import java.sql.Date;

public class Room {
    public int Id, isAvailable, NumBeds, isSmoking;
    public String RoomType, BedType;
    public Date NextAvailableDate;

    public Room(){

    }

    public Room(int isAvailable, Date NextAvailableDate, String RoomType, int NumBeds, String BedType, int isSmoking){
        this.isAvailable = isAvailable;
        this.NextAvailableDate = NextAvailableDate;
        this.RoomType = RoomType;
        this.NumBeds = NumBeds;
        this.BedType = BedType;
        this.isSmoking = isSmoking;

    }


}
