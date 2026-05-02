package com.hotel;

public class Room {

    private final String roomNumber;
    private final double pricePerNight;
    private final RoomType type;
    private RoomStatus status;

    public Room(String roomNumber, double pricePerNight, RoomType type){
        this.roomNumber = roomNumber;
        this.pricePerNight = pricePerNight;
        this.type = type;
        this.status = RoomStatus.AVAILABLE;
    }

    public String getRoomNumber(){
        return roomNumber;
    }

    public double getPricePerNight(){
        return pricePerNight;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    public RoomType getType() {
        return type;
    }

    public boolean isAvailable(){
        return status == RoomStatus.AVAILABLE;
    }

    @Override
    public String toString() {
        return "Room{" +
                ", number='" + roomNumber + '\'' +
                ", pricePerNight=" + pricePerNight +
                ", status=" + status +
                ", type=" + type +
                '}';
    }

}
