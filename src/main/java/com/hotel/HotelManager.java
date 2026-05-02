package com.hotel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class HotelManager {

    private final ArrayList<Room> rooms = new ArrayList<>();
    private final ArrayList<Customer> customers = new ArrayList<>();
    private final ArrayList<Booking> bookings = new ArrayList<>();

    public void addRoom(Room room){
        for( Room r : rooms){
            if(r.getRoomNumber().equals(room.getRoomNumber())){
                throw new IllegalArgumentException("Room already exists: " + room.getRoomNumber());
            }
        }
        rooms.add(room);
        System.out.println("Added: " + room);
    }

    public ArrayList<Room> getAllAvailableRooms(){
        ArrayList<Room> result = new ArrayList<>();
        for(Room r : rooms) {
            if(r.isAvailable()) {
                result.add(r);
            }
        }

        return result;
    }

    public ArrayList<Room> getAvailableRooms(LocalDate start, LocalDate end){
       ArrayList<Room> result = new ArrayList<>();
       for(Room  r : rooms ){
           if (r.getStatus() != RoomStatus.MAINTENANCE && checkAvailability(r.getRoomNumber(), start, end))           {
               result.add(r);
           }
       }
       return result;
    }

    public void createCustomer(Customer customer){
        for (Customer c : customers) {
            if (c.getEmail().equals(customer.getEmail())) {
                System.out.println("Customer already exists: " + customer.getName());
                return;
            }
        }
        customers.add(customer);
        System.out.println("Registered: " + customer);
    }


    public Booking createBooking(Customer customer, String roomNumber, LocalDate checkIn, LocalDate checkOut){
        Room room = null;
        for(Room r: rooms){
            if(r.getRoomNumber().equals(roomNumber)){
                room = r;
                break;
            }
        }

        if(room == null){
            throw new NoSuchElementException("Room not found" + roomNumber );
        }
        if(!checkAvailability(roomNumber, checkIn, checkOut)){
         throw new IllegalStateException("Room " + roomNumber + " is not available for the selected dates.");
        }

        Booking booking = new Booking(customer,room,checkIn,checkOut);
        room.setStatus(RoomStatus.OCCUPIED);
        bookings.add(booking);
        System.out.println("Booking created: " + booking);

        return booking;

    }

    public void cancelBooking(String bookingId){
        for (Booking b : bookings) {
            if (b.getBookingId().equals(bookingId)) {
                b.cancel();
                return;
            }
        }
        throw new NoSuchElementException("Booking not found: " + bookingId);
    }

    public void setBookingStatus(String bookingId, BookingStatus newStatus) {
        Booking booking = null;

        for (Booking b : bookings){
            if(b.getBookingId().equals(bookingId)){
                booking = b;
            }
        }
        if (booking == null){
            throw new NoSuchElementException("Booking not found: " + bookingId);
        }

        if (newStatus == BookingStatus.CANCELLED) {
            booking.cancel();
        } else {
            booking.setStatus(newStatus);
            if (newStatus == BookingStatus.COMPLETED)
                booking.getRoom().setStatus(RoomStatus.AVAILABLE);
        }
        System.out.println("Booking " + bookingId + " status -> " + newStatus);
    }

    public boolean checkAvailability(String roomNumber,LocalDate start, LocalDate end){
        for(Booking b : bookings){
            if(b.getRoom().getRoomNumber().equals(roomNumber) && b.overlaps(start,end) && b.getStatus() != BookingStatus.CANCELLED){
                return false;
            }
        }
        return true;
    }

    public ArrayList<Booking> findBookingsByCustomer(String customerId){
        ArrayList<Booking> result = new ArrayList<>();
        for(Booking b : bookings){
            if( b.getCustomer().getCustomerId().equals(customerId)){
                result.add(b);
            }
        }

        return result;
    }

    public ArrayList<Booking> findBookingsByPeriod(LocalDate start,LocalDate end ){
        ArrayList<Booking> result = new ArrayList<>();
        for (Booking b : bookings){
            if( b.overlaps(start, end)){
                result.add(b);
            }
        }

       return result;
    }

    public Booking findBookingById(String bookingId){
        for(Booking b : bookings ){
            if(b.getBookingId().equals(bookingId)){
                return b;
            }
        }
        throw new NoSuchElementException("Booking not found: " + bookingId);
    }

    public void printSummary(){
        int active=0;

        for (Booking b : bookings){
            if(b.getStatus() == BookingStatus.CONFIRMED){
                active++;
            }
        }
        System.out.println("\n=== Hotel Summary ===");
        System.out.println("Rooms     : " + rooms.size() + " total, " + getAllAvailableRooms().size() + " available");
        System.out.println("Customers : " + customers.size());
        System.out.println("Bookings  : " + bookings.size() + " total, " + active + " active");
        System.out.println("====================\n");
    }
}


















