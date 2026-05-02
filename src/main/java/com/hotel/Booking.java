package com.hotel;

import java.time.LocalDate;
import java.util.UUID;
import java.time.temporal.ChronoUnit;

public class Booking {
    private final String bookingId;
    private final Room room;
    private final Customer customer;
    private BookingStatus status;
    private final double totalPrice;
    private final LocalDate checkIn;
    private final LocalDate checkOut;

    public Booking( Customer customer, Room room,LocalDate checkIn, LocalDate checkOut ){
        this.bookingId = UUID.randomUUID().toString();
        this.room = room;
        this.customer = customer;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalPrice = room.getPricePerNight() * getDurationDays();
        this.status = BookingStatus.CONFIRMED;
    }

    public String getBookingId(){
        return bookingId;
    }

    public double getTotalPrice(){
        return totalPrice;
    }

    public BookingStatus getStatus(){
        return status;
    }

    public void setStatus(BookingStatus status) {
        if (this.status == BookingStatus.CANCELLED) {
            throw new IllegalStateException("Can't change status of a cancelled booking");
        }
        this.status = status;
    }

    public int getDurationDays(){
        return (int) ChronoUnit.DAYS.between(checkIn, checkOut);
    }

    public void cancel(){
        setStatus(BookingStatus.CANCELLED);
        room.setStatus(RoomStatus.AVAILABLE);
    }

    public boolean overlaps(LocalDate start, LocalDate end){
        if(status == BookingStatus.CANCELLED) return false;
        return start.isBefore(checkOut) || end.isAfter(checkIn);
    }

    public Customer getCustomer(){
        return customer;
    }

    public Room getRoom(){
        return room;
    }

    @Override
    public String toString() {
        return "Booking{" +
                ", Room='" + room.getRoomNumber() + '\'' +
                ", Customer=" + customer.getName() +
                ", Check In=" + checkIn +
                ", Check Out=" + checkOut +
                ", Duration Of Days=" + getDurationDays() +
                ", Total Price=" + totalPrice +
                ", Status=" + status +
                '}';
    }

}

