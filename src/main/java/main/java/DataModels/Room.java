package main.java.DataModels;

import java.sql.Date;

public class Room {
    public int id, numBeds, isSmoking;
    public String roomType, bedType;
    public Date nextAvailableDate;
    public boolean isAvailable;

    public Room(){

    }

    public Room(boolean isAvailable, Date NextAvailableDate, String RoomType, int NumBeds, String BedType, int isSmoking){
        this.isAvailable = isAvailable;
        this.nextAvailableDate = NextAvailableDate;
        this.roomType = RoomType;
        this.numBeds = NumBeds;
        this.bedType = BedType;
        this.isSmoking = isSmoking;

    }


}
